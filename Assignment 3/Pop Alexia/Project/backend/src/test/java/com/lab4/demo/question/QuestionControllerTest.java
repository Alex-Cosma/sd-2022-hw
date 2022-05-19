package com.lab4.demo.question;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.question.model.dto.QuestionDTO;
import com.lab4.demo.security.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.QUESTION;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionControllerTest extends BaseControllerTest {

    @InjectMocks
    private QuestionController controller;

    @Mock
    private QuestionService questionService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new QuestionController(questionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allQuestions() throws Exception {
        List<QuestionDTO> questions = TestCreationFactory.listOf(Question.class);
        when(questionService.findAll()).thenReturn(questions);

        ResultActions response = performGet(QUESTION);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(questions));
    }

    @Test
    void filterQuestions() throws Exception {
       QuestionDTO questionDTO1 = QuestionDTO.builder()
               .id(randomLong())
               .statement("ww")
               .category("ww")
               .build();
       QuestionDTO questionDTO2 = QuestionDTO.builder()
               .id(randomLong())
               .statement("aa")
               .category("aa")
               .build();

       List<QuestionDTO> filteredQuestions = List.of(questionDTO2);

       when(questionService.create(questionDTO1)).thenReturn(questionDTO1);
       when(questionService.create(questionDTO2)).thenReturn(questionDTO2);
       when(questionService.filterQuestions("%aa%")).thenReturn(filteredQuestions);

       ResultActions result1 = performPostWithRequestBody(QUESTION, questionDTO1);
        result1.andExpect(status().isOk())
                .andExpect(jsonContentToBe(questionDTO1));

        ResultActions result2 = performPostWithRequestBody(QUESTION, questionDTO2);
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(questionDTO2));

        ResultActions result3 = performGetWithPathVariable(QUESTION+"/filter/{filter}","%aa%" );
        result3.andExpect(status().isOk()).andExpect(jsonContentToBe(filteredQuestions));
    }

    @Test
    void create() throws Exception {
        QuestionDTO reqQuestion = QuestionDTO.builder()
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();

        when(questionService.create(reqQuestion)).thenReturn(reqQuestion);

        ResultActions result = performPostWithRequestBody(QUESTION, reqQuestion);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqQuestion));;
    }

    @Test
    void edit() throws Exception {
        QuestionDTO reqQuestion = QuestionDTO.builder()
                .id(randomLong())
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();

        when(questionService.create(reqQuestion)).thenReturn(reqQuestion);
        ResultActions result = performPostWithRequestBody(QUESTION, reqQuestion);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqQuestion));

        reqQuestion.setStatement("anotherTitle");
        when(questionService.edit(reqQuestion.getId(),reqQuestion)).thenReturn(reqQuestion);
        when(questionService.findById(reqQuestion.getId())).thenReturn(reqQuestion);
        when(questionService.findByQuestionName(reqQuestion.getStatement())).thenReturn(reqQuestion);

        ResultActions result2 = performPutWithRequestBodyAndPathVariables(QUESTION+"/{id}", reqQuestion,reqQuestion.getId());
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Question edited successfully")));
    }

    @Test
    void delete() throws Exception {
        QuestionDTO reqQuestion = QuestionDTO.builder()
                .id(randomLong())
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();

        when(questionService.create(reqQuestion)).thenReturn(reqQuestion);
        ResultActions result = performPostWithRequestBody(QUESTION, reqQuestion);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqQuestion));

        when(questionService.findById(reqQuestion.getId())).thenReturn(reqQuestion);
        doNothing().when(questionService).delete(reqQuestion.getId());

        ResultActions result2 = performDeleteWithPathVariable(QUESTION +"/{id}", reqQuestion.getId());
        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("Question deleted successfully")));
        verify(questionService, times(1)).delete(reqQuestion.getId());
    }
}