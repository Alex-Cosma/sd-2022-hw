package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.ERole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.*;
import static java.util.stream.Collectors.toList;
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
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void allEmployees() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        for(UserListDTO userListDTO : userListDTOs.stream().limit(userListDTOs.size()/2).collect(toList())) {
            userListDTO.setRoles(Set.of("REGULAR"));
        }
        when(userService.findAllEmployees()).thenReturn(
                userListDTOs.stream()
                        .filter(
                                user -> user.getRoles().stream()
                                .anyMatch(role -> role.equals("REGULAR"))
                        )
                        .collect(toList())
        );

        List<UserListDTO> filteredUsers = userListDTOs.stream()
                .filter(
                        user -> user.getRoles().stream()
                                .anyMatch(role -> role.equals("REGULAR"))
                )
                .collect(toList());

        ResultActions result = mockMvc.perform(get(USER + EMPLOYEES));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(filteredUsers));
    }

    @Test
    void create() throws Exception {
        UserListDTO reqUser = newUserListDTO();

        when(userService.create(reqUser)).thenReturn(reqUser);

        ResultActions response = performPostWithRequestBody(USER, reqUser);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        UserListDTO reqUser = newUserListDTO();

        when(userService.edit(id, reqUser)).thenReturn(reqUser);

        ResultActions response = performPutWithRequestBodyAndPathVariables(USER + USER_ID_PART, reqUser, id);
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void delete() throws Exception {
        UserListDTO reqUser = newUserListDTO();
        when(userService.delete(reqUser.getId())).thenReturn(reqUser);

        ResultActions response = performDeleteWithPathVariables(USER + USER_ID_PART, reqUser.getId());
        response.andExpect(status().isOk());
    }
}