package com.lab4.demo.user;

import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserDTO create(UserDTO userDTO){

        authService.register(SignupRequest.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .roles(Set.of("EMPLOYEE"))
                .build());

        return userDTO;
    }
    public void delete(Long id){
        User user = findById(id);
        userRepository.delete(user);
    }
    public UserDTO edit(Long id, UserDTO userDTO) {
        User actUser = findById(id);
        actUser.setUsername(userDTO.getUsername());
        actUser.setEmail(userDTO.getEmail());

        userRepository.save(actUser);
        return userMapper.toDto(actUser);
    }
}
