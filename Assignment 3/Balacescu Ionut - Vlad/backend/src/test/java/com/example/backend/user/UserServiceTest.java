package com.example.backend.user;

import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.mapper.UserMapper;
import com.example.backend.user.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.backend.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;



    @Test
    void allUsersForList() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }
        when(userRepository.findAll()).thenReturn(users);
        List<UserDTO> newUsers = userService.allUsersForList();
        assertEquals(users.size(),newUsers.size());
    }

    @Test
    void edit() {
        User user = User.builder().id(1L)
                .username(randomString())
                .password(UUID.randomUUID().toString())
                .email(randomEmail())
                .build();
        UserDTO userDTO = newUserDTO();
        userDTO.setUsername("dddddd");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userRepository.save(user)).thenReturn(user);
        userDTO.setUsername("dda");
        UserDTO userDTO1 = userService.edit(user.getId(),userDTO);
        assertEquals(userDTO1.getUsername(),"dda");
    }

    @Test
    void delete() {
        User user = User.builder()
                .id(1L)
                .username(randomString())
                .password(UUID.randomUUID().toString())
                .email(randomEmail())
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);

        userRepository.deleteById(user.getId());
        assertFalse(userRepository.existsByUsername(user.getUsername()));
    }
}