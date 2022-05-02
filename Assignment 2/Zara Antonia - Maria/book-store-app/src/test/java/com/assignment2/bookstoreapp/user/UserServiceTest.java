package com.assignment2.bookstoreapp.user;

import com.assignment2.bookstoreapp.TestCreationFactory;
import com.assignment2.bookstoreapp.report.ReportServiceFactory;
import com.assignment2.bookstoreapp.security.AuthService;
import com.assignment2.bookstoreapp.user.dto.UserMinimalDTO;
import com.assignment2.bookstoreapp.user.dto.UserRegisterDTO;
import com.assignment2.bookstoreapp.user.mapper.UserMapper;
import com.assignment2.bookstoreapp.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.assignment2.bookstoreapp.user.model.ERole.EMPLOYEE;
import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthService authService;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, authService, encoder);
    }

    @Test
    void findAll() {
        User user1 = User.builder()
                .username("root")
                .password("root!1")
                .build();
        User user2 = User.builder()
                .username("anto")
                .password("anto!1")
                .build();

        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserMinimalDTO> all = userService.allUsersMinimal();

        Assertions.assertEquals(users.size(), all.size());
    }




}