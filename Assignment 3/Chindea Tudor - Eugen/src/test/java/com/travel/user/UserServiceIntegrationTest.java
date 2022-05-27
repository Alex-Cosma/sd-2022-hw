package com.travel.user;

import com.travel.TestCreationFactory;
import com.travel.security.AuthService;
import com.travel.security.dto.MessageResponse;
import com.travel.security.dto.SignupRequest;
import com.travel.user.dto.UserDTO;
import com.travel.user.mapper.UserMapper;
import com.travel.user.model.ERole;
import com.travel.user.model.Role;
import com.travel.user.model.User;
import static com.travel.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
//    @Test
//    void edit(){
//        userRepository.deleteAll();
//    }
}
