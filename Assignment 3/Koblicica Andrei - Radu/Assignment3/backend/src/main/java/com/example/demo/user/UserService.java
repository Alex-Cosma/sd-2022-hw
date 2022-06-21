package com.example.demo.user;



import com.example.demo.security.AuthService;
import com.example.demo.security.dto.MessageResponse;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.dto.UserDTO;

import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;


    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream().map(userMapper::toDTO)
                .collect(Collectors.toList());
    }


    public void delete(Long id){
        userRepository.deleteById(id);
    }




    public UserDTO update(Long id, UserDTO userDto) {
        User user = findById(id);
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userMapper.toDTO(userRepository.save(user));
    }

    public ResponseEntity<?> create(UserDTO user) {
        SignupRequest signupRequest= SignupRequest.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        Set<String> roles=new HashSet<>();
        roles.add("REGULAR_USER");
        signupRequest.setRoles(roles);
        if (authService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (authService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        authService.register(signupRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }




}