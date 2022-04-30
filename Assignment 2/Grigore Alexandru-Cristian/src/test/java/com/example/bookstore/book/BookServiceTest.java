package com.example.bookstore.book;

import com.example.bookstore.Report.ReportServiceFactory;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static com.example.bookstore.book.model.Genre.ACTION;
import static com.example.bookstore.book.model.Genre.FANTASY;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Autowired
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp(){
        bookService = new BookService(reportServiceFactory,bookRepository,bookMapper);
        MockitoAnnotations.openMocks(this);
        bookRepository.deleteAll();
    }

    @Test
    void findAllGenre(){
        assertEquals(0,bookService.findAllGenre("FANTASY").size());

        final Book book = Book.builder().title("Two Towers").author("JRRT").genre("FANTASY").quantity(2).price(2).build();
        final Book book1 = Book.builder().title("Two Towers1").author("JRRT").genre("ACTION").quantity(2).price(2).build();
        final Book book2 = Book.builder().title("Two Towers2").author("JRRT").genre("FANTASY").quantity(2).price(2).build();

        bookRepository.saveAll(List.of(book,book1,book2));
        final List<BookDTO> bookDTOS = bookService.findAll();
        System.out.println(bookDTOS.get(0).getGenre());
        assertEquals(3,bookDTOS.size());
    }

}