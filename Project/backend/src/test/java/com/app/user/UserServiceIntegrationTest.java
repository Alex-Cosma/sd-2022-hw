package com.app.user;

import com.app.TestCreationFactory;
import com.app.security.AuthService;
import com.app.user.dto.UserDTO;
import com.app.user.mapper.UserMapper;
import com.app.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void allUsers() {
        List<User> users = TestCreationFactory.listOf(User.class);
        userRepository.saveAll(users);

        List<UserDTO> actual = userService.allUsers();

        assertEquals(users.size(), actual.size());
    }

    @Test
    void edit() {
        User user = TestCreationFactory.newUser();
        User savedUser = userRepository.save(user);
        UserDTO userDTO = userMapper.toDto(savedUser);
        userDTO.setUsername("New Username");

        UserDTO edited = userService.edit(userDTO.getId(), userDTO);

        assertEquals(userDTO.getUsername(), edited.getUsername());
    }
}