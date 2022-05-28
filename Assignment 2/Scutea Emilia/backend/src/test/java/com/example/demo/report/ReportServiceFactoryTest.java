package com.example.demo.report;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.report.ReportType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReportServiceFactoryTest {


    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() {
        final String csv = "src/main/resources/csvReport.csv";
        final String pdf = "src/main/resources/pdfReport.pdf";

        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        assertEquals(csv, csvReportService.export());

        ReportService pdfBoxReportService = reportServiceFactory.getReportService(PDF);
        assertEquals(pdf, pdfBoxReportService.export());
    }
}