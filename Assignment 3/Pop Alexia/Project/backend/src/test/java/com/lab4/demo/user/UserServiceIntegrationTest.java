package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.user.model.ERole.ADMIN;
import static com.lab4.demo.user.model.ERole.CLIENT;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        roleRepository.save(new Role(1,ADMIN));
        roleRepository.save(new Role(2,CLIENT));
    }

    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        for(User user : users){
            user.setRoles(Set.of(roleRepository.findByName(CLIENT).get()));
        }
        userRepository.saveAll(users);

        List<UserDTO> userDTOS = userService.findAll();

        Assertions.assertEquals(users.size(), userDTOS.size());
    }

    @Test
    void findById(){
        User user = User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(encodePassword(randomString()))
                .build();

        user = userRepository.save(user);

        User foundUser = userService.findById(user.getId());

        Assertions.assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    void findByEmail(){
        User user = User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(encodePassword(randomString()))
                .build();

        user = userRepository.save(user);

        User foundUser = userService.findByEmail(user.getEmail());

        Assertions.assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    void existsByUsername(){
        User user = User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(encodePassword(randomString()))
                .build();

        user = userRepository.save(user);

        Assertions.assertTrue(userService.existsByUsername(user.getUsername()));
    }

    @Test
    void existsByEmail(){
        User user = User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(encodePassword(randomString()))
                .build();

        user = userRepository.save(user);

        Assertions.assertTrue(userService.existsByEmail(user.getEmail()));
    }

    @Test
    void filter(){
        User user1 = User.builder()
                .id(randomLong())
                .username("wwwwwww")
                .email("email@email.com")
                .password(encodePassword("password"))
                .build();

        User user2 = User.builder()
                .id(randomLong())
                .username("qqqqqqq")
                .email("email2@email.com")
                .password(encodePassword("password"))
                .build();

        userRepository.saveAll(List.of(user1,user2));

        List<UserDTO> all = userService.filterUsers("%w%");

        Assertions.assertEquals(1, all.size());
    }

    @Test
    void create(){
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .email("email@email.com")
                .password(passwordEncoder.encode(randomString()))
                .role(CLIENT.name())
                .build();
        user  = userService.create(user);
        Assertions.assertTrue(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    void edit() {
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email("email@email.com")
                .password(passwordEncoder.encode(randomString()))
                .role(CLIENT.name())
                .build();
        user = userService.create(user);
        user.setUsername("otherstring");
        userService.edit(user.getId(),user);

        Assertions.assertEquals(userRepository.findById(user.getId()).get().getUsername(), user.getUsername());

    }

    @Test
    void delete() {
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .email("email@email.com")
                .password(passwordEncoder.encode(randomString()))
                .role(CLIENT.name())
                .build();
        user = userService.create(user);
        userService.delete(user.getId());
        Assertions.assertTrue(userRepository.findById(user.getId()).isEmpty());
    }
}