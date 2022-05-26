package com.policeSection.user;

import com.policeSection.user.dto.UserMinimalDTO;
import com.policeSection.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp()
    {
        userRepository.deleteAll();
    }

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
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getName());
        }


    }
    @Test
    void delete(){
        Long id = 1L;
        User user = User.builder()
                .id(id)
                .username("whatever")
                .email("email@email.com")
                .password("    ")
                .build();

        User savedUser=userRepository.save(user);


        userService.delete(savedUser.getId());
        assertFalse(userRepository.existsById(savedUser.getId()));

    }
    @Test
    void findByID(){
        Long id = 1L;
        User user = User.builder()
                .id(id)
                .username("whatever")
                .email("email@email.com")
                .password("    ")
                .build();

        User savedUser=userRepository.save(user);


        assertEquals(userService.findById(savedUser.getId()),savedUser);

    }
}