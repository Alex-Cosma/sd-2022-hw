package com.lab4.demo.quizz;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.quizz.model.Quizz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QuizzRepositoryTest {

    @Autowired
    private QuizzRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void findAll() {
        List<Quizz> quizzes = TestCreationFactory.listOf(Quizz.class);
        repository.saveAll(quizzes);
        List<Quizz> all = repository.findAll();
        assertEquals(quizzes.size(), all.size());
    }

    @Test
    public void filterQuizzes(){
        Quizz quizz1 = Quizz.builder()
                .title("aa")
                .description("aa")
                .questions(null)
                .build();

        Quizz quizz2 = Quizz.builder()
                .title("ww")
                .description("ww")
                .questions(null)
                .build();

        repository.save(quizz1);
        repository.save(quizz2);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Quizz> all = repository.findAllByTitleLikeOrDescriptionLike("%a%", "%a%",pageRequest);

        assertEquals(1, all.getNumberOfElements());
    }

    @Test
    public void findById() {
        Quizz quizz = repository.save(Quizz.builder()
                .title("aa")
                .description("aa")
                .questions(null)
                .build());
        Quizz quizzFound = repository.findById(quizz.getId()).get();

        assertEquals(quizz.getId(), quizzFound.getId());
    }

    @Test
    public void findByQuizzTitle(){
        Quizz quizz = repository.save(Quizz.builder()
                .title("aa")
                .description("aa")
                .questions(null)
                .build());
        Quizz quizzFound = repository.findByTitle(quizz.getTitle());

        assertEquals(quizz.getTitle(), quizzFound.getTitle());
    }

    @Test
    public void create() {
        Quizz quizzSaved = repository.save(Quizz.builder()
                .title("aa")
                .description("aa")
                .questions(null)
                .build());

        assertNotNull(quizzSaved);
        assertEquals(1,repository.findAll().size());
        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(Quizz.builder().build());
        });
    }

    @Test
    public void edit(){
        Quizz quizz = repository.save(Quizz.builder()
                .title("aa")
                .description("aa")
                .questions(null)
                .quizzSessions(null)
                .build());
        quizz.setTitle("newtitle");
        quizz = repository.save(quizz);
        assertEquals(repository.findById(quizz.getId()).get().getTitle(), "newtitle");
    }

    @Test
    public void delete(){
        Quizz quizz = repository.save(Quizz.builder()
                .title("aa")
                .description("aa")
                .questions(null)
                .build());
        repository.delete(quizz);
        assertTrue(repository.findAll().isEmpty());
    }

}
