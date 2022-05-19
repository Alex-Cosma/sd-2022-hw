package com.example.youtubeish.user;

import com.example.youtubeish.BaseControllerTest;
import com.example.youtubeish.TestCreationFactory;
import com.example.youtubeish.security.dto.MessageResponse;
import com.example.youtubeish.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.youtubeish.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserDTO> userListDTOs = TestCreationFactory.listOf(UserDTO.class);
        when(userController.allUsers()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USER + GET_USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void deleteUser() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .email("myemail@aaa.com")
                .username("username")
                .password("password")
                .build();
        when(userService.create(userDTO)).thenReturn(userDTO);
        ResultActions result = performPostWithRequestBody(USER + ADD_USER, userDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User registered successfully!")));

        doNothing().when(userService).deleteById(userDTO.getId());
        ResultActions deleteAction = performDeleteWIthPathVariable(USER + DELETE_USER, userDTO.getId());
        deleteAction.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("User deleted successfully!")));
        verify(userService, times(1)).deleteById(userDTO.getId());
    }

    @Test
    void addUser() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .email("myemail@aaa.com")
                .username("username")
                .password("password")
                .build();
        userController.create(userDTO);
        assertNotNull(userRepository.findByUsername("username"));
    }

    @Test
    void edit() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .email("myemail@aaa.com")
                .username("username")
                .password("password")
                .build();
        userController.create(userDTO);

        //when(userService.create(userDTO)).thenReturn(userDTO);
        ResultActions result = performPostWithRequestBody(USER + ADD_USER, userDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User registered successfully!")));

        userDTO.setUsername("new username");
        userDTO.setEmail("newemail@email.com");

        when(userController.edit(userDTO.getId(), userDTO)).thenReturn(userDTO);
        when(userController.findUserById(userDTO.getId())).thenReturn(userDTO);

        ResultActions getEditedUserAction = performGetWithPathVariable(USER + GET_USER, userDTO.getId());
        getEditedUserAction.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTO));
    }

    @Test
    void findUserById() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .email("myemail@aaa.com")
                .username("username")
                .password("password")
                .build();
        when(userService.create(userDTO)).thenReturn(userDTO);
        ResultActions result = performPostWithRequestBody(USER + ADD_USER, userDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User registered successfully!")));
        when(userController.findUserById(userDTO.getId())).thenReturn(userDTO);
        ResultActions getUserByIdAction = performGetWithPathVariable(USER + GET_USER, userDTO.getId());
        getUserByIdAction.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTO));
    }

    @Test
    void duplicateUsername() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .email("myemail@aaa.com")
                .username("username")
                .password("password")
                .build();
        ResultActions result = performPostWithRequestBody(USER + ADD_USER, userDTO);

        UserDTO userDTO2 = UserDTO.builder()
                .id(2L)
                .email("myemail@aaa.com")
                .username("username")
                .password("password")
                .build();
        userController.create(userDTO);
        ResultActions result2 = performPostWithRequestBody(USER + ADD_USER, userDTO2);
        result2
                .andExpect(jsonContentToBe(new MessageResponse("Error: Username is already taken!")));
    }
}
