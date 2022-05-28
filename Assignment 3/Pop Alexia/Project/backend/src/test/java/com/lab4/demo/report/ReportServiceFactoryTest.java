package com.lab4.demo.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.lab4.demo.report.ReportType.DOCX;
import static com.lab4.demo.report.ReportType.PDF;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() {
        ReportService docxReportService = reportServiceFactory.getReportService(DOCX);
        Assertions.assertEquals(DOCX, docxReportService.getType());

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals(PDF, pdfReportService.getType());
    }
}