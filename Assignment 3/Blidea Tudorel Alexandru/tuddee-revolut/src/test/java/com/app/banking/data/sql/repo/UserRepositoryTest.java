package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.banking.TestCreationFactory.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ActiveProfiles("alexandra")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testAddUser() {
        String email = "email@employee.com";
        //String password = "Abcdefg1234!";
        User userSaved = repository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build());

        assertNotNull(userSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(User.builder()
                    .firstName(randomString())
                    .lastName(randomString())
                    .username(randomString())
                    .email(email)
                    .passwordHash(randomString())
                    .role(getRandomRole())
                    .birthDate(getRandomDate())
                    .build());
        });
    }

    @Test
    public void testExistsByUsername(){
        String username = randomString();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User user = repository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(username)
                .email(email)
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build());

        repository.save(user);
        Boolean result = repository.existsByUsername(username);
        assertEquals(result, true);
    }

    @Test
    public void testFindByUsername(){
        String username = randomString();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User user = repository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(username)
                .email(email)
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build());

        repository.save(user);

        Optional<User> result = repository.findByUsername(username);
        assertTrue(result.isPresent());
        result.ifPresent(u -> assertEquals(u.getUsername(), username));

    }


    @Test
    public void testExistsByEmail(){
        String username = randomString();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User user = repository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(username)
                .email(email)
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build());

        repository.save(user);
        Boolean result = repository.existsByEmail(email);
        assertEquals(result, true);
    }

    @Test
    public void testFindAll(){
        int noUsers = 10;
        List<User> users = new ArrayList<>();

        for (int i = 0; i < noUsers; i++) {
            users.add(User.builder()
                    .firstName(randomString())
                    .lastName(randomString())
                    .username(randomString())
                    .email(i + "@email.com")
                    .passwordHash(randomString())
                    .role(getRandomRole())
                    .birthDate(getRandomDate())
                    .build());
        }
        repository.saveAll(users);
        List<User> all = repository.findAll();
        assertEquals(users.size(), all.size());
    }

    @Test
    public void testFindById(){
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User user = repository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build());

        User savedUser = repository.save(user);

        Optional<User> result = repository.findById(savedUser.getId());
        assertTrue(result.isPresent());
        result.ifPresent(u -> assertEquals(u.getId(), savedUser.getId()));
    }

    @Test
    public void testDeleteById(){
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User savedUser = repository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build());

        //User savedUser = repository.save(user);

        repository.deleteById(savedUser.getId());

        Optional<User> result = repository.findById(savedUser.getId());
        assertTrue(result.isEmpty());
    }
}
