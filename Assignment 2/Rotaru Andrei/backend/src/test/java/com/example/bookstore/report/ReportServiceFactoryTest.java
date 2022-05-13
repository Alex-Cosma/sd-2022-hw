package com.example.bookstore.report;

import com.example.bookstore.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.bookstore.report.ReportType.CSV;
import static com.example.bookstore.report.ReportType.PDF;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReportServiceFactoryTest {
    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Autowired
    private BookService bookService;

    @Test
    void getReportService() {
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        assertEquals("I am a CSV reporter.", csvReportService.export(bookService.findAllUnavailable()));

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        assertEquals("I am a PDF reporter.", pdfReportService.export(bookService.findAllUnavailable()));
    }
}
