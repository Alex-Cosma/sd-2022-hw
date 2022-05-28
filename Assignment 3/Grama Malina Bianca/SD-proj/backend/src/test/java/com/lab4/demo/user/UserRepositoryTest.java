package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.order.repository.OrderRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.RoleRepository;
import com.lab4.demo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        for(ERole role : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(role)
                            .build()
            );
        }
    }

    @Test
    void findAll() {
        assertEquals(0, userRepository.findAll().size());
        Role customer = roleRepository.findByName(ERole.CUSTOMER).get();
        List<User> users = TestCreationFactory.listOf(User.class);
        for(User user : users) {
            user.setRoles(Set.of(customer));
        }

        List<User> usersFromRepo = userRepository.saveAll(users);

        assertEquals(users.size(), userRepository.findAll().size());

        for(User user : users) {
            assertEquals(user.getUsername(), usersFromRepo.get(users.indexOf(user)).getUsername());
            assertEquals(user.getPassword(), usersFromRepo.get(users.indexOf(user)).getPassword());
            assertEquals(user.getEmail(), usersFromRepo.get(users.indexOf(user)).getEmail());
            assertEquals(user.getRoles().contains(customer), usersFromRepo.get(users.indexOf(user)).getRoles().contains(customer));
        }

    }

    @Test
    void findByUsername() {
        Role customer = roleRepository.findByName(ERole.CUSTOMER).get();
        List<User> users = TestCreationFactory.listOf(User.class);
        for(User user : users) {
            user.setRoles(Set.of(customer));
        }

        userRepository.saveAll(users);

        for(User user : users) {
            assertEquals(user.getUsername(), userRepository.findByUsername(user.getUsername()).get().getUsername());
            assertEquals(user.getPassword(), userRepository.findByUsername(user.getUsername()).get().getPassword());
            assertEquals(user.getEmail(), userRepository.findByUsername(user.getUsername()).get().getEmail());
            assertEquals(user.getRoles().contains(customer), userRepository.findByUsername(user.getUsername()).get().getRoles().contains(customer));
        }
    }

    @Test
    void existsByUsername() {
        Role customer = roleRepository.findByName(ERole.CUSTOMER).get();
        List<User> users = TestCreationFactory.listOf(User.class);
        for(User user : users) {
            user.setRoles(Set.of(customer));
        }

        userRepository.saveAll(users);

        for(User user : users) {
            assertTrue(userRepository.existsByUsername(user.getUsername()));
        }
    }

    @Test
    void existsByEmail() {
        Role customer = roleRepository.findByName(ERole.CUSTOMER).get();
        List<User> users = TestCreationFactory.listOf(User.class);
        for(User user : users) {
            user.setRoles(Set.of(customer));
        }

        userRepository.saveAll(users);

        for(User user : users) {
            assertTrue(userRepository.existsByEmail(user.getEmail()));
        }
    }
}