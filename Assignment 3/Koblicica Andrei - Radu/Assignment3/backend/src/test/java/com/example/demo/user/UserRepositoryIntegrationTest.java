package com.example.demo.user;

import com.example.demo.movie.MovieRepository;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.util.Optional;

import static com.example.demo.TestCreationFactory.newUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository repository;

    @Test
    void findByUsername(){
        User user=newUser();
        User savedUser=repository.save(user);
        User foundUser=repository.findByUsername(savedUser.getUsername()).get();
        assertEquals(savedUser.getUsername(),foundUser.getUsername());
    }

    @Test
    void existsByUsername(){
        User user=newUser();
        User savedUser=repository.save(user);
        assertTrue(repository.existsByUsername(savedUser.getUsername()));
    }

    @Test
    void existsByEmail(){
        User user=newUser();
        User savedUser=repository.save(user);
        assertTrue(repository.existsByEmail(savedUser.getEmail()));
    }

    @Test
    void deleteById( ){
        User user=newUser();
        User savedUser=repository.save(user);
        repository.deleteById(savedUser.getId());
        assertFalse(repository.existsById(savedUser.getId()));

    }
}
