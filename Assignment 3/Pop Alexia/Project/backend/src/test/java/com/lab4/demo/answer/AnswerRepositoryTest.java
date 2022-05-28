package com.lab4.demo.answer;

import com.lab4.demo.answer.model.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void findById() {
        Answer answer = repository.save(Answer.builder()
                .answer("aa")
                .correct(false)
                .build());
        Answer answerFound = repository.findById(answer.getId()).get();

        assertEquals(answer.getId(), answerFound.getId());
    }

    @Test
    public void create() {
        Answer answerSaved = repository.save(Answer.builder()
                .answer("aa")
                .correct(false)
                .build());

        assertNotNull(answerSaved);
        assertEquals(1,repository.findAll().size());
        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(Answer.builder().build());
        });
    }

    @Test
    public void edit(){
        Answer answer = repository.save(Answer.builder()
                .answer("aa")
                .correct(false)
                .build());
        answer.setAnswer("newtitle");
        answer = repository.save(answer);

        assertEquals(repository.findById(answer.getId()).get().getAnswer(), "newtitle");
    }

    @Test
    public void delete(){
        Answer answer = repository.save(Answer.builder()
                .answer("aa")
                .correct(false)
                .build());
        repository.delete(answer);
        assertTrue(repository.findAll().isEmpty());
    }
}
