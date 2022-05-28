package com.lab4.demo.answer;

import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.answer.model.dto.AnswerDTO;
import com.lab4.demo.question.QuestionMapper;
import com.lab4.demo.question.QuestionService;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.question.model.dto.QuestionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class AnswerServiceIntegrationTest {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @BeforeEach
    void setUp() {
        answerRepository.deleteAll();
    }

    @Test
    void findById(){
        Answer answer = Answer.builder()
                .answer("aa")
                .correct(true)
                .build();
        answer = answerRepository.save(answer);
        AnswerDTO answerDTO = answerService.findById(answer.getId());
        Assertions.assertEquals(answer.getId(), answerDTO.getId());
    }

    @Test
    void create(){
        AnswerDTO answer = AnswerDTO.builder()
                .answer("aa")
                .correct(true)
                .build();
        Question question = Question.builder()
                .statement("aa")
                .category("aa")
                .answers(Set.of(answerMapper.fromDto(answer)))
                .build();
        QuestionDTO q = questionService.create(questionMapper.toDto(question));
        answer.setQuestionId(q.getId());
        answer = answerService.create(answer);
        Assertions.assertTrue(answerRepository.findById(answer.getId()).isPresent());
    }

    @Test
    void edit() {
        AnswerDTO answer = AnswerDTO.builder()
                .answer("aa")
                .correct(true)
                .build();
        Question question = Question.builder()
                .statement("aa")
                .category("aa")
                .answers(Set.of(answerMapper.fromDto(answer)))
                .build();
        QuestionDTO q = questionService.create(questionMapper.toDto(question));
        answer.setQuestionId(q.getId());
        answer = answerService.create(answer);
        answer.setAnswer("otherstring");
        answerService.edit(answer.getId(),answer);

        Assertions.assertEquals(answerRepository.findById(answer.getId()).get().getAnswer(), answer.getAnswer());

    }

    @Test
    void delete() {
        QuestionDTO question = QuestionDTO.builder()
                .statement("aa")
                .category("aa")
                .build();
        question = questionService.create(question);
        AnswerDTO answer = AnswerDTO.builder()
                .answer("answerTObeDeleted")
                .correct(true)
                .questionId(question.getId())
                .build();
        answer = answerService.create(answer);
        answerService.delete(answer.getId());
        Assertions.assertTrue(answerRepository.findById(answer.getId()).isEmpty());
    }
}
