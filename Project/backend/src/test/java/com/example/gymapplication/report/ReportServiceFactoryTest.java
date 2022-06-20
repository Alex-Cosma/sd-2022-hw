package com.example.gymapplication.report;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static com.example.gymapplication.report.ReportType.CSV;
import static com.example.gymapplication.report.ReportType.PDF;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ComponentScan(basePackages = {
        "com.example.services.gymapplication.report",
})
public class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() {
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        assertEquals(csvReportService.getType(),CSV);

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        assertEquals(pdfReportService.getType(), PDF);
    }
}
