package com.app.banking.controller;


import com.app.banking.BaseControllerTest;
import com.app.banking.data.dto.mapper.UserMapper;
import com.app.banking.data.dto.mapper.UserMapperImpl;
import com.app.banking.data.dto.model.UserDto;
import com.app.banking.data.sql.entity.User;
import com.app.banking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.app.banking.TestCreationFactory.*;
import static com.app.banking.URLMapping.USERS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserMapper userMapper;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        userMapper = new UserMapperImpl();
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void allRegularUsers() throws Exception {
        List<UserDto> users = new ArrayList<>();
        int noUsers = 10;
        for (int i = 0; i < noUsers; i++) {
            users.add(userMapper.userToUserDto(buildUser()));
        }


        when(userService.findAll()).thenReturn(users);

        ResultActions response = performGet(USERS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));

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

    @Test
    void getMyDetails() {
    }

    @Test
    void allUsers() {
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
