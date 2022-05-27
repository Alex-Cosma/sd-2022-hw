package com.example.backend.user;

import com.example.backend.BaseControllerTest;
import com.example.backend.TestCreationFactory;
import com.example.backend.user.dto.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.backend.TestCreationFactory.newUserDTO;
import static com.example.backend.TestCreationFactory.randomLong;
import static com.example.backend.UrlMapping.USER;
import static com.example.backend.UrlMapping.USER_ID_PART;
import static org.mockito.Mockito.doNothing;
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
        List<UserDTO> userListDTOs = TestCreationFactory.listOf(UserDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void edit() throws Exception {
        long userId = randomLong();
        UserDTO user1 = newUserDTO();
        user1.setId(userId);

        when(userService.edit(user1.getId(),user1)).thenReturn(user1);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(USER + USER_ID_PART,user1,user1.getId());
        result.andExpect(status().isOk());


    }

    @Test
    void delete() throws Exception {
        long userId = randomLong();
        UserDTO user1 = newUserDTO();
        user1.setId(userId);

        doNothing().when(userService).delete(user1.getId());

        ResultActions result = performDeleteWIthPathVariable(USER + USER_ID_PART, user1.getId());
    }
}