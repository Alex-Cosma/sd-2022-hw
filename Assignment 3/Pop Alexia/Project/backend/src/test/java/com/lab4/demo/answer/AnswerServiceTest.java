package com.lab4.demo.answer;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.answer.model.dto.AnswerDTO;
import com.lab4.demo.question.QuestionMapper;
import com.lab4.demo.question.QuestionService;
import com.lab4.demo.question.model.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AnswerServiceTest {

    @InjectMocks
    private AnswerService answerService;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private AnswerMapper answerMapper;

    @Mock
    private QuestionService questionService;

    @Mock
    private QuestionMapper questionMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        answerService = new AnswerService(answerRepository, answerMapper,questionService,questionMapper);
    }

    @Test
    void findById(){
        List<Answer> answers = TestCreationFactory.listOf(Answer.class);
        AnswerDTO q = AnswerDTO.builder()
                .id(answers.get(0).getId())
                .answer(answers.get(0).getAnswer())
                .correct(answers.get(0).isCorrect())
                .build();

        when(answerRepository.findById(answers.get(0).getId())).thenReturn(Optional.of(answers.get(0)));
        when(answerMapper.toDto(answers.get(0))).thenReturn(q);

        AnswerDTO q1 = answerService.findById(answers.get(0).getId());

        assertEquals(q1.getId(),answers.get(0).getId());
    }

    @Test
    void create(){
        AnswerDTO answerDTO = AnswerDTO.builder()
                .id(1L)
                .answer("aa")
                .correct(false)
                .build();
        Question question  = Question.builder()
                .id(1L)
                .statement("q")
                .category("c")
                .build();
        answerDTO.setQuestionId(question.getId());
        Answer answer = Answer.builder()
                .id(1L)
                .answer("aa")
                .correct(false)
                .question(question)
                .build();
        when(answerMapper.fromDto(answerDTO)).thenReturn(answer);
        when(questionMapper.fromDto(questionService.findById(answerDTO.getQuestionId()))).thenReturn(question);
        when(answerMapper.toDto(answerRepository.save(answer))).thenReturn(answerDTO);
        AnswerDTO createdAnswer = answerService.create(answerDTO);
        Assertions.assertEquals(createdAnswer,answerDTO);
    }

    @Test
    void edit(){
        Long id = 1L;
        Answer answer = Answer.builder()
                .id(id)
                .answer("aa")
                .correct(false)
                .build();
        Question question  = Question.builder()
                .id(1L)
                .statement("q")
                .category("c")
                .build();
        answer.setQuestion(question);
        AnswerDTO answerDTO = AnswerDTO.builder()
                .id(id)
                .answer("aa")
                .correct(false)
                .build();

        when(answerMapper.fromDto(answerDTO)).thenReturn(answer);
        when(questionMapper.fromDto(questionService.findById(answerDTO.getQuestionId()))).thenReturn(question);
        when(answerMapper.toDto(answerRepository.save(answer))).thenReturn(answerDTO);
        when(answerRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(answer));
        when(answerMapper.toDto(answerRepository.save(answerMapper.fromDto(answerDTO)))).thenReturn(answerDTO);
        AnswerDTO createdAnswer = answerService.create(answerDTO);
        Assertions.assertEquals("aa",createdAnswer.getAnswer());
        createdAnswer.setAnswer("NewTitle");
        AnswerDTO editedAnswer = answerService.edit(createdAnswer.getId(),createdAnswer);
        Assertions.assertEquals("NewTitle" ,editedAnswer.getAnswer());
    }

    @Test
    void delete(){
        Long id = 1L;
        Answer answer = Answer.builder()
                .id(id)
                .answer("aa")
                .correct(false)
                .build();

        when(answerRepository.save(answer)).thenReturn(answer);
        when(answerRepository.findById(id)).thenReturn(java.util.Optional.of(answer));
        when(answerRepository.existsById(id)).thenReturn(false);

        answerService.delete(id);
        Assertions.assertFalse(answerRepository.existsById(id));
    }
}
