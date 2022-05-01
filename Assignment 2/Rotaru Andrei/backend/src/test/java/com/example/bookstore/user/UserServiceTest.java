package com.example.bookstore.user;

import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.user.model.Role;
import com.example.bookstore.user.model.User;
import com.example.bookstore.user.model.dto.UserDTO;
import com.example.bookstore.user.model.dto.UserMinimalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.bookstore.user.model.ERole.ADMIN;
import static com.example.bookstore.user.model.ERole.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository.deleteAll();
        roleRepository.deleteAll();
        roleRepository.save(new Role(1,ADMIN));
        roleRepository.save(new Role(2,EMPLOYEE));
        userService = new UserService(userRepository,roleRepository, userMapper,passwordEncoder);
    }

    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);

        List<UserMinimalDTO> all = userService.allUsersMinimal();

        assertEquals(users.size(), all.size());
    }

    @Test
    void create(){
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .role(EMPLOYEE.name())
                .build();
        User user = User.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,EMPLOYEE)))
                .build();

        when(roleRepository.findByName(EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(EMPLOYEE).build()));
        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(userMapper.toDto(userRepository.save(userMapper.fromDto(userDTO)))).thenReturn(userDTO);
        UserDTO createdUser = userService.create(userDTO);
        assertEquals(createdUser,userMapper.toDto(user));
    }

    @Test
    void edit(){
        User user = User.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,EMPLOYEE)))
                .build();
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .role(EMPLOYEE.name())
                .build();

        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(roleRepository.findByName(EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(EMPLOYEE).build()));
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        when(userMapper.toDto(userRepository.save(userMapper.fromDto(userDTO)))).thenReturn(userDTO);
        UserDTO createdUser = userService.create(userDTO);

        createdUser.setUsername("newUsername");
        UserDTO editedUser = userService.edit(createdUser.getId(),createdUser);
        assertEquals("newUsername" ,editedUser.getUsername());
    }

    @Test
    void delete(){
        User user = User.builder()
                .id(1L)
                .username("")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .build();

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        userService.delete(1L);
        assertFalse(userService.existsByEmail(user.getEmail()));
    }
}
