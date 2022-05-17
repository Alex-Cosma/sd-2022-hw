package com.lab4.demo.quizzSession;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.UrlMapping.QUIZZ_SESSION;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuizzSessionControllerTest extends BaseControllerTest {

    @InjectMocks
    private QuizzSessionController controller;

    @Mock
    private QuizzSessionService quizzSessionService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new QuizzSessionController(quizzSessionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allQuizzSessions() throws Exception {
        List<QuizzSessionDTO> quizzSessions = TestCreationFactory.listOf(QuizzSessionDTO.class);
        when(quizzSessionService.findAll()).thenReturn(quizzSessions);

        ResultActions response = performGet(QUIZZ_SESSION);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(quizzSessions));
    }

    @Test
    void create() throws Exception {
        QuizzSessionDTO reqquizz = QuizzSessionDTO.builder()
                .score(1)
                .build();

        when(quizzSessionService.create(reqquizz)).thenReturn(reqquizz);

        ResultActions result = performPostWithRequestBody(QUIZZ_SESSION, reqquizz);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqquizz));;
    }

    @Test
    void exportReport() throws Exception {
        QuizzSessionDTO reqquizz = QuizzSessionDTO.builder()
                .score(1)
                .build();
    }

}
