package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolationException;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.encodePassword;
import static com.lab4.demo.TestCreationFactory.randomLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void save() {
        User userSaved = repository.save(User.builder()
                .username("whatever1111")
                .password(passwordEncoder.encode("whatever"))
                .email("email1111@email.com")
                .rankingPoints(0)
                .build());

        assertNotNull(userSaved);
        assertEquals(1,repository.findAll().size());
        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(User.builder().build());
        });
    }

    @Test
    public void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        repository.saveAll(users);
        List<User> all = repository.findAll();
        assertEquals(users.size(), all.size());
    }

    @Test
    public void findById() {
        User user = repository.save(User.builder()
                .username("whatever")
                .password(passwordEncoder.encode("whatever"))
                .email("email@email.com")
                .rankingPoints(0)
                .reviews(null)
                .quizzSessions(null)
                .build());
        User userFound = repository.findById(user.getId()).get();
        assertEquals(user.getUsername(), userFound.getUsername());
    }

    @Test
    public void findByEmail() {
        User user = repository.save(User.builder()
                .username("whatever")
                .password(passwordEncoder.encode("whatever"))
                .email("email@email.com")
                .build());
        User userFound = repository.findByEmail(user.getEmail()).get();

        assertEquals(user.getEmail(), userFound.getEmail());
    }

    @Test
    public void existsByEmail(){
        User user = repository.save(User.builder()
                .username("whatever")
                .password(passwordEncoder.encode("whatever"))
                .email("email@email.com")
                .build());
        assertTrue(repository.existsByEmail(user.getEmail()));
    }

    @Test
    public void existsByUsername(){
        User user = repository.save(User.builder()
                .username("whatever")
                .password(passwordEncoder.encode("whatever"))
                .email("email@email.com")
                .build());
        assertTrue(repository.existsByUsername(user.getUsername()));
    }

    @Test
    public void filterUsers(){
        User user1 = User.builder()
                .id(randomLong())
                .username("wwwwwww")
                .email("email1212@email.com")
                .password(encodePassword("password"))
                .rankingPoints(0)
                .build();

        User user2 = User.builder()
                .id(randomLong())
                .username("qqqqqq")
                .email("email21231231@email.com")
                .password(encodePassword("password"))
                .rankingPoints(0)
                .build();

        repository.save(user1);
        repository.save(user2);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<User> all = repository.findAllByUsernameLikeOrEmailLike("%w%", "%w%",pageRequest);

        assertEquals(1, all.getNumberOfElements());
    }

    @Test
    public void update(){
        User user = repository.save(User.builder()
                .id(1L)
                .username("whatever")
                .password(passwordEncoder.encode("whatever"))
                .email("email@email.com")
                .quizzSessions(null)
                .rankingPoints(0)
                .reviews(null)
                .build());
        user = repository.findById(user.getId()).get();
        user.setUsername("newtitle");
        user = repository.save(user);

        assertEquals(repository.findById(user.getId()).get().getUsername(), "newtitle");
    }

    @Test
    public void delete(){
        User user = repository.save(User.builder()
                .username("whatever")
                .password(passwordEncoder.encode("whatever"))
                .email("email@email.com")
                .build());
        repository.delete(user);
        assertTrue(repository.findAll().isEmpty());
    }

}
