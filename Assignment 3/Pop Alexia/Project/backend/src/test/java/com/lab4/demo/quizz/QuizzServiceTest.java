package com.lab4.demo.quizz;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.answer.model.dto.AnswerDTO;
import com.lab4.demo.question.QuestionMapper;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.question.model.dto.QuestionDTO;
import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class QuizzServiceTest {
    @InjectMocks
    private QuizzService quizzService;

    @Mock
    private QuizzRepository quizzRepository;

    @Mock
    private QuizzMapper quizzMapper;

    @Mock
    private QuestionMapper questionMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quizzService = new QuizzService(quizzRepository, quizzMapper,questionMapper);
    }

    @Test
    void findAll() {
        List<Quizz> quizzes = TestCreationFactory.listOf(Quizz.class);
        when(quizzRepository.findAll()).thenReturn(quizzes);

        List<QuizzDTO> all = quizzService.findAll();

        assertEquals(quizzes.size(), all.size());
    }

    @Test
    void findById(){
        List<Quizz> quizzes = TestCreationFactory.listOf(Quizz.class);
        QuizzDTO q = QuizzDTO.builder()
                .id(quizzes.get(0).getId())
                .title(quizzes.get(0).getTitle())
                .description(quizzes.get(0).getDescription())
                .questions(null)
                .build();

        when(quizzRepository.findById(quizzes.get(0).getId())).thenReturn(Optional.of(quizzes.get(0)));
        when(quizzMapper.toDto(quizzes.get(0))).thenReturn(q);

        Quizz q1 = quizzService.findById(quizzes.get(0).getId());

        assertEquals(q1.getId(),quizzes.get(0).getId());
    }

    @Test
    void findByQuizzTitle(){
        List<Quizz> quizzes = TestCreationFactory.listOf(Quizz.class);
        QuizzDTO q = QuizzDTO.builder()
                .id(quizzes.get(0).getId())
                .title(quizzes.get(0).getTitle())
                .description(quizzes.get(0).getDescription())
                .questions(null)
                .build();

        when(quizzRepository.findByTitle(quizzes.get(0).getTitle())).thenReturn(quizzes.get(0));
        when(quizzMapper.toDto(quizzes.get(0))).thenReturn(q);

        QuizzDTO q1 = quizzService.findByQuizzTitle(quizzes.get(0).getTitle());

        assertEquals(q1.getTitle(),quizzes.get(0).getTitle());
    }

    @Test
    void filterQuizzes(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        String filter = "a";
        Page<Quizz> quizzes1 = new PageImpl(List.of(Quizz.builder().title("a").description("a").build()),pageRequest,10);
        when(quizzRepository.findAllByTitleLikeOrDescriptionLike("%" +filter + "%","%" + filter + "%",pageRequest)).thenReturn(quizzes1);

        List<QuizzDTO> all = quizzService.filterQuizzes(filter);

        assertEquals(all.size(), 1);
    }

    @Test
    void create(){
        QuizzDTO quizz = QuizzDTO.builder()
                .id(1L)
                .title("a")
                .description("a")
                .build();
        when(quizzMapper.toDto(quizzRepository.save(quizzMapper.fromDto(quizz)))).thenReturn(quizz);
        QuizzDTO createdQuizz = quizzService.create(quizz);
        Assertions.assertEquals(createdQuizz,quizz);
    }

    @Test
    void edit(){
        Long id = 1L;
        Quizz quizz = Quizz.builder()
                .id(id)
                .title("a")
                .description("a")
                .questions(Set.of(Question.builder().id(1L).statement("a").category("a").answers(Set.of(Answer.builder().answer("aa").correct(false).build())).build()))
                .build();
        QuizzDTO quizzDTO = QuizzDTO.builder()
                .id(id)
                .title("a")
                .description("a")
                .questions(Set.of(QuestionDTO.builder().id(1L).statement("a").category("a").answers(Set.of(AnswerDTO.builder().answer("aa").correct(false).build())).build()))
                .build();

        when(quizzRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(quizz));
        when(quizzMapper.toDto(quizzRepository.save(quizzMapper.fromDto(quizzDTO)))).thenReturn(quizzDTO);
        QuizzDTO createdQuizz = quizzService.create(quizzDTO);
        Assertions.assertEquals("a",createdQuizz.getTitle());
        createdQuizz.setTitle("NewTitle");
        QuizzDTO editedQuizz = quizzService.edit(createdQuizz.getId(),createdQuizz);
        Assertions.assertEquals("NewTitle" ,editedQuizz.getTitle());

    }

    @Test
    void delete(){
        Long id = 1L;
        Quizz quizz = Quizz.builder()
                .id(id)
                .title("a")
                .description("a")
                .build();

        when(quizzRepository.save(quizz)).thenReturn(quizz);
        when(quizzRepository.findById(id)).thenReturn(java.util.Optional.of(quizz));
        when(quizzRepository.existsById(id)).thenReturn(false);

        quizzService.delete(id);
        Assertions.assertFalse(quizzRepository.existsById(id));
    }
}
