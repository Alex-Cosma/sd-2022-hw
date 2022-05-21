package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.order.repository.OrderRepository;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.dto.UserCreateDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.RoleRepository;
import com.lab4.demo.user.repository.UserRepository;
import com.lab4.demo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.lab4.demo.user.model.ERole.CUSTOMER;
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

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
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
    void allUsersMinimal() {
        Role customer = roleRepository.findByName(ERole.CUSTOMER).get();
        List<User> users = TestCreationFactory.listOf(User.class);
        for(User user : users) {
            user.setRoles(Set.of(customer));
        }

        List<User> savedUsers = userRepository.saveAll(users);
        List<UserMinimalDTO> expectedUsers = new ArrayList<>();
        for(User user : savedUsers) {
            expectedUsers.add(userMapper.userMinimalFromUser(user));
        }

        List<UserMinimalDTO> actualUsers = userService.allUsersMinimal();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void allUsersForList() {
        Role customer = roleRepository.findByName(ERole.CUSTOMER).get();
        List<User> users = TestCreationFactory.listOf(User.class);
        for(User user : users) {
            user.setRoles(Set.of(customer));
        }

        List<User> savedUsers = userRepository.saveAll(users);
        List<UserListDTO> expectedUsers = new ArrayList<>();
        for(User user : savedUsers) {
            expectedUsers.add(userMapper.userListDtoFromUser(user));
        }

        List<UserListDTO> actualUsers = userService.allUsersForList();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void create() {
        User user = User.builder()
                .username(TestCreationFactory.randomString())
                .password(UUID.randomUUID().toString())
                .email(TestCreationFactory.randomString() + "@gmail.com")
                .roles(Set.of(roleRepository.findByName(ERole.MANAGER).get()))
                .build();

        UserCreateDTO userCreateDTO = userMapper.userCreateDtoFromUser(user);
        ResponseEntity<?> actualResponse = userService.create(userCreateDTO);

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
    void edit() {
        User user = User.builder()
                .username(TestCreationFactory.randomString())
                .password(UUID.randomUUID().toString())
                .email(TestCreationFactory.randomString() + "@gmail.com")
                .roles(Set.of(roleRepository.findByName(CUSTOMER).orElse(null)))
                .build();

        userRepository.save(user);

        UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
        userMapper.populateRoles(user, userListDTO);
        userListDTO.setName("username");
        userListDTO.setEmail("email@email.com");

        userService.edit(user.getId(), userListDTO);

        assertEquals(userListDTO.getName(), userRepository.findById(user.getId()).get().getUsername());
        assertEquals(userListDTO.getEmail(), userRepository.findById(user.getId()).get().getEmail());
    }

    @Test
    void getUser() {
        Role customer = roleRepository.findByName(ERole.CUSTOMER).get();
        List<User> users = TestCreationFactory.listOf(User.class);
        for(User user : users) {
            user.setRoles(Set.of(customer));
        }

        List<User> savedUsers = userRepository.saveAll(users);

        for(User user : savedUsers) {
            UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
            userMapper.populateRoles(user, userListDTO);
            assertEquals(userListDTO, userService.getUser(user.getId()));
        }

    }

    @Test
    void findById() {
        User user = User.builder()
                .username(TestCreationFactory.randomString())
                .password(UUID.randomUUID().toString())
                .email(TestCreationFactory.randomString() + "@gmail.com")
                .roles(Set.of(roleRepository.findByName(ERole.CUSTOMER).get()))
                .build();

        User expected = userRepository.save(user);

        User actual = userService.findById(user.getId());

        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getRoles(), actual.getRoles());
        assertEquals(expected.getPassword(), actual.getPassword());

    }
}