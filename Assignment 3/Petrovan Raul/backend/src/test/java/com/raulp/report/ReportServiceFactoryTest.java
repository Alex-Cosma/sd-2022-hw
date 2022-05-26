package com.raulp.report;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() throws IOException {
//        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
//        Assertions.assertEquals("I am a CSV reporter.",
//                bookService.export(CSV));
//
//        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
//        Assertions.assertEquals(PdfReportService.docPath, bookService.export(PDF));
    }
}