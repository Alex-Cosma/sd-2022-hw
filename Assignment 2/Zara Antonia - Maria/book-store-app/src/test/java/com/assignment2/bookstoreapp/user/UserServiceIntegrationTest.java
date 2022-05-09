package com.assignment2.bookstoreapp.user;

import com.assignment2.bookstoreapp.TestCreationFactory;
import com.assignment2.bookstoreapp.user.dto.UserListDTO;
import com.assignment2.bookstoreapp.user.dto.UserMinimalDTO;
import com.assignment2.bookstoreapp.user.dto.UserRegisterDTO;
import com.assignment2.bookstoreapp.user.model.ERole;
import com.assignment2.bookstoreapp.user.model.Role;
import com.assignment2.bookstoreapp.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.codec.Decoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static com.assignment2.bookstoreapp.user.model.ERole.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    void findAll() {
        userRepository.deleteAll();
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .id(TestCreationFactory.randomLong())
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
//                    .roles(new HashSet<>(Collections.singletonList(new Role(1L,EMPLOYEE))) {
//                    })
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();

        assertEquals(users.size(), userMinimalDTOS.size());
    }

    @Test
    void findAllEmployees() {
        userRepository.deleteAll();
        int nrUsers = 10;
        List<UserRegisterDTO> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            UserRegisterDTO user = UserRegisterDTO.builder()
                    .id(TestCreationFactory.randomLong())
                    .name("User " + i)
                    .password(UUID.randomUUID().toString())
                    .roles(new HashSet<String>(Collections.singletonList(EMPLOYEE.name())) {
                    })
                    .build();
            users.add(user);
            userService.create(user);
        }

        List<UserListDTO> userDTOS = userService.allUsersForList();

        assertEquals(users.size(), userDTOS.size());
    }

    @Test
    void create(){
        userRepository.deleteAll();
        UserRegisterDTO user = UserRegisterDTO.builder()
                .id(TestCreationFactory.randomLong())
                .name("EU")
                .password(UUID.randomUUID().toString())
                .roles(new HashSet<String>(Collections.singletonList(EMPLOYEE.name())) {
                })
                .build();
        userService.create(user);

        Optional<User> found = userRepository.findByUsername("EU");

        assertTrue(found.isPresent());
   }
}