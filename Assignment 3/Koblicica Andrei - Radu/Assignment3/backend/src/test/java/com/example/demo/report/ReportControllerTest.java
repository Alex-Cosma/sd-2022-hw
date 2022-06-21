package com.example.demo.report;

import com.example.demo.BaseControllerTest;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.watchlist.WatchlistController;
import com.example.demo.watchlist.WatchlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Random;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportControllerTest extends BaseControllerTest {


    @InjectMocks
    private ReportController reportController;

    @Mock
    private ReportService reportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        reportController = new ReportController(reportService);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    void getReport() throws Exception {

        byte[] b=new byte[500];
        new Random().nextBytes(b);
        ByteArrayInputStream report = new ByteArrayInputStream(b);
        when(reportService.exportReport()).thenReturn(report);
        ResultActions response = performGet(REPORT);
        response.andExpect(status().isOk());
    }
}
