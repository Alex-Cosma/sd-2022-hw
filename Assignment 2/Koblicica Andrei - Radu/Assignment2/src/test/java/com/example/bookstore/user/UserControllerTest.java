package com.example.bookstore.user;

import com.example.bookstore.BaseControllerTest;
import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.report.ReportServiceFactory;
import com.example.bookstore.security.AuthService;
import com.example.bookstore.security.dto.MessageResponse;
import com.example.bookstore.security.dto.SignupRequest;
import com.example.bookstore.user.dto.UserListDTO;
import com.example.bookstore.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.bookstore.UrlMapping.USERS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
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
        List<UserListDTO> users = TestCreationFactory.listOf(User.class);
        when(userService.allUsersForList()).thenReturn(users);

        ResultActions response = performGet(USERS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));

    }

    @Test
    void create() throws Exception {
        UserListDTO userListDTO = UserListDTO.builder()
                .email("test@test.com")
                .name("test")
                .password("WooHoo1!")
                .build();
        ResultActions result = performPostWithRequestBody(USERS,userListDTO);
        result.andExpect(status().isOk());
    }






}
