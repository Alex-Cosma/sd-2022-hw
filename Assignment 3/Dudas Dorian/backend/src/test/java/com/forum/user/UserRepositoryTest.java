package com.forum.user;

import com.forum.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static com.forum.TestCreationFactory.listOf;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    void testDataIntegrity(){
        User user = userRepository.save(User.builder().username("asd").email("q@q.q").password("1").build());

        assertNotNull(user);

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(User.builder().build());
        });
    }

    @Test
    void testFindAll(){
        List<User> users = listOf(User.class);
        userRepository.saveAll(users);
        List<User> foundUsers = userRepository.findAll();
        assertEquals(users.size(), foundUsers.size());
    }

    @Test
    void testDeleteById(){
        List<User> users = listOf(User.class);
        userRepository.saveAll(users);
        List<User> booksFound = userRepository.findAll();
        assertEquals(users.size(), booksFound.size());
        userRepository.deleteById(booksFound.get(0).getId());
        assertEquals(users.size() - 1, userRepository.findAll().size());
    }
}
