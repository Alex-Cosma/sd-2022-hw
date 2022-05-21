package com.example.youtubeish.user;

import com.example.youtubeish.BaseControllerTest;
import com.example.youtubeish.security.AuthService;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.youtubeish.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserServiceTest extends BaseControllerTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, authService, userMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
    }

    @Test
    void allUsers() {
        List<User> userList = List.of(
                newUser(), newUser(), newUser()
        );
        when(userRepository.findAll()).thenReturn(userList);

        for(User user: userList) {
            when(userMapper.toDto(user)).thenReturn(toDto(user));
        }
        List<UserDTO> allUsers = userService.allUsersDto();
        assertEquals(userList.size(), allUsers.size());
    }

    @Test
    void create() {
        UserDTO userDTO = newUserDto();
        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .build();
        when(authService.register(userDTO)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);

        UserDTO createdUser = userService.create(userDTO);
        assertEquals(createdUser.getId(), userDTO.getId());
    }

    @Test
    void delete() {
        UserDTO userDTO = newUserDto();
        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .build();
        doNothing().when(userRepository).deleteById(user.getId());
        userService.deleteById(user.getId());
    }

    @Test
    void findById() {
        Long id = randomLong();
        User user = newUser();
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);

        UserDTO userDTO1 = userService.findById(id);
        assertEquals(userDTO1.getId(), user.getId());
    }

    @Test
    void getUserByUsername() {
        UserDTO userDTO = newUserDto();
        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .build();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));

        User searchedUser = userService.getUserByUsername(user.getUsername());
        assertEquals(user.getId(), searchedUser.getId());
    }

    @Test
    void edit() {
        UserDTO userDTO = newUserDto();
        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .build();
        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userRepository.findById(userDTO.getId())).thenReturn(java.util.Optional.ofNullable(user));
        when(userMapper.fromDto(userDTO)).thenReturn(user);

        userDTO.setEmail(randomEmail());
        userDTO.setUsername(randomString());
        when(userRepository.save(fromDto(userDTO))).thenReturn(fromDto(userDTO));

        UserDTO editedUser = userService.edit(userDTO.getId(), userDTO);
        assertEquals(userDTO.getUsername(), editedUser.getUsername());
    }

    @Test
    void existsByEmail() {
        UserDTO userDTO = newUserDto();
        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .build();
        when(userRepository.existsByEmail(user.getEmail()))
                .thenReturn(true);

        assertTrue(userService.existsByEmail(user.getEmail()));
        assertFalse(userService.existsByEmail(user.getUsername()));
    }

    @Test
    void existsByUsername() {
        UserDTO userDTO = newUserDto();
        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .build();
        when(userRepository.existsByUsername(user.getUsername()))
                .thenReturn(true);

        assertTrue(userService.existsByUsername(user.getUsername()));
        assertFalse(userService.existsByUsername(user.getEmail()));
    }
}
