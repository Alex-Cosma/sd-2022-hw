package com.policeSection.reports;

import com.policeSection.BaseControllerTest;
import com.policeSection.Reports.ReportController;
import com.policeSection.Reports.ReportService;
import com.policeSection.fine.FineController;
import com.policeSection.fine.FineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayInputStream;

import static com.policeSection.UrlMapping.DWN_PDF;
import static com.policeSection.UrlMapping.FINES;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportsControllerTest extends BaseControllerTest {
    @InjectMocks
    private ReportController controller;

    @Mock
    private ReportService reportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new ReportController(reportService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void saveReport() throws Exception {

        byte[] bytes = new byte[123];
        ByteArrayInputStream resource = new ByteArrayInputStream(bytes);
        when(reportService.exportP()).thenReturn(resource);
        ResultActions response = performGet(DWN_PDF);
        response.andExpect(status().isOk());
    }
}
