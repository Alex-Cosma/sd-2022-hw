package com.app.user;

import com.app.TestCreationFactory;
import com.app.security.AuthService;
import com.app.user.dto.UserDTO;
import com.app.user.mapper.UserMapper;
import com.app.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
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
    private PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, authService, encoder);
    }

    @Test
    void allUsers() {
        List<User> users = TestCreationFactory.listOf(User.class);
        List<UserDTO> userDTOS = TestCreationFactory.getUserDTO(users);
        when(userRepository.findAll()).thenReturn(users);

        for(int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            when(userMapper.toDto(u)).thenReturn(userDTOS.get(i));
        }

        List<UserDTO> actual = userService.allUsers();

        assertEquals(userDTOS, actual);
    }

    @Test
    void create() {
        UserDTO userDTO = TestCreationFactory.newUserDTO();
        UserDTO actual = userService.create(userDTO);

        assertEquals(userDTO.getUsername(), actual.getUsername());
    }

    @Test
    void edit() {
        User user = TestCreationFactory.newUser();
        UserDTO userDTO = TestCreationFactory.getUserDTO(List.of(user)).get(0);
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(user));

        when(userMapper.toDto(userRepository.save(user))).thenReturn(userDTO);

        UserDTO actual = userService.edit(userDTO.getId(), userDTO);

        assertEquals(userDTO, actual);
    }
}
