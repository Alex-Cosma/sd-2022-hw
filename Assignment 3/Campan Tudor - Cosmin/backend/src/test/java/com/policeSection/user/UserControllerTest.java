package com.policeSection.user;

import com.policeSection.BaseControllerTest;
import com.policeSection.TestCreationFactory;
import com.policeSection.security.dto.MessageResponse;
import com.policeSection.user.dto.UserDTO;
import com.policeSection.user.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.policeSection.UrlMapping.USER;
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
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }
    @Test
    void create() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .username("username")
                .email("we@email.com")
                .password(" ")
                .build();

        when(userService.newUser(reqUser)).thenReturn(true);

        ResultActions result = performPostWithRequestBody(USER, reqUser);
        result.andExpect(status().isOk());

    }

    @Test
    void edit() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .id(333L)
                .username("whatever3")
                .email("we2@email.com")
                .password(" ")
                .build();
        when(userService.newUser(reqUser)).thenReturn(true);
        ResultActions result = performPostWithRequestBody(USER, reqUser);
        result.andExpect(status().isOk());

        reqUser.setUsername("test");
        when(userService.edit(reqUser.getId(),reqUser)).thenReturn(reqUser);

        ResultActions result2 = performPutWithRequestBodyAndPathVariables(USER+"/{id}", reqUser,reqUser.getId());
        result2.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .id(1L)
                .username("whatever2")
                .email("we3@email.com")
                .password("   ")
                .build();

        when(userService.newUser(reqUser)).thenReturn(true);
        ResultActions result = performPostWithRequestBody(USER, reqUser);
        result.andExpect(status().isOk());

        doNothing().when(userService).delete(reqUser.getId());

        ResultActions result2 = performDeleteWIthPathVariable(USER+"/{id}", reqUser.getId());
        result2.andExpect(status().isOk());
        verify(userService, times(1)).delete(reqUser.getId());
    }


}