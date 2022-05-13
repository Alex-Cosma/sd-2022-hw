package com.example.bookstore.user;


import com.example.bookstore.user.dto.UserListDTO;
import com.example.bookstore.user.dto.UserMinimalDTO;
import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import com.example.bookstore.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAll() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password("WooHoo1!")
                    .email("user" + i + "@gmail.com")
                    .build();
            Set<Role> roles=new HashSet<>();
            roles.add(Role.builder()
                    .name(ERole.EMPLOYEE)
                    .build());
            user.setRoles(roles);
            userRepository.save(user);
        }

        List<UserListDTO> userList = userService.allUsersForList();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userList.get(i).getId());
            assertEquals(users.get(i).getUsername(), userList.get(i).getName());
        }
    }
}