package com.lab4.demo.question;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.question.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void findAll() {
        List<Question> questions = TestCreationFactory.listOf(Question.class);
        repository.saveAll(questions);
        List<Question> all = repository.findAll();
        assertEquals(questions.size(), all.size());
    }

    @Test
    public void filterQuestions(){
        Question question1 = Question.builder()
                .statement("aa")
                .category("aa")
                .answers(Set.of(Answer.builder().answer("aa").correct(false).build()))
                .build();

        Question question2 = Question.builder()
                .statement("ww")
                .category("ww")
                .answers(Set.of(Answer.builder().answer("ww").correct(false).build()))
                .build();

        repository.save(question1);
        repository.save(question2);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Question> all = repository.findAllByCategoryLikeOrStatementLike("%a%", "%a%",pageRequest);

        assertEquals(1, all.getNumberOfElements());
    }

    @Test
    public void findById() {
        Question question = repository.save(Question.builder()
                .statement("aa")
                .category("aa")
                .answers(null)
                .build());
        Question questionFound = repository.findById(question.getId()).get();

        assertEquals(question.getId(), questionFound.getId());
    }

    @Test
    public void findByStatement(){
        Question question = repository.save(Question.builder()
                .statement("aa")
                .category("aa")
                .answers(null)
                .build());
        Question questionFound = repository.findByStatement(question.getStatement());

        assertEquals(question.getStatement(), questionFound.getStatement());
    }

    @Test
    public void create() {
        Question questionSaved = repository.save(Question.builder()
                .statement("aa")
                .category("aa")
                .answers(null)
                .build());

        assertNotNull(questionSaved);
        assertEquals(1,repository.findAll().size());
        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(Question.builder().build());
        });
    }

    @Test
    public void edit(){
        Question question = repository.save(Question.builder()
                .statement("aa")
                .category("aa")
                .answers(Set.of(Answer.builder().answer("aa").correct(false).build()))
                .build());
        question.setStatement("newtitle");
        question = repository.save(question);

        assertEquals(repository.findById(question.getId()).get().getStatement(), "newtitle");
    }

    @Test
    public void delete(){
        Question question = repository.save(Question.builder()
                .statement("aa")
                .category("aa")
                .answers(null)
                .build());
        repository.delete(question);
        assertTrue(repository.findAll().isEmpty());
    }
}