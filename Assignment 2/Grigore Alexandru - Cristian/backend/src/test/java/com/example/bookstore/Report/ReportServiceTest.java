package com.example.bookstore.Report;

import com.example.bookstore.book.BookRepository;
import com.example.bookstore.book.BookService;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.BookMapper;
import com.example.bookstore.book.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static com.example.bookstore.Report.ReportType.CSV;
import static com.example.bookstore.Report.ReportType.PDF;
import static com.example.bookstore.book.model.Genre.FANTASY;

@ComponentScan("com.example.bookstore.book")
@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Autowired
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(reportServiceFactory,bookRepository,bookMapper);
        bookRepository.deleteAll();
    }


    @Test
    void getReportService() {

        final Book book = Book.builder().title("Two Towers").author("JRRT").genre("FANTASY").quantity(0).price(2).build();
        bookRepository.save(book);

        List<BookDTO> books = bookService.findForReport();
        System.out.println(books.get(0));
        Assertions.assertEquals("CSV",reportServiceFactory.export(books,CSV));
        Assertions.assertEquals("PDF", reportServiceFactory.export(books,PDF));
    }


}
