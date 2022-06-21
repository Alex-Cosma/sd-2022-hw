package com.app.banking.service;

import com.app.banking.data.dto.mapper.UserMapper;
import com.app.banking.data.dto.mapper.UserMapperImpl;
import com.app.banking.data.dto.model.UserDto;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.app.banking.TestCreationFactory.*;
import static com.app.banking.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();

        userRepository.deleteAll();
    }

    @Test
    void testFindAll() {
        List<User> users = new ArrayList<>();
        int noUsers = 10;
        for (int i = 0; i < noUsers; i++) {
            users.add(buildUser());
        }
        userRepository.saveAll(users);

        List<UserDto> all = userService.findAll();

        assertEquals(noUsers, all.size());
    }

//    @Test // TODO
    void testFindById(){
        List<User> users = new ArrayList<>();
        int noUsers = 10;
        for (int i = 0; i < noUsers; i++) {
            User user = userRepository.save(buildUser());
            users.add(user);
        }



        UserDto foundUser = userService.findById(users.get(5).getId());
        assertEquals(userMapper.userToUserDto(users.get(5)), foundUser);
    }

//    @Test // TODO
    void testAddUser(){
        User user = buildUser();

        UserDto userDto = userMapper.userToUserDto(user);

        UserDto savedUSerDto = userService.addUser(userDto);

        assertEquals(savedUSerDto, userDto);
    }

//    @Test // TODO
    void testUpdateDeposit(){
        User user = buildUser();

        UserDto userDto = userMapper.userToUserDto(user);

        UserDto savedUSerDto = userService.update(1, userDto);

        assertEquals(savedUSerDto, userDto);
    }

    private User buildUser(){
        //String password = "Abcdefg1234!";
        return User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(randomString() + "@email.com")
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build();
    }

}
