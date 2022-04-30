package com.example.assignment2.user;

import com.example.assignment2.BaseControllerTest;
import com.example.assignment2.TestCreationFactory;
import com.example.assignment2.bookstore.BookController;
import com.example.assignment2.bookstore.model.Book;
import com.example.assignment2.bookstore.model.dto.BookDTO;
import com.example.assignment2.user.dto.UserListDTO;
import com.example.assignment2.user.model.ERole;
import com.example.assignment2.user.model.Role;
import com.example.assignment2.user.model.User;

import static com.example.assignment2.TestCreationFactory.*;
import static com.example.assignment2.TestCreationFactory.randomLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.assignment2.UrlMappings.USERS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;


    @BeforeEach
    protected  void setUp(){
        super.setUp();
        controller = new UserController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }
    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(service.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        User reqUser = User.builder().username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();

        when(service.create(reqUser)).thenReturn(reqUser);

        ResultActions result = performPostWithRequestBody("/api/users/create", reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }
}