package com.forum.user;

import com.forum.UrlMapping;
import com.forum.BaseControllerTest;
import com.forum.TestCreationFactory;
import com.forum.user.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static com.forum.TestCreationFactory.newUserListDTO;
import static com.forum.TestCreationFactory.randomLong;
import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.when;
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

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void allRegularUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        for(UserListDTO userListDTO : userListDTOs.stream().limit(userListDTOs.size()/2).collect(toList())) {
            userListDTO.setRoles(Set.of("REGULAR"));
        }
        when(userService.findAllRegularUsers()).thenReturn(
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

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.USER + UrlMapping.REGULAR));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(filteredUsers));
    }

    @Test
    void allNonAdminUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        for(UserListDTO userListDTO : userListDTOs.stream().limit(userListDTOs.size()/2).collect(toList())) {
            userListDTO.setRoles(Set.of("REGULAR"));
        }
        when(userService.findAllRegularUsers()).thenReturn(
                userListDTOs.stream()
                        .filter(
                                user -> user.getRoles().stream()
                                        .noneMatch(role -> role.equals("ADMIN"))
                        )
                        .collect(toList())
        );

        List<UserListDTO> filteredUsers = userListDTOs.stream()
                .filter(
                        user -> user.getRoles().stream()
                                .noneMatch(role -> role.equals("ADMIN"))
                )
                .collect(toList());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.USER + UrlMapping.NON_ADMIN));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(filteredUsers));
    }

    @Test
    void create() throws Exception {
        UserListDTO reqUser = newUserListDTO();

        when(userService.create(reqUser)).thenReturn(reqUser);

        ResultActions response = performPostWithRequestBody(UrlMapping.USER, reqUser);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        UserListDTO reqUser = newUserListDTO();

        when(userService.edit(id, reqUser)).thenReturn(reqUser);

        ResultActions response = performPutWithRequestBodyAndPathVariables(UrlMapping.USER + UrlMapping.USER_ID_PART, reqUser, id);
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void delete() throws Exception {
        UserListDTO reqUser = newUserListDTO();
        when(userService.delete(reqUser.getId())).thenReturn(reqUser);

        ResultActions response = performDeleteWithPathVariables(UrlMapping.USER + UrlMapping.USER_ID_PART, reqUser.getId());
        response.andExpect(status().isOk());
    }
}