package com.app.banking.service;

import com.app.banking.data.dto.mapper.UserMapper;
import com.app.banking.data.dto.mapper.UserMapperImpl;
import com.app.banking.data.dto.model.UserDto;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.banking.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    void testFindAll() {
        List<User> users = new ArrayList<>();
        int noUsers = 10;
        for (int i = 0; i < noUsers; i++) {
            users.add(buildUser());
        }

        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> all = userService.findAll();

        assertEquals(noUsers, all.size());
    }

//    @Test // TODO
    void testFindByIDSuccess(){
        Integer id = randomInteger();
        User user = buildUser();
        user.setId(id);

        UserDto userDto = userMapper.userToUserDto(user);

        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findById(id)).thenReturn(optionalUser);

        UserDto result = userService.findById(id);

        assertEquals(userDto, result);
    }

    @Test
    void testFindByIDFail(){
        Integer id = randomInteger();
        User user = buildUser();
        user.setId(id);

        Optional<User> optionalUser = Optional.empty();

        when(userRepository.findById(id)).thenReturn(optionalUser);

        assertThrows(RuntimeException.class, () -> {
            userService.findById(id);
        });
    }

//    @Test // TODO
    void testAddUser(){
        User user = buildUser();
        UserDto userDto = userMapper.userToUserDto(user);

        when(userRepository.save(any())).thenReturn(user);

        UserDto savedUserDto = userService.addUser(userDto);
        assertEquals(userDto, savedUserDto);
    }

//    @Test // TODO
    void testUpdateUser(){
        User user = buildUser();
        UserDto userDto = userMapper.userToUserDto(user);

        when(userRepository.save(any())).thenReturn(user);

        UserDto savedUserDto = userService.update(randomInteger(), userDto);
        assertEquals(userDto, savedUserDto);
    }

    @Test
    void testDelete(){
        Integer id = randomInteger();
        User user = buildUser();
        user.setId(id);

        userRepository.save(user);

        doNothing().when(userRepository).deleteById(id);

        userService.delete(id);

        verify(userRepository, times(1)).deleteById(id);
    }


    private User buildUser(){
        String email = "email@employee.com";
        //String password = "Abcdefg1234!";
        return User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build();
    }
}
