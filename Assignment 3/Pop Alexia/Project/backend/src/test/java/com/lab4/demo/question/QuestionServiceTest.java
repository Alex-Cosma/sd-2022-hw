package com.lab4.demo.question;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.question.model.dto.QuestionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionService = new QuestionService(questionRepository, questionMapper);
    }

    @Test
    void findAll() {
        List<Question> questions = TestCreationFactory.listOf(Question.class);
        when(questionRepository.findAll()).thenReturn(questions);

        List<QuestionDTO> all = questionService.findAll();

        assertEquals(questions.size(), all.size());
    }

    @Test
    void findById(){
        List<Question> questions = TestCreationFactory.listOf(Question.class);
        QuestionDTO q = QuestionDTO.builder()
                        .id(questions.get(0).getId())
                        .category(questions.get(0).getCategory())
                        .statement(questions.get(0).getStatement())
                        .answers(null)
                        .build();

        when(questionRepository.findById(questions.get(0).getId())).thenReturn(Optional.of(questions.get(0)));
        when(questionMapper.toDto(questions.get(0))).thenReturn(q);

        QuestionDTO q1 = questionService.findById(questions.get(0).getId());

        assertEquals(q1.getId(),questions.get(0).getId());
    }

    @Test
    void findByQuestionName(){
        List<Question> questions = TestCreationFactory.listOf(Question.class);
        QuestionDTO q = QuestionDTO.builder()
                .id(questions.get(0).getId())
                .category(questions.get(0).getCategory())
                .statement(questions.get(0).getStatement())
                .answers(null)
                .build();

        when(questionRepository.findByStatement(questions.get(0).getStatement())).thenReturn(questions.get(0));
        when(questionMapper.toDto(questions.get(0))).thenReturn(q);

        QuestionDTO q1 = questionService.findByQuestionName(questions.get(0).getStatement());

        assertEquals(q1.getStatement(),questions.get(0).getStatement());
    }

    @Test
    void filterQuestions(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        String filter = "a";
        Page<Question> questions1 = new PageImpl(List.of(Question.builder().category("a").statement("a").build()),pageRequest,10);
        when(questionRepository.findAllByCategoryLikeOrStatementLike("%" +filter + "%","%" + filter + "%",pageRequest)).thenReturn(questions1);

        List<QuestionDTO> all = questionService.filterQuestions(filter);

        assertEquals(all.size(), 1);
    }

    @Test
    void create(){
        QuestionDTO question = QuestionDTO.builder()
                .id(1L)
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();
        when(questionMapper.toDto(questionRepository.save(questionMapper.fromDto(question)))).thenReturn(question);
        QuestionDTO createdQuestion = questionService.create(question);
        Assertions.assertEquals(createdQuestion,question);
    }

    @Test
    void edit(){
        Long id = 1L;
        Question question = Question.builder()
                .id(id)
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();
        QuestionDTO questionDTO = QuestionDTO.builder()
                .id(id)
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();

        when(questionRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(question));
        when(questionMapper.toDto(questionRepository.save(questionMapper.fromDto(questionDTO)))).thenReturn(questionDTO);
        QuestionDTO createdQuestion = questionService.create(questionDTO);
        Assertions.assertEquals("aa",createdQuestion.getStatement());
        createdQuestion.setStatement("NewTitle");
        QuestionDTO editedQuestion = questionService.edit(createdQuestion.getId(),createdQuestion);
        Assertions.assertEquals("NewTitle" ,editedQuestion.getStatement());

    }

    @Test
    void delete(){
        Long id = 1L;
        Question question = Question.builder()
                .id(id)
                .statement("aa")
                .category("aa")
                .answers(null)
                .build();

        when(questionRepository.save(question)).thenReturn(question);
        when(questionRepository.findById(id)).thenReturn(java.util.Optional.of(question));
        when(questionRepository.existsById(id)).thenReturn(false);

        questionService.delete(id);
        Assertions.assertFalse(questionRepository.existsById(id));
    }
}