package com.lab4.demo.quizzSession;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.quizz.QuizzMapper;
import com.lab4.demo.quizz.QuizzService;
import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import com.lab4.demo.quizzSession.model.QuizzSession;
import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;
import com.lab4.demo.report.DOCXReportService;
import com.lab4.demo.report.PdfReportService;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Mock
    private QuizzMapper quizzMapper;

    @Mock
    private PdfReportService pdfReportService;

    @Mock
    private DOCXReportService docxReportService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quizzSessionService = new QuizzSessionService(quizzSessionRepository, quizzSessionMapper,reportServiceFactory,userService,quizzService,quizzMapper);
    }

    @Test
    void findAll() {
        List<QuizzSession> quizzSessions = TestCreationFactory.listOf(QuizzSession.class);
        when(quizzSessionRepository.findAll()).thenReturn(quizzSessions);

        List<QuizzSessionDTO> all = quizzSessionService.findAll();

        assertEquals(quizzSessions.size(), all.size());
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

    @Test
    void export(){

        String filePath = "src/report.pdf";
        String filePath2 = "src/report.docx";
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
        List<QuizzSessionDTO> quizzSessions = new ArrayList<>();
        Quizz quizz = Quizz.builder()
                .id(1L)
                .title("quizz")
                .description("description")
                .quizzSessions(Set.of(quizzSession))
                .build();
        List<QuizzDTO> quizzes= new ArrayList<>();
        user.setQuizzSessions(Set.of(quizzSession));
        quizzSession.setQuizz(quizz);
        when(quizzSessionService.findAll().stream().filter(q -> q.getUserId().equals(user.getId())).collect(Collectors.toList())).thenReturn(quizzSessions);
        when(quizzService.findAll().stream().filter(q->q.getId().equals(quizzSessions.get(0).getQuizzId())).collect(Collectors.toList())).thenReturn(quizzes);
        when(reportServiceFactory.getReportService(ReportType.PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(ReportType.DOCX)).thenReturn(docxReportService);
        when(reportServiceFactory.getReportService(ReportType.PDF).export(quizzSessions,quizzes)).thenReturn("src/report.pdf");
        when(reportServiceFactory.getReportService(ReportType.DOCX).export(quizzSessions,quizzes)).thenReturn("src/report.docx");

        Assertions.assertEquals(quizzSessionService.export(ReportType.PDF,user.getId()),filePath);
        Assertions.assertEquals(quizzSessionService.export(ReportType.DOCX,user.getId()),filePath2);
    }
}
