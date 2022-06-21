package com.example.gymapplication.user;

import com.example.gymapplication.BaseControllerTest;
import com.example.gymapplication.TestCreationFactory;
import com.example.gymapplication.security.dto.MessageResponse;
import com.example.gymapplication.training.TrainingService;
import com.example.gymapplication.training.model.Training;
import com.example.gymapplication.training.model.dto.TrainingDTO;
import com.example.gymapplication.user.model.dto.UserDTO;
import com.example.gymapplication.user.model.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.example.gymapplication.TestCreationFactory.*;
import static com.example.gymapplication.UrlMapping.*;
import static com.example.gymapplication.user.model.ERole.TRAINER;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Mock
    private TrainingService trainingService;

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
    void allTrainers() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password(encodePassword("password"))
                .role(TRAINER.name())
                .build();

        List<UserDTO> trainerListDTOs = new ArrayList<>();
        trainerListDTOs.add(reqUser);
        when(userService.allTrainers()).thenReturn(trainerListDTOs);

        ResultActions result = mockMvc.perform(get(USER+TRAINERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(trainerListDTOs));
    }

    @Test
    void create() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password(encodePassword("password"))
                .build();

        when(userService.create(reqUser)).thenReturn(reqUser);

        ResultActions result = performPostWithRequestBody(USER, reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User created successfully")));
    }

    @Test
    void edit() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .id(randomLong())
                .username("username2")
                .email("email2@email.com")
                .password(encodePassword("password2"))
                .build();
        when(userService.create(reqUser)).thenReturn(reqUser);

        reqUser.setUsername(randomString());
        when(userService.edit(reqUser.getId(),reqUser)).thenReturn(reqUser);
        when(userService.findById(reqUser.getId())).thenReturn(reqUser);

        ResultActions result2 = performPatchWithRequestBodyAndPathVariables(USER+USER_ID_PATH, reqUser,reqUser.getId());
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User edited successfully")));
    }

    @Test
    void delete() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .id(1L)
                .username("username3")
                .email("email3@email.com")
                .password(encodePassword("password3"))
                .build();

        when(userService.create(reqUser)).thenReturn(reqUser);

        doNothing().when(userService).delete(reqUser.getId());

        ResultActions result2 = performDeleteWithPathVariable(USER+USER_ID_PATH, reqUser.getId());
        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("User deleted successfully")));
        verify(userService, times(1)).delete(reqUser.getId());
    }

    @Test
    void addRegularTraining() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .id(1L)
                .username("username3")
                .email("email3@email.com")
                .password(encodePassword("password3"))
                .build();

        TrainingDTO reqTraining = TrainingDTO.builder()
                .id(1L)
                .title("Cardio")
                .type("For kids")
                .date("Wednesday")
                .build();

        when(userService.create(reqUser)).thenReturn(reqUser);
        when(trainingService.create(reqTraining)).thenReturn(reqTraining);
        when(userService.create(reqUser)).thenReturn(reqUser);

        doNothing().when(userService).addRegularTraining(reqUser.getId(), reqTraining.getId());

        ResultActions result = performPatchWithPathVariables(USER+USER_ID_TRAINING_ID_PATH,reqUser.getId(),reqTraining.getId());
        result.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("Training added successfully to user")));

    }
}
