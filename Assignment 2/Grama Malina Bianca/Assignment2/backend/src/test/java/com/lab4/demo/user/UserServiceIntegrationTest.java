package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.lab4.demo.user.model.ERole.ADMIN;
import static com.lab4.demo.user.model.ERole.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }
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
    void create() {
        User user = User.builder()
                .username(TestCreationFactory.randomString())
                .password(UUID.randomUUID().toString())
                .email(TestCreationFactory.randomString() + "@gmail.com")
                .build();

        UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
        ResponseEntity<?> actualResponse = userService.create(userListDTO);

        ResponseEntity<?> response = ResponseEntity.ok(new MessageResponse("User registered successfully!"));

        assertEquals(response, actualResponse);

    }

    @Test
    void delete() {

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = User.builder()
                    .username(TestCreationFactory.randomString())
                    .password(UUID.randomUUID().toString())
                    .email(TestCreationFactory.randomString() + "@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        assertEquals(users.size(), userRepository.findAll().size());
        userService.delete(users.get(0).getId());
        assertEquals(users.size() - 1, userRepository.findAll().size());
        userService.delete(users.get(1).getId());
        assertEquals(users.size() - 2, userRepository.findAll().size());
    }

    @Test
    void update() {
        User user = User.builder()
                .username(TestCreationFactory.randomString())
                .password(UUID.randomUUID().toString())
                .email(TestCreationFactory.randomString() + "@gmail.com")
                .roles(Set.of(roleRepository.findByName(EMPLOYEE).orElse(null)))
                .build();

        userRepository.save(user);

        UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
        userMapper.populateRoles(user, userListDTO);
        userListDTO.setName("username");
        userListDTO.setEmail("email@email.com");
        userListDTO.setRoles(Set.of("ADMIN"));

        userService.edit(user.getId(), userListDTO);

        assertEquals(userListDTO.getName(), userRepository.findById(user.getId()).get().getUsername());
        assertEquals(userListDTO.getEmail(), userRepository.findById(user.getId()).get().getEmail());
        assertEquals(userListDTO.getRoles().contains("ADMIN"), userRepository.findById(user.getId()).get().getRoles().contains(roleRepository.findByName(ADMIN).orElse(null)));
    }
}