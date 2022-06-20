package com.example.gymapplication.training;

import com.example.gymapplication.BaseControllerTest;
import com.example.gymapplication.TestCreationFactory;
import com.example.gymapplication.security.dto.MessageResponse;
import com.example.gymapplication.training.model.dto.TrainingDTO;
import com.example.gymapplication.user.UserService;
import com.example.gymapplication.user.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.gymapplication.TestCreationFactory.*;
import static com.example.gymapplication.UrlMapping.*;
import static com.example.gymapplication.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TrainingControllerTest extends BaseControllerTest {
    @InjectMocks
    private TrainingController controller;

    @Mock
    private TrainingService trainingService;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new TrainingController(trainingService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void allTrainings() throws Exception {
        List<TrainingDTO> trainingList = TestCreationFactory.listOf(TrainingDTO.class);
        when(trainingService.findAll()).thenReturn(trainingList);

        ResultActions response = performGet(TRAININGS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(trainingList));
    }

    @Test
    void filterTrainings() throws Exception{
        TrainingDTO trainingDTO1 = TrainingDTO.builder()
                .id(randomLong())
                .title("title1")
                .type("type1")
                .date("date1")
                .build();

        TrainingDTO trainingDTO2 = TrainingDTO.builder()
                .id(randomLong())
                .title("amazingTitle")
                .type("amazingType")
                .date("amazingDate")
                .build();

        List<TrainingDTO> filteredTrainings = List.of(trainingDTO2);

        when(trainingService.create(trainingDTO1)).thenReturn(trainingDTO1);
        when(trainingService.create(trainingDTO2)).thenReturn(trainingDTO2);
        when(trainingService.filterTrainings("%amazing%")).thenReturn(filteredTrainings);

        ResultActions result3 = performGetWithPathVariable(TRAININGS+"/filter/{filter}","%amazing%" );
        result3.andExpect(status().isOk()).andExpect(jsonContentToBe(filteredTrainings));
    }

    @Test
    void create() throws Exception{
        TrainingDTO reqTraining = TrainingDTO.builder()
                .title(randomString())
                .type(randomString())
                .date(randomString())
                .build();

        when(trainingService.create(reqTraining)).thenReturn(reqTraining);

        ResultActions result = performPostWithRequestBody(TRAININGS, reqTraining);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Training created successfully")));;
    }

    @Test
    void edit() throws Exception{
        TrainingDTO reqTraining = TrainingDTO.builder()
                .id(randomLong())
                .title(randomString())
                .type(randomString())
                .date(randomString())
                .build();

        when(trainingService.create(reqTraining)).thenReturn(reqTraining);

        reqTraining.setTitle("newTitle");
        when(trainingService.edit(reqTraining.getId(),reqTraining)).thenReturn(reqTraining);
        when(trainingService.findById(reqTraining.getId())).thenReturn(reqTraining);
        when(trainingService.findByTitle(reqTraining.getTitle())).thenReturn(reqTraining);

        ResultActions result2 = performPatchWithRequestBodyAndPathVariables(TRAININGS+TRAINING_ID_PATH, reqTraining,reqTraining.getId());
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Training edited successfully")));
    }

    @Test
    void delete() throws Exception{
        TrainingDTO reqTraining = TrainingDTO.builder()
                .id(randomLong())
                .title(randomString())
                .type(randomString())
                .date(randomString())
                .build();

        when(trainingService.create(reqTraining)).thenReturn(reqTraining);

        doNothing().when(trainingService).delete(reqTraining.getId());

        ResultActions result2 = performDeleteWithPathVariable(TRAININGS+TRAINING_ID_PATH, reqTraining.getId());
        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("Training deleted successfully")));
        verify(trainingService, times(1)).delete(reqTraining.getId());
    }

    /*
    @Test
    void exportReport() throws Exception {
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
                .location("Calea Turzii nr. 82")
                .build();

        when(userService.create(reqUser)).thenReturn(reqUser);
        when(trainingService.create(reqTraining)).thenReturn(reqTraining);
        when(userService.create(reqUser)).thenReturn(reqUser);

        ResultActions result = performGetWithPathVariable(TRAININGS+EXPORT_REPORT,PDF,"username3");
        result.andExpect(status().isOk());
    }
    */

}
