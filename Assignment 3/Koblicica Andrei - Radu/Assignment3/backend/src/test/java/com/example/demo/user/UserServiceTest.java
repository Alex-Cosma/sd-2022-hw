package com.example.demo.user;

import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.dto.UserListDTO;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.user.model.ERole.ADMIN;
import static com.example.demo.user.model.ERole.REGULAR_USER;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
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
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository.deleteAll();
        roleRepository.deleteAll();
        roleRepository.save(new Role(1,ADMIN));
        roleRepository.save(new Role(2,REGULAR_USER));
        userService = new UserService(userRepository, userMapper,passwordEncoder, authService);
    }

    @Test
    void findAll() {
        List<User> users = listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> all = userService.getAll();

        assertEquals(users.size(), all.size());
    }

    @Test
    void existsById(){
        User user=newUser();
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.existsById(user.getId())).thenReturn(true);

        assertTrue(userService.existsById(user.getId()));
    }

    @Test
    void create(){
        UserDTO user=newUserDTO();

        SignupRequest signupRequest= SignupRequest.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(null)
                .build();

        when(authService.existsByEmail(user.getEmail())).thenReturn(false);
        when(authService.existsByUsername(user.getUsername())).thenReturn(false);

        doNothing().when(authService).register(signupRequest);

        ResponseEntity response=userService.create(user);

        assertEquals(200,response.getStatusCode().value());
    }

    @Test
    void update(){
        User user=newUser();
        UserDTO userDTO=newUserDTO();
        when(userRepository.findById(user.getId())).thenReturn(of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);
        when(userRepository.save(user)).thenReturn(user);
        userDTO.setId(user.getId());
        String username=randomString();
        userDTO.setUsername(username);

        UserDTO newUserDTO=userService.update(userDTO.getId(),userDTO);
        assertEquals(userDTO, newUserDTO);

    }

    @Test
    void delete(){
        User user=newUser();
        when(userRepository.save(user)).thenReturn(user);
        userService.delete(user.getId());
        assertFalse(userRepository.existsById(user.getId()));

    }

}
