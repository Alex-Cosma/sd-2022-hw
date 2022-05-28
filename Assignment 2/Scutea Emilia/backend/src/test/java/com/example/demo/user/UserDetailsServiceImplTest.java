package com.example.demo.user;

import com.example.demo.TestCreationFactory;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDetailsServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
    }

    @Test
    void loadUserByUsername(){
        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);

        String username = user.getUsername();

        Assertions.assertEquals(username, userDetailsService.loadUserByUsername(username).getUsername());


    }
}