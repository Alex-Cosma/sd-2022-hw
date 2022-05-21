package com.example.demo.user;

import com.example.demo.TestCreationFactory;
import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EmailService emailService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, authService, emailService);
    }

    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);

        when(userRepository.findAll()).thenReturn(users);

        UserDTO userDTO = TestCreationFactory.newUserDTO();
        User user = TestCreationFactory.newUser();

        when(userMapper.toDto(user)).thenReturn(userDTO);
        doNothing().when(userMapper).populateRolesDTO(user, userDTO);

        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    void create(){
        UserDTO userDTO = TestCreationFactory.newUserDTO();

        doNothing().when(authService).register(SignupRequest.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .roles(userDTO.getRoles())
                .build());

        User user = TestCreationFactory.newUser();
        when(userRepository.save(user)).thenReturn(user);

        ERole eRole = ERole.EMPLOYEE;
        Role role = TestCreationFactory.newRole();

        when(roleRepository.findByName(eRole)).thenReturn(Optional.ofNullable(role));

        Assertions.assertEquals(userDTO.getUsername(), userService.create(userDTO).getUsername());
    }

    @Test
    void edit(){
        User user = TestCreationFactory.newUser();
        UserDTO userDTO =TestCreationFactory.newUserDTO();

        when(userRepository.save(user)).thenReturn(user);

        Long id = user.getId();

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        when(userMapper.toDto(user)).thenReturn(userDTO);

        Assertions.assertEquals(userDTO.getUsername(), userService.edit(id, userDTO).getUsername());
    }

    @Test
    void delete(){
        User user = TestCreationFactory.newUser();
        when(userRepository.save(user)).thenReturn(user);

        Long id = user.getId();
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        doNothing().when(userRepository).delete(user);

        userService.delete(id);

        Assertions.assertFalse(userRepository.existsByEmail(user.getEmail()));
    }

}
