package com.lab4.demo.question;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.question.model.dto.QuestionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.randomLong;

@SpringBootTest
class QuestionServiceIntegrationTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        questionRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Question> questions = TestCreationFactory.listOf(Question.class);
        questionRepository.saveAll(questions);

        List<QuestionDTO> all = questionService.findAll();

        Assertions.assertEquals(questions.size(), all.size());
    }

    @Test
    void filter(){
        Question question1 = Question.builder()
                .statement("aa")
                .category("aa")
                .answers(Set.of(Answer.builder().answer("aa").correct(false).build()))
                .build();

        Question question2 = Question.builder()
                .statement("ww")
                .category("ww")
                .answers(Set.of(Answer.builder().answer("bb").correct(false).build()))
                .build();

        questionRepository.saveAll(List.of(question1, question2));

        List<QuestionDTO> all = questionService.filterQuestions("%w%");

        Assertions.assertEquals(1, all.size());
    }

    @Test
    void findById(){
        Question question = Question.builder()
                .id(1L)
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();
        question = questionRepository.save(question);
        QuestionDTO questionDTO = questionService.findById(question.getId());
        Assertions.assertEquals(question.getId(), questionDTO.getId());
    }
    @Test
    void findByQuestionName(){
        Question question = Question.builder()
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();
        question = questionRepository.save(question);
        QuestionDTO questionDTO = questionService.findByQuestionName("aa");
        Assertions.assertEquals(question.getId(), questionDTO.getId());
    }

    @Test
    void create(){
        QuestionDTO question = QuestionDTO.builder().statement("aa")
                .category("aa")
                .answers(null)
                .build();
        question = questionService.create(question);
        Assertions.assertTrue(questionRepository.findById(question.getId()).isPresent());
    }

    @Test
    void edit() {
        QuestionDTO question = QuestionDTO.builder()
                .id(randomLong())
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();
        question = questionService.create(question);
        question.setStatement("otherstring");
        questionService.edit(question.getId(),question);

        Assertions.assertEquals(questionRepository.findById(question.getId()).get().getStatement(), question.getStatement());

    }

    @Test
    void delete() {
        QuestionDTO question = QuestionDTO.builder()
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();
        question = questionService.create(question);
        questionService.delete(question.getId());
        Assertions.assertTrue(questionRepository.findById(question.getId()).isEmpty());
    }

}