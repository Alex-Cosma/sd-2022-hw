package com.app.bookingapp.controller;

import com.app.bookingapp.BaseControllerTest;
import com.app.bookingapp.TestCreationFactory;
import com.app.bookingapp.data.dto.model.UserDto;
import com.app.bookingapp.data.sql.entity.User;
import com.app.bookingapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.app.bookingapp.TestCreationFactory.randomLong;
import static com.app.bookingapp.TestCreationFactory.randomString;
import static com.app.bookingapp.utils.URLMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testAllUsers() throws Exception {
        List<User> users = TestCreationFactory.listOf(UserDto.class);
        when(userService.findAll()).thenReturn(users);

        ResultActions response = performGet(USERS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));
    }

    @Test
    void testCreate() throws Exception {
        UserDto reqUser = TestCreationFactory.newUserDto();

        when(userService.create(reqUser)).thenReturn(reqUser);

        ResultActions result = performPostWithRequestBody(USERS, reqUser);

        verify(userService, times(1)).create(reqUser);

        ResultMatcher jsonReqItem = jsonContentToBe(reqUser);
        result.andExpect(status().isCreated());
        MvcResult mvcResult = result.andReturn();
        jsonReqItem.match(mvcResult);
    }

    @Test
    void testUpdate() throws Exception {                
        final long id = randomLong();
        UserDto reqUser = TestCreationFactory.newUserDto();

        when(userService.update(id, reqUser)).thenReturn(reqUser);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(USERS + ID, id, reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void delete() throws Exception {
        String username = randomString();

        doNothing().when(userService).delete(username);

        ResultActions result = performDeleteWIthPathVariable(USERS + USERNAME, username);

        result.andExpect(status().isOk());

    }
}
