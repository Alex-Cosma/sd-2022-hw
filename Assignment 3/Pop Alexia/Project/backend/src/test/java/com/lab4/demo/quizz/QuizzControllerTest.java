package com.lab4.demo.quizz;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import com.lab4.demo.security.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.QUIZZ;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuizzControllerTest extends BaseControllerTest {

    @InjectMocks
    private QuizzController controller;

    @Mock
    private QuizzService quizzService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new QuizzController(quizzService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allQuizzes() throws Exception {
        List<QuizzDTO> quizzes = TestCreationFactory.listOf(QuizzDTO.class);
        when(quizzService.findAll()).thenReturn(quizzes);

        ResultActions response = performGet(QUIZZ);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(quizzes));
    }

    @Test
    void filterQuizzes() throws Exception {
        QuizzDTO quizzDTO1 = QuizzDTO.builder()
                .id(randomLong())
                .title("ww")
                .description("ww")
                .points(1)
                .build();
        QuizzDTO quizzDTO2 = QuizzDTO.builder()
                .id(randomLong())
                .title("aa")
                .description("aa")
                .points(1)
                .build();

        List<QuizzDTO> filteredQuizzes = List.of(quizzDTO2);

        when(quizzService.create(quizzDTO1)).thenReturn(quizzDTO1);
        when(quizzService.create(quizzDTO2)).thenReturn(quizzDTO2);
        when(quizzService.filterQuizzes("%aa%")).thenReturn(filteredQuizzes);

        ResultActions result1 = performPostWithRequestBody(QUIZZ, quizzDTO1);
        result1.andExpect(status().isOk())
                .andExpect(jsonContentToBe(quizzDTO1));

        ResultActions result2 = performPostWithRequestBody(QUIZZ, quizzDTO2);
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(quizzDTO2));

        ResultActions result3 = performGetWithPathVariable(QUIZZ+"/filter/{filter}","%aa%" );
        result3.andExpect(status().isOk()).andExpect(jsonContentToBe(filteredQuizzes));
    }

    @Test
    void create() throws Exception {
        QuizzDTO reqquizz = QuizzDTO.builder()
                .title("ww")
                .description("ww")
                .points(1)
                .build();

        when(quizzService.create(reqquizz)).thenReturn(reqquizz);

        ResultActions result = performPostWithRequestBody(QUIZZ, reqquizz);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqquizz));;
    }

    @Test
    void edit() throws Exception {

        QuizzDTO reqquizz = QuizzDTO.builder()
                .id(randomLong())
                .title("aaaa")
                .description("aaaaa")
                .points(1)
                .build();

        Quizz quizz = Quizz.builder()
                .id(reqquizz.getId())
                .title("aaaa")
                .description("aaaaa")
                .points(1)
                .build();

        when(quizzService.create(reqquizz)).thenReturn(reqquizz);
        ResultActions result = performPostWithRequestBody(QUIZZ, reqquizz);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqquizz));

        reqquizz.setDescription("anotherDesc");
        when(quizzService.edit(reqquizz.getId(),reqquizz)).thenReturn(reqquizz);
        when(quizzService.findById(reqquizz.getId())).thenReturn(quizz);
        when(quizzService.findByQuizzTitle(reqquizz.getTitle())).thenReturn(reqquizz);

        ResultActions result2 = performPutWithRequestBodyAndPathVariables(QUIZZ+"/{id}", reqquizz,reqquizz.getId());
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Quizz edited successfully")));
    }

    @Test
    void delete() throws Exception {
        QuizzDTO reqquizz = QuizzDTO.builder()
                .id(randomLong())
                .title("ww")
                .description("ww")
                .points(1)
                .build();

        when(quizzService.create(reqquizz)).thenReturn(reqquizz);
        ResultActions result = performPostWithRequestBody(QUIZZ, reqquizz);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqquizz));

        doNothing().when(quizzService).delete(reqquizz.getId());

        ResultActions result2 = performDeleteWithPathVariable(QUIZZ +"/{id}", reqquizz.getId());
        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("Quizz deleted successfully")));
        verify(quizzService, times(1)).delete(reqquizz.getId());
    }
}
