package com.example.youtubeish.user;

import com.example.youtubeish.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.example.youtubeish.TestCreationFactory.newUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void existsByEmail() {
        User user = newUser();
        user = userRepository.save(user);
        assertTrue(userRepository.existsByEmail(user.getEmail()));
    }

    @Test
    void existsByUsername() {
        User user = newUser();
        user = userRepository.save(user);
        assertTrue(userRepository.existsByUsername(user.getUsername()));
    }

    @Test
    void deleteById() {
        User user = newUser();
        user = userRepository.save(user);
        userRepository.deleteById(user.getId());
        assertEquals(Optional.empty(), userRepository.findById(user.getId()));
    }

    @Test
    void allUsers() {
        userRepository.save(newUser());
        userRepository.save(newUser());
        userRepository.save(newUser());
        assertEquals(3, userRepository.findAll().size());
    }
}
