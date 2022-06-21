package com.example.demo.user;

import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.dto.UserListDTO;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.TestCreationFactory.newUser;
import static com.example.demo.user.model.ERole.ADMIN;
import static com.example.demo.user.model.ERole.REGULAR_USER;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleRepository roleRepository;


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        roleRepository.save(new Role(1,ADMIN));
        roleRepository.save(new Role(2,REGULAR_USER));
    }

    @Test
    void findAll() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = newUser();
            users.add(user);
            userRepository.save(user);
        }

        List<UserDTO> userList = userService.getAll();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userList.get(i).getId());
            assertEquals(users.get(i).getUsername(), userList.get(i).getUsername());
        }
    }

    @Test
    void existsById(){
        User user=newUser();
        User savedUser=userRepository.save(user);

        assertTrue(userService.existsById(savedUser.getId()));
    }

    @Test
    void create(){
        UserDTO user=newUserDTO();

        userService.create(user);

        assertTrue(userRepository.existsByUsername(user.getUsername()));
    }

    @Test
    void update(){
        User user=newUser();
        User savedUser=userRepository.save(user);
        UserDTO userDTO=userMapper.toDTO(savedUser);

        String username=randomString();
        userDTO.setUsername(username);
        UserDTO updatedUserDTO=userService.update(userDTO.getId(),userDTO);

        assertEquals(userDTO.getUsername(), updatedUserDTO.getUsername());

    }

    @Test
    void delete(){
        User user=newUser();
        User savedUser=userRepository.save(user);
        userService.delete(savedUser.getId());
        assertFalse(userRepository.existsById(savedUser.getId()));

    }

}
