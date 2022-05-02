package com.lab4.demo.report;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.frontoffice.BookRepository;
import com.lab4.demo.frontoffice.BookService;
import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.report.ReportService;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void getReportService() throws IOException {
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals("Exported to csv-report.csv", csvReportService.export());

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals("Exported to pdf-report.pdf", pdfReportService.export());
    }

    @Test
    void generatePDF() throws IOException {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Book book = Book.builder().
                    title("Book " + i).
                    author("Author " + i).
                    genre("Genre " + i).
                    quantity(0).build();
            books.add(book);
        }
        bookRepository.saveAll(books);

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals("Exported to pdf-report.pdf", pdfReportService.export());
    }

    @Test
    void generateCSV() throws IOException {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Book book = Book.builder().
                    title("Book " + i).
                    author("Author " + i).
                    genre("Genre " + i).
                    quantity(0).build();
            books.add(book);
        }
        bookRepository.saveAll(books);

        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals("Exported to csv-report.csv", csvReportService.export());
    }
}