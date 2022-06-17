package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lab4.demo.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }
    }
    @Test
    void findAll() {
        userRepository.deleteAll();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = User.builder()
                    .username(randomString())
                    .password(randomString())
                    .email(randomEmail())
                    .build();
            users.add(user);
            userRepository.save(user);
        }
        List<UserDTO> userMinimalDTOS = userService.findAll();
        for (int i = 0; i < 3; i++) {
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getUsername());
        }
    }
    @Test
    void create() {
        userRepository.deleteAll();

        UserDTO userDTO = UserDTO.builder()
                .username(randomString())
                .password(randomString())
                .email(randomEmail())
                .build();

        authService.register(SignupRequest.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build());
        assertTrue(userRepository.existsByUsername(userDTO.getUsername()));

    }
    @Test
    void delete(){
        UserDTO userDTO = UserDTO.builder()
                .username(randomString())
                .password(randomString())
                .email(randomEmail())
                .build();
        authService.register(SignupRequest.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build());
        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
        user.ifPresent(userRepository::delete);
        assertFalse(userRepository.existsByUsername(userDTO.getUsername()));
    }

}