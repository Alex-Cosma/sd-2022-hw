package com.rdaniel.sd.a2.backend.user;

import com.rdaniel.sd.a2.backend.user.dto.RegularUserDto;
import com.rdaniel.sd.a2.backend.user.dto.UserListDto;
import com.rdaniel.sd.a2.backend.user.model.Role;
import com.rdaniel.sd.a2.backend.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static com.rdaniel.sd.a2.backend.TestCreationFactory.listOf;
import static com.rdaniel.sd.a2.backend.TestCreationFactory.newUser;
import static com.rdaniel.sd.a2.backend.user.model.RoleType.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {

  @Autowired
  private UserService userService;

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
  void allUsersForList() {
    final List<User> users = listOf(User.class, 20);
    final Role role = Role.builder().name(EMPLOYEE).build();
    roleRepository.save(role);

    users.forEach(user -> user.setRoles(Set.of(role)));

    userRepository.saveAll(users);

    final List<UserListDto> userDtos = userService.allUsersForList();

    assertEquals(users.size(), userDtos.size());
  }

  @Test
  void findAllRegularUsers() {
    final List<User> users = listOf(User.class, 20);
    final Role employeeRole = Role.builder().name(EMPLOYEE).build();
    roleRepository.save(employeeRole);
    users.forEach(user -> user.setRoles(Set.of(employeeRole)));

    final Role adminRole = Role.builder().name(ADMIN).build();
    roleRepository.save(adminRole);
    final User adminUser = newUser();
    adminUser.setRoles(Set.of(adminRole));

    users.add(adminUser);
    userRepository.saveAll(users);

    final List<UserListDto> userDtos = userService.findAllRegularUsers();

    assertEquals(users.size() - 1, userDtos.size());
  }

  @Test
  void findById() {
    final User user = newUser();
    final Role employeeRole = Role.builder().name(EMPLOYEE).build();
    roleRepository.save(employeeRole);

    user.setRoles(Set.of(employeeRole));
    final User save = userRepository.save(user);

    final UserListDto userDto = userService.findById(save.getId());

    assertEquals(save.getId(), userDto.getId());
    assertEquals(user.getUsername(), userDto.getName());
    assertEquals(user.getEmail(), userDto.getEmail());
  }

  @Test
  void createUser() {
    final Role employeeRole = Role.builder().name(EMPLOYEE).build();
    roleRepository.save(employeeRole);

    final RegularUserDto regularUserDto = RegularUserDto.builder()
        .name("John Doe")
        .email("johndoes@email.com")
        .password("password1")
        .build();

    final UserListDto userDto = userService.createRegularUser(regularUserDto);

    assertNotNull(userDto);
    assertEquals(regularUserDto.getName(), userDto.getName());
    assertEquals(regularUserDto.getEmail(), userDto.getEmail());
  }

  @Test
  void updateRegularUser() {
    final User user = newUser();
    final Role employeeRole = Role.builder().name(EMPLOYEE).build();
    roleRepository.save(employeeRole);
    user.setRoles(Set.of(employeeRole));

    final User savedUser = userRepository.save(user);

    final RegularUserDto regularUserDto = RegularUserDto.builder()
        .name(user.getUsername())
        .email("newEmail@email.com")
        .password("newPassword")
        .build();

    final UserListDto updatedUser = userService.updateRegularUser(savedUser.getId(), regularUserDto);

    assertEquals(regularUserDto.getName(), updatedUser.getName());
    assertEquals(regularUserDto.getEmail(), updatedUser.getEmail());
  }

  @Test
  void deleteRegularUser() {
    final User user = newUser();
    final Role employeeRole = Role.builder().name(EMPLOYEE).build();
    roleRepository.save(employeeRole);
    user.setRoles(Set.of(employeeRole));

    final User savedUser = userRepository.save(user);

    userService.deleteRegularUser(savedUser.getId());
  }
}