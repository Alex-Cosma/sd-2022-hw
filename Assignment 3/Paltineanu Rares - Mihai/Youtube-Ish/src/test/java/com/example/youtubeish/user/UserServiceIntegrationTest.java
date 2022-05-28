package com.example.youtubeish.user;

import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.model.ERole;
import com.example.youtubeish.user.model.Role;
import com.example.youtubeish.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.example.youtubeish.TestCreationFactory.randomEmail;
import static com.example.youtubeish.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void findAll() {
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

        List<UserDTO> userMinimalDTOS = userService.allUsersDto();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getUsername());
        }
    }

    @Test
    void getUserByUsername() {
        User user = User.builder()
                .username("User")
                .password(UUID.randomUUID().toString())
                .email("user@gmail.com")
                .build();
        userRepository.save(user);
        assertTrue(userService.existsByUsername(user.getUsername()));
    }

    @Test
    void existsByUsername() {
        User user = User.builder()
                .username("User")
                .password(UUID.randomUUID().toString())
                .email("user@gmail.com")
                .build();
        userRepository.save(user);
        assertTrue(userService.existsByUsername(user.getUsername()));
    }

    @Test
    void existsByEmail() {
        User user = User.builder()
                .username("User")
                .password(UUID.randomUUID().toString())
                .email("user@gmail.com")
                .build();
        userRepository.save(user);
        assertTrue(userService.existsByEmail(user.getEmail()));
    }

    @Test
    void deleteById() {
        User user = User.builder()
                .username("User")
                .password(UUID.randomUUID().toString())
                .email("user@gmail.com")
                .build();
        User newUser = userRepository.save(user);
        userService.deleteById(user.getId());
        assertFalse(userService.existsByUsername(newUser.getUsername()));
    }

    @Test
    void create() {
        roleRepository.save(Role.builder()
                .name(ERole.CUSTOMER)
                .build());

        UserDTO userDTO = UserDTO.builder()
                .password(randomString())
                .email(randomEmail())
                .username(randomString())
                .roles(Set.of(Role.builder()
                                .name(ERole.CUSTOMER)
                                .id(1)
                        .build()))
                .build();
        UserDTO user = userService.create(userDTO);
        assertNotNull(userService.findById(user.getId()));
    }

    @Test
    void findById() {
        User user = User.builder()
                .username("User")
                .password(UUID.randomUUID().toString())
                .email("user@gmail.com")
                .build();
        user = userRepository.save(user);
        assertEquals(user.getUsername(), userService.findById(user.getId()).getUsername());
    }
}
