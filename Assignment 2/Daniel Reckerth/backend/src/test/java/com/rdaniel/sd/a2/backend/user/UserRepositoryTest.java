package com.rdaniel.sd.a2.backend.user;

import com.rdaniel.sd.a2.backend.user.model.Role;
import com.rdaniel.sd.a2.backend.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.rdaniel.sd.a2.backend.TestCreationFactory.listOf;
import static com.rdaniel.sd.a2.backend.TestCreationFactory.newUser;
import static com.rdaniel.sd.a2.backend.user.model.RoleType.ADMIN;
import static com.rdaniel.sd.a2.backend.user.model.RoleType.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
    roleRepository.deleteAll();
  }

  @Test
  void findByUsername() {
    final User user = newUser();
    final String username = user.getUsername();

    userRepository.save(user);
    final Optional<User> byUsername = userRepository.findByUsername(username);
    assertTrue(byUsername.isPresent());
    assertEquals(username, byUsername.get().getUsername());
  }

  @Test
  void existsByUsername() {
    final User user = newUser();
    final String username = user.getUsername();

    userRepository.save(user);
    final Boolean aBoolean = userRepository.existsByUsername(username);
    assertTrue(aBoolean);
  }

  @Test
  void existsByEmail() {
    final User user = newUser();
    final String email = user.getEmail();

    userRepository.save(user);
    final Boolean aBoolean = userRepository.existsByEmail(email);
    assertTrue(aBoolean);
  }

  @Test
  void findAllByRolesNameIn() {
    final List<User> users = listOf(User.class, 20);
    final Role role = Role.builder().name(EMPLOYEE).build();
    roleRepository.save(role);
    users.forEach(user -> user.setRoles(Set.of(role)));

    final Role adminRole = Role.builder().name(ADMIN).build();
    roleRepository.save(adminRole);
    final User adminUser = newUser();
    adminUser.setRoles(Set.of(adminRole));

    users.add(adminUser);
    userRepository.saveAll(users);

    final List<User> regularUsersFound = userRepository.findAllByRolesNameIn(Set.of(EMPLOYEE));

    assertEquals(users.size() - 1, regularUsersFound.size());
  }
}