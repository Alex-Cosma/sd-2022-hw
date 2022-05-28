package com.example.backend.user;

import com.example.backend.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.example.backend.TestCreationFactory.randomEmail;
import static com.example.backend.TestCreationFactory.randomString;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;



    @Test
    void findByUsername() {
        User user = User.builder()
                .password(randomString())
                .email(randomEmail())
                .username(randomString())
                .build();
        userRepository.save(user);
        Optional<User> newUser = userRepository.findByUsername(user.getUsername());
        Assertions.assertEquals(user.getUsername(),newUser.get().getUsername());

    }

    @Test
    void existsByUsername() {
        User user = User.builder()
                .password(randomString())
                .email(randomEmail())
                .username(randomString())
                .build();
        userRepository.save(user);
        Assertions.assertEquals(true,userRepository.existsByUsername(user.getUsername()));
    }

    @Test
    void existsByEmail() {
        User user = User.builder()
                .password(randomString())
                .email(randomEmail())
                .username(randomString())
                .build();
        userRepository.save(user);
        Assertions.assertEquals(true,userRepository.existsByEmail(user.getEmail()));
    }
}