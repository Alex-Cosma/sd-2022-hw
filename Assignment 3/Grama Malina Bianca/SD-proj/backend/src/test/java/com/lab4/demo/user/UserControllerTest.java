package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.dto.UserCreateDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.USERS;
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
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                .build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void create() throws Exception {
        UserCreateDTO userCreateDTO = TestCreationFactory.newUserCreateDTO();
        ResponseEntity<?> response = ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        doReturn(response).when(userService).create(userCreateDTO);

        ResultActions result = performPostWithRequestBody(USERS, userCreateDTO);
        result.andExpect(status().isOk());
    }

    @Test
    void getUser() throws Exception {
        UserListDTO userListDTO = TestCreationFactory.newUserListDTO();
        Long id = TestCreationFactory.randomLong();
        userListDTO.setId(id);

        when(userService.getUser(id)).thenReturn(userListDTO);

        ResultActions result = performGetWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTO));

    }

    @Test
    void delete() throws Exception {
        Long id = TestCreationFactory.randomLong();
        doNothing().when(userService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(USERS + ENTITY, id);
        verify(userService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception {
        Long id = TestCreationFactory.randomLong();
        UserListDTO userEditDTO = UserListDTO.builder()
                        .id(id)
                        .name("name")
                        .email(TestCreationFactory.randomEmail())
                        .roles(Set.of(ERole.CUSTOMER.toString()))
                        .build();

        when(userService.edit(id, userEditDTO)).thenReturn(userEditDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS + ENTITY, userEditDTO, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userEditDTO));
    }
}