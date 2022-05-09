package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthService authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, authService, passwordEncoder);
    }

    @Test
    void usersForList() {
        List<User> users = generateUsers();
        when(userRepository.findAll()).thenReturn(users);

        List<UserListDTO> userDtos = userService.allUsersForList();

        assertEquals(users.size(), userDtos.size());
    }

    @Test
    void usersForMinimal() {
        List<User> users = generateUsers();
        when(userRepository.findAll()).thenReturn(users);

        List<UserMinimalDTO> userDtos = userService.allUsersMinimal();

        assertEquals(users.size(), userDtos.size());
    }

    List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        Role admin = new Role(1, ERole.ADMIN);
        Role employee = new Role(2, ERole.EMPLOYEE);

        for (int i = 0; i < 10; i++) {
            User user;
            if (i % 2 == 0) {
                user = User.builder().
                        username("username" + i).
                        password("password" + i).
                        email("email" + i + "@gmail.com").
                        roles(Set.of(employee)).
                        build();
            } else {
                user = User.builder().
                        username("username" + i).
                        password("password" + i).
                        email("email" + i + "@gmail.com").
                        roles(Set.of(admin)).
                        build();
            }

            users.add(user);
        }
        return users;
    }

}
