package com.raulp.user.repos;

import com.raulp.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void findByUsername() {
        User user = User.builder().username("raulp").password("raulp").build();
        User user1 =
                User.builder().username("raulp").password("raulp").email("email@email.com").build();
        userRepository.save(user1);
        assertEquals(user1, userRepository.findByUsername("raulp").get());
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    void existsByUsername() {
        User user1 =
                User.builder().username("raulp").password("raulp").email("email@email.com").build();
        userRepository.save(user1);
        assertTrue(userRepository.existsByUsername("raulp"));
        assertFalse(userRepository.existsByUsername("raulp1"));
    }

    @Test
    void existsByEmail() {
        User user1 =
                User.builder().username("raulp").password("raulp").email("email@email.com").build();
        userRepository.save(user1);
        assertTrue(userRepository.existsByEmail("email@email.com"));
        assertFalse(userRepository.existsByEmail("email1@email.com"));
    }

}