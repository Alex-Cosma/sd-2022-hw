package com.lab4.demo.quizz;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;

@SpringBootTest
public class QuizzServiceIntegrationTest {

    @Autowired
    private QuizzService quizzService;

    @Autowired
    private QuizzRepository quizzRepository;

    @BeforeEach
    void setUp() {
        quizzRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Quizz> quizzes = TestCreationFactory.listOf(Quizz.class);
        quizzRepository.saveAll(quizzes);

        List<QuizzDTO> all = quizzService.findAll();

        Assertions.assertEquals(quizzes.size(), all.size());
    }

    @Test
    void filter(){
        Quizz quizz1 = Quizz.builder()
                .title("a")
                .description("a")
                .points(1)
                .build();

        Quizz quizz2 = Quizz.builder()
                .title("w")
                .description("w")
                .points(1)
                .build();

        quizzRepository.saveAll(List.of(quizz1, quizz2));

        List<QuizzDTO> all = quizzService.filterQuizzes("%w%");

        Assertions.assertEquals(1, all.size());
    }

    @Test
    void findById(){
        Quizz quizz = Quizz.builder()
                .id(1L)
                .title("a")
                .description("a")
                .points(1)
                .build();
        quizz = quizzRepository.save(quizz);
        Quizz foundQuizz = quizzService.findById(quizz.getId());
        Assertions.assertEquals(quizz.getId(), foundQuizz.getId());
    }
    @Test
    void findByQuizzTitle(){
        Quizz quizz = Quizz.builder()
                .title("a")
                .description("a")
                .points(1)
                .build();
        quizz = quizzRepository.save(quizz);
        QuizzDTO quizzDTO = quizzService.findByQuizzTitle("a");
        Assertions.assertEquals(quizz.getId(), quizzDTO.getId());
    }

    @Test
    void create(){
        QuizzDTO quizz = QuizzDTO.builder()
                .title("a")
                .description("a")
                .points(1)
                .build();
        quizz = quizzService.create(quizz);
        Assertions.assertTrue(quizzRepository.findById(quizz.getId()).isPresent());
    }

    @Test
    void edit() {
        QuizzDTO quizz = QuizzDTO.builder()
                .id(randomLong())
                .title("a")
                .description("a")
                .points(1)
                .build();
        quizz = quizzService.create(quizz);
        quizz.setTitle("otherstring");
        quizzService.edit(quizz.getId(),quizz);

        Assertions.assertEquals(quizzRepository.findById(quizz.getId()).get().getTitle(), quizz.getTitle());

    }

    @Test
    void delete() {
        QuizzDTO quizz = QuizzDTO.builder()
                .title("a")
                .description("a")
                .points(1)
                .build();
        quizz = quizzService.create(quizz);
        quizzService.delete(quizz.getId());
        Assertions.assertTrue(quizzRepository.findById(quizz.getId()).isEmpty());
    }
}
