package com.lab4.demo.quizzSession;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;
import com.lab4.demo.report.ReportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static com.lab4.demo.UrlMapping.QUIZZ_SESSION;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuizzSessionControllerTest extends BaseControllerTest {

    @InjectMocks
    private QuizzSessionController controller;

    @Mock
    private QuizzSessionService quizzSessionService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new QuizzSessionController(quizzSessionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void create() throws Exception {
        QuizzSessionDTO reqquizz = QuizzSessionDTO.builder()
                .score(1)
                .build();

        when(quizzSessionService.create(reqquizz)).thenReturn(reqquizz);

        ResultActions result = performPostWithRequestBody(QUIZZ_SESSION, reqquizz);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqquizz));;
    }

    @Test
    void exportReport() throws Exception {
        Long id = 1L;
        String filePathPDF = "src/report.pdf";
        when(quizzSessionService.export(ReportType.PDF,id)).thenReturn(filePathPDF);
        ResultActions result = performGetWithPathVariable(QUIZZ_SESSION + EXPORT_REPORT + "/" + id,ReportType.PDF.name(),id);
        result.andExpect(status().isOk());

        String filePathDOCX = "src/report.docx";
        when(quizzSessionService.export(ReportType.DOCX,id)).thenReturn(filePathDOCX);
        ResultActions result2 = performGetWithPathVariable(QUIZZ_SESSION + EXPORT_REPORT + "/" + id,ReportType.DOCX.name(),id);
        result2.andExpect(status().isOk());

    }

}
