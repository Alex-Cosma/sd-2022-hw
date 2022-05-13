package com.assignment2.bookstoreapp.user;

import com.assignment2.bookstoreapp.TestCreationFactory;
import com.assignment2.bookstoreapp.security.AuthService;
import com.assignment2.bookstoreapp.security.dto.SignupRequest;
import com.assignment2.bookstoreapp.user.UserRepository;
import com.assignment2.bookstoreapp.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        User userSaved = repository.save(User.builder()
                .id(TestCreationFactory.randomLong())
                .username("User2")
                .password(UUID.randomUUID().toString())
                .build());

        assertNotNull(userSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(User.builder().build());
        });
    }

    @Test
    public void deleteAll(){
        authService.register(SignupRequest.builder()
                .username("root")
                .password("root!1")
                .roles(Set.of("ADMIN"))
                .build());
        authService.register(SignupRequest.builder()
                .username("anto")
                .password("anto!1")
                .roles(Set.of("EMPLOYEE"))
                .build());

        Assertions.assertTrue(repository.findAll().size() > 0);

        repository.deleteAll();

        Assertions.assertEquals(repository.findAll().size(), 0);
    }

    @Test
    public void testFindAll() {

        authService.register(SignupRequest.builder()
                .username("root")
                .password("root!1")
                .roles(Set.of("ADMIN"))
                .build());
        authService.register(SignupRequest.builder()
                .username("anto")
                .password("anto!1")
                .roles(Set.of("EMPLOYEE"))
                .build());

        assertEquals(repository.findAll().size(), 2);

    }
}