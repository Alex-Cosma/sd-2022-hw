package com.app.user;

import com.app.TestCreationFactory;
import com.app.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void findByUsername() {
        User user = TestCreationFactory.newUser();
        userRepository.save(user);

        Optional<User> actual = userRepository.findByUsername(user.getUsername());

        assertTrue(actual.isPresent());
    }

    @Test
    void existsByUsername() {
        User user = TestCreationFactory.newUser();
        userRepository.save(user);

        assertTrue(userRepository.existsByUsername(user.getUsername()));
    }

    @Test
    void existsByEmail() {
        User user = TestCreationFactory.newUser();
        userRepository.save(user);

        assertTrue(userRepository.existsByEmail(user.getEmail()));
    }
}
