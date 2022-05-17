package com.lab4.demo.quizzSession;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.quizz.QuizzService;
import com.lab4.demo.quizzSession.model.QuizzSession;
import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class QuizzSessionServiceTest {
    @InjectMocks
    private QuizzSessionService quizzSessionService;

    @Mock
    private QuizzSessionRepository quizzSessionRepository;

    @Mock
    private QuizzSessionMapper quizzSessionMapper;

    @Mock
    private UserService userService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private QuizzService quizzService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quizzSessionService = new QuizzSessionService(quizzSessionRepository, quizzSessionMapper,reportServiceFactory,userService,quizzService);
    }

    @Test
    void findAll() {
        List<QuizzSession> quizzSessions = TestCreationFactory.listOf(QuizzSession.class);
        when(quizzSessionRepository.findAll()).thenReturn(quizzSessions);

        List<QuizzSessionDTO> all = quizzSessionService.findAll();

        assertEquals(quizzSessions.size(), all.size());
    }

    @Test
    void findById(){
        List<QuizzSession> quizzSessions = TestCreationFactory.listOf(QuizzSession.class);
        QuizzSessionDTO q = QuizzSessionDTO.builder()
                .id(quizzSessions.get(0).getId())
                .score(quizzSessions.get(0).getScore())
                .build();

        when(quizzSessionRepository.findById(quizzSessions.get(0).getId())).thenReturn(Optional.of(quizzSessions.get(0)));
        when(quizzSessionMapper.toDto(quizzSessions.get(0))).thenReturn(q);

        QuizzSessionDTO q1 = quizzSessionService.findById(quizzSessions.get(0).getId());

        assertEquals(q1.getId(),quizzSessions.get(0).getId());
    }
    @Test
    void create(){
        User user = User.builder()
                .id(1L)
                .username("user")
                .password("password")
                .email("user@email.com")
                .rankingPoints(0)
                .build();
        QuizzSession quizzSession = QuizzSession.builder()
                .id(1L)
                .score(1)
                .user(user)
                .build();
        QuizzSessionDTO quizz = QuizzSessionDTO.builder()
                .id(1L)
                .score(1)
                .userId(user.getId())
                .build();
        when(quizzSessionMapper.fromDto(quizz)).thenReturn(quizzSession);
        when(quizzSessionMapper.toDto(quizzSessionRepository.save(quizzSessionMapper.fromDto(quizz)))).thenReturn(quizz);
        when(userService.findById(user.getId())).thenReturn(user);
        QuizzSessionDTO createdQuizz = quizzSessionService.create(quizz);
        Assertions.assertEquals(createdQuizz,quizz);
    }
}
