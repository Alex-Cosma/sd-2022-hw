package com.example.demo.user;

import com.example.demo.BaseControllerTest;
import com.example.demo.security.dto.MessageResponse;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.dto.UserListDTO;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.listOf;
import static com.example.demo.TestCreationFactory.newUserDTO;
import static com.example.demo.UrlMapping.USERS;
import static com.example.demo.UrlMapping.USERS_ID_PART;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {


    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;



    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }


    @Test
    void allUsers() throws Exception {
        List<UserDTO> users = listOf(User.class);
        when(userService.getAll()).thenReturn(users);

        ResultActions response = performGet(USERS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));

    }

    @Test
    void create() throws Exception {
        UserDTO user = newUserDTO();
        ResultActions result = performPostWithRequestBody(USERS,user);
        result.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception{
        UserDTO user=newUserDTO();
        userService.create(user);
        ResultActions response = performDeleteWithPathVariables(USERS + USERS_ID_PART,user.getId());
        response.andExpect(status().isOk());
    }






}
