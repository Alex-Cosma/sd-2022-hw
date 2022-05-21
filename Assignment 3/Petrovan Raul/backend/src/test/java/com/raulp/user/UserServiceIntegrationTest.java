package com.raulp.user;

import com.raulp.user.dto.user.UserMinimalDTO;
import com.raulp.user.model.User;
import com.raulp.user.repos.UserRepository;
import com.raulp.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

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
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();

        for (int i = 0; i < nrUsers; i++) {
            int finalI = i;
            assertEquals(users.get(i).getUsername(),
                    userMinimalDTOS.stream()
                            .filter(user -> user.getId() == users.get(finalI).getId()).collect(
                                    Collectors.toList()).get(0).getName());
        }
    }
}