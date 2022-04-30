package com.example.bookstore.User;

import com.example.bookstore.user.RoleRepository;
import com.example.bookstore.user.UserRepository;
import com.example.bookstore.user.UserService;
import com.example.bookstore.user.dto.UserMinimalDTO;
import com.example.bookstore.user.mapper.UserMapper;
import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.User;
import org.h2.engine.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void findAll(){
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for(int i = 0;i<nrUsers;i++){
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user"+i+"@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();
        for(int i = 0;i<nrUsers;i++){
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getName());
        }
    }

}
