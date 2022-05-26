package com.example.leaguecomp.user;

import com.example.leaguecomp.security.AuthService;
import com.example.leaguecomp.security.dto.SignupRequest;
import com.example.leaguecomp.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthService authService;
    @BeforeEach
    void setUp(){
        authService.register(SignupRequest.builder()
                .email("andrei3@gmail.com")
                .username("adolf3")
                .password("parolaHei3!")
                .roles(Set.of("ADMINISTRATOR"))
                .build());
    }

    @AfterEach
    void cleanUp(){
        User user = repository.findByUsername("adolf3").orElseThrow(()->new EntityNotFoundException("user not found"));
        repository.delete(user);
    }
    @Test
    void findByUsername() {
        User user = repository.findByUsername("adolf3").orElseThrow(()->new EntityNotFoundException("user not found"));
        assertEquals("adolf3",user.getUsername());
    }

    @Test
    void existsByUsername() {
        Boolean exists = repository.existsByUsername("adolf3");
        assertTrue(exists);
    }

    @Test
    void existsByEmail() {
        Boolean exists = repository.existsByEmail("andrei3@gmail.com");
        assertTrue(exists);
    }

}