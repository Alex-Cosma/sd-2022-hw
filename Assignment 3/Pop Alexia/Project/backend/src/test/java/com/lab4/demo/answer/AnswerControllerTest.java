package com.lab4.demo.answer;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.answer.model.dto.AnswerDTO;
import com.lab4.demo.security.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.ANSWER;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnswerControllerTest extends BaseControllerTest {

    @InjectMocks
    private AnswerController controller;

    @Mock
    private AnswerService answerService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new AnswerController(answerService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void create() throws Exception {
        AnswerDTO reqAnswer = AnswerDTO.builder()
                .answer("aa")
                .correct(true)
                .build();

        when(answerService.create(reqAnswer)).thenReturn(reqAnswer);
        ResultActions result = performPostWithRequestBody(ANSWER, reqAnswer);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqAnswer));
    }

    @Test
    void edit() throws Exception {
        AnswerDTO reqAnswer = AnswerDTO.builder()
                .id(randomLong())
                .answer("aa")
                .correct(true)
                .build();

        when(answerService.create(reqAnswer)).thenReturn(reqAnswer);
        ResultActions result = performPostWithRequestBody(ANSWER, reqAnswer);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqAnswer));

        reqAnswer.setAnswer("anotherTitle");
        when(answerService.edit(reqAnswer.getId(),reqAnswer)).thenReturn(reqAnswer);
        when(answerService.findById(reqAnswer.getId())).thenReturn(reqAnswer);

        ResultActions result2 = performPutWithRequestBodyAndPathVariables(ANSWER+"/{id}", reqAnswer,reqAnswer.getId());
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Answer edited successfully")));
    }

    @Test
    void delete() throws Exception {
        AnswerDTO reqAnswer = AnswerDTO.builder()
                .id(randomLong())
                .answer("aa")
                .correct(true)
                .build();

        when(answerService.create(reqAnswer)).thenReturn(reqAnswer);
        ResultActions result = performPostWithRequestBody(ANSWER, reqAnswer);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqAnswer));

        when(answerService.findById(reqAnswer.getId())).thenReturn(reqAnswer);
        doNothing().when(answerService).delete(reqAnswer.getId());

        ResultActions result2 = performDeleteWithPathVariable(ANSWER +"/{id}", reqAnswer.getId());
        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("Answer deleted successfully")));
        verify(answerService, times(1)).delete(reqAnswer.getId());
    }
}
