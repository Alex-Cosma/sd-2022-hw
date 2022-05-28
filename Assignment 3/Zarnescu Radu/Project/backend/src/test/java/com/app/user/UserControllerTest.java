package com.app.user;

import com.app.BaseControllerTest;
import com.app.TestCreationFactory;
import com.app.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.app.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserDTO> userDTOs = TestCreationFactory.listOf(UserDTO.class);
        when(userService.allUsers()).thenReturn(userDTOs);

        ResultActions result = performGet(USER);

        result.andExpect(status().isOk()).andExpect(jsonContentToBe(userDTOs));
    }

    @Test
    void create() throws Exception {
        UserDTO userDTO = TestCreationFactory.newUserDTO();
        when(userService.create(userDTO)).thenReturn(userDTO);

        ResultActions result = performPostWithRequestBody(USER + USER_CREATE, userDTO);

        result.andExpect(status().isOk()).andExpect(jsonContentToBe(userDTO));
    }

    @Test
    void edit() throws Exception {
        UserDTO userDTO = TestCreationFactory.newUserDTO();

        userDTO.setUsername("New Username");

        when(userService.edit(userDTO.getId(), userDTO)).thenReturn(userDTO);

        ResultActions result = performPatchWithRequestBodyAndPathVariables(USER + USER_EDIT, userDTO, userDTO.getId());

        result.andExpect(status().isOk()).andExpect(jsonContentToBe(userDTO));
    }
}