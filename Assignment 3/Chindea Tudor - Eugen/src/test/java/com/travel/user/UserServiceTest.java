package com.travel.user;

import com.travel.TestCreationFactory;
import com.travel.city.CityService;
import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import com.travel.security.AuthService;
import com.travel.security.dto.SignupRequest;
import com.travel.user.dto.UserDTO;
import com.travel.user.mapper.UserMapper;
import com.travel.user.model.ERole;
import com.travel.user.model.Role;
import com.travel.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.control.MappingControl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


import static com.travel.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
class UserServiceTest {
    @InjectMocks
    private  UserService userService;

    @Mock
    private  UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository,userMapper,authService);
    }

    @Test
    void findAll() {
        List<User> users = createUsers();
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> all = userService.findAll();

        Assertions.assertEquals(users.size(), all.size());
    }

    @Test
    void create() {
        User user = newUser();
        UserDTO userDTO = newUserDTO();
        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        UserDTO newUserDTO = userService.create(userDTO);

        assertEquals(newUserDTO,userDTO);
    }

    @Test
    void delete() {
        User user = newUser();
        when(userRepository.save(user)).thenReturn(user);
        Long id = user.getId();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        userService.delete(user.getId());
        assertFalse(userRepository.existsById(id));
    }

    @Test
    void edit() {
        Long userId = randomLong();
        User user = newUser();
        UserDTO userDTO = newUserDTO();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        UserDTO newUserDTO = userService.edit(userId,userDTO);
        assertEquals(newUserDTO,userDTO);
    }
    List<User> createUsers(){
        List<User> users = new ArrayList<>();
        Role role1 = Role.builder().name(ERole.ADMIN).id(1).build();
        Role role2 = Role.builder().name(ERole.CLIENT).id(1).build();
        users.add(User.builder()
                .email("alex@email.com")
                .username("alex")
                .password("WooHoo1!")
                .roles(Set.of(role1))
                .build());
        users.add(User.builder()
                .email("alex1@email.com")
                .username("alex")
                .password("WooHoo1!")
                .roles(Set.of(role2))
                .build());
        return  users;
    }
}