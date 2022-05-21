package com.raulp.report;

import com.raulp.book.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.raulp.report.ReportType.CSV;
import static com.raulp.report.ReportType.PDF;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;
    @Autowired
    private BookService bookService;

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