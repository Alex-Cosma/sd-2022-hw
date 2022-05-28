package com.example.demo.user;

import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp(){
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
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserDTO> userMinimalDTOS = userService.allUsers();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getUsername());
        }
    }

    @Test
    void create() {
        userRepository.deleteAll();
        UserDTO userDTO = UserDTO.builder()
                .username("username")
                .password("password")
                .email("email@yahoo.com")
                .build();

        authService.register(SignupRequest.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build());

        Assertions.assertTrue(userRepository.existsByEmail(userDTO.getEmail()));
    }

    @Test
    void edit() {
        userRepository.deleteAll();
        UserDTO userDTO = UserDTO.builder()
                .username("username")
                .password("password")
                .email("email@yahoo.com")
                .build();

        authService.register(SignupRequest.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build());

        Optional<User> user = userRepository.findByUsername("username");
        User user1 = new User();

        user1.setId(user.get().getId());
        user1.setPassword(user.get().getPassword());
        user1.setEmail("newEmail@yahoo.com");
        user1.setUsername("newUsername");
        userRepository.save(user1);

        Optional<User> foundUser = userRepository.findById(user1.getId());
        foundUser.ifPresent(value -> Assertions.assertEquals("newUsername", value.getUsername()));
        foundUser.ifPresent(value -> Assertions.assertEquals("newEmail@yahoo.com", value.getEmail()));
    }

    @Test
    void delete(){
        userRepository.deleteAll();
        UserDTO userDTO = UserDTO.builder()
                .username("username")
                .password("password")
                .email("email@yahoo.com")
                .build();

        authService.register(SignupRequest.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build());

        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
        user.ifPresent(userRepository::delete);

        Assertions.assertTrue(userRepository.findByUsername(userDTO.getUsername()).isEmpty());
    }
}
