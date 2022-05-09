package com.example.bookstore.user;


import com.example.bookstore.security.AuthService;
import com.example.bookstore.security.dto.MessageResponse;
import com.example.bookstore.security.dto.SignupRequest;
import com.example.bookstore.user.dto.UserListDTO;
import com.example.bookstore.user.dto.UserMinimalDTO;
import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import com.example.bookstore.user.model.User;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static java.util.stream.Collectors.toList;

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

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }
    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }




    public UserListDTO update(Long id, UserListDTO userDto) {
        User user = findById(id);
        user.setUsername(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<Role> roles=new HashSet<>();
        roles.add(Role.builder()
                .name(ERole.EMPLOYEE)
                .build());
        user.setRoles(roles);
        return userMapper.userListDtoFromUser(userRepository.save(user));
    }

    public ResponseEntity<?> create(UserListDTO user) {
        SignupRequest signupRequest= SignupRequest.builder()
                .username(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        Set<String> roles=new HashSet<>();
        roles.add("EMPLOYEE");
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