package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.user.dto.UserCreateDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.UserRepository;
import com.lab4.demo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, authService, passwordEncoder);
    }

    @Test
    void allUsersMinimal() {
        List<User> users = generateUsers();
        when(userRepository.findAll()).thenReturn(users);

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();
        assertEquals(users.size(), userMinimalDTOS.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void allUsersForList() {
        List<User> users = generateUsers();
        when(userRepository.findAll()).thenReturn(users);

        List<UserListDTO> userListDTOS = userService.allUsersForList();
        assertEquals(users.size(), userListDTOS.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void create() {
        UserCreateDTO userListDTO = UserCreateDTO.builder()
                .id(1L)
                .name("test")
                .password("test")
                .email("test")
                .role("ADMIN")
                .build();

        ResponseEntity<?> response = userService.create(userListDTO);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void delete() {
        Long id = TestCreationFactory.randomLong();
        User user = TestCreationFactory.newUser();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));

        userService.delete(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void edit() {
        UserListDTO userListDTO = TestCreationFactory.newUserListDTO();

        Long id = TestCreationFactory.randomLong();
        User user = TestCreationFactory.newUser();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        user.setUsername(userListDTO.getName());
        user.setEmail(userListDTO.getEmail());

        String encodedPassword = new String(user.getPassword());
        when(passwordEncoder.encode(user.getPassword())).thenReturn(encodedPassword);

        user.setPassword(encodedPassword);

        when(userRepository.save(user)).thenReturn(user);

        when(userMapper.userListDtoFromUser(user)).thenReturn(userListDTO);

        UserListDTO userListDTOResponse = userService.edit(id, userListDTO);

        assertEquals(userListDTO, userListDTOResponse);
    }

    @Test
    void getUser() {
        Long id = TestCreationFactory.randomLong();
        User user = TestCreationFactory.newUser();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserListDTO userListDTO = TestCreationFactory.newUserListDTO();
        when(userMapper.userListDtoFromUser(user)).thenReturn(userListDTO);

        UserListDTO expected = userService.getUser(id);

        assertEquals(expected, userListDTO);
    }

    private List<User> generateUsers() {
        Role admin = new Role(1, ERole.ADMIN);
        Role employee = new Role(2, ERole.MANAGER);
        Role customer = new Role(3, ERole.CUSTOMER);

        List<User> users = TestCreationFactory.listOf(User.class);
        for (int i = 0; i < users.size(); i++) {
            if(i % 3 == 0) {
                users.get(i).setRoles(Set.of(admin));
            } else if(i % 3 == 1) {
                users.get(i).setRoles(Set.of(employee));
            } else {
                users.get(i).setRoles(Set.of(customer));
            }
        }

        return users;
    }

    @Test
    void findById() {
        Long id = TestCreationFactory.randomLong();
        User user = TestCreationFactory.newUser();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User expected = userService.findById(id);

        assertEquals(expected, user);
    }
}