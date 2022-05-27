package com.travel.user;

import com.travel.BaseControllerTest;
import com.travel.TestCreationFactory;
import com.travel.hotel.model.dto.HotelDTO;
import com.travel.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.travel.TestCreationFactory.*;
import static com.travel.UrlMapping.*;
import static org.mockito.Mockito.*;
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
        List<UserDTO> usersDTO = TestCreationFactory.listOf(UserDTO.class);
        when(userService.findAll()).thenReturn(usersDTO);

        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(usersDTO));
    }

    @Test
    void create() throws Exception {
        UserDTO reqUser = newUserDTO();
        when(userService.create(reqUser)).thenReturn(reqUser);

        ResultActions result = performPostWithRequestBody(USER, reqUser);
        result.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        long id =randomLong();
        doNothing().when(userService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(USER + USERS_ID_PART, id);
        verify(userService, times(1)).delete(id);
        result.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception {
        long id =randomLong();
        UserDTO reqUser = newUserDTO();
        when(userService.edit(id, reqUser)).thenReturn(reqUser);

        ResultActions resultActions = performPatchWithRequestBodyAndPathVariable(USER + USERS_ID_PART,reqUser, id);
        resultActions.andExpect(status().isOk());
    }
}
