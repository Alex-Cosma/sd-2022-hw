package com.example.demo.user;

import com.example.demo.TestCreationFactory;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();
    }

    @Test
    void findByUsername() {
        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);
        String username = user.getUsername();

        Optional<User> userOptional = userRepository.findByUsername(username);

        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    void existsByUsername() {
        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);
        String username = user.getUsername();

        Assertions.assertTrue(userRepository.existsByUsername(username));
    }

    @Test
    void existsByEmail() {
        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);
        String email = user.getEmail();

        Assertions.assertTrue(userRepository.existsByEmail(email));
    }
}