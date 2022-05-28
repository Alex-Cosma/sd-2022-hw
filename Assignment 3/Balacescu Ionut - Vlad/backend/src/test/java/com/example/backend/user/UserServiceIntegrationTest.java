package com.example.backend.user;

import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.backend.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    void findAll() {
        userRepository.deleteAll();
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

        List<UserDTO> userDTOS = userService.allUsersForList();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userDTOS.get(i).getUsername());
        }
    }

    @Test
    void edit() {
        User user = User.builder()
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
        User newUser = userRepository.save(user);
        UserDTO userEdited = newUserDTO();
        userEdited.setPassword(randomString());
        UserDTO user1 = userService.edit(newUser.getId(), userEdited);
        assertEquals(user1.getUsername(),userEdited.getUsername());
        assertEquals(user1.getEmail(),userEdited.getEmail());
    }

    @Test
    void delete() {
        userRepository.deleteAll();
        int nrUsers = 10;
        User userToDelete = new User();
        for(int i=0;i<nrUsers;i++){
            User user = User.builder()
                    .username(randomString())
                    .email(randomEmail())
                    .password(randomString())
                    .build();
            userToDelete = user;
            userRepository.save(user);
        }
        userService.delete(userToDelete.getId());
        List<UserDTO> userDTOS = userService.allUsersForList();
        assertEquals(nrUsers-1,userDTOS.size());
    }
}


