package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomBoundedInt;
import static com.lab4.demo.TestCreationFactory.randomString;
import static org.mockito.Mockito.when;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(reportServiceFactory, bookRepository, bookMapper);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }
    @Test
    void findByAuthor() {
        List<Book> books = new ArrayList<>();
        Book book1 = Book.builder().title(randomString())
                .author("auth1")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Book book2 =  Book.builder().title(randomString())
                .author("auth")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Book book3 =  Book.builder().title(randomString())
                .author("auth1 2")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();

        books.add(book1);
        books.add(book2);
        books.add(book3);

        bookRepository.save(book2);
        bookRepository.save(book1);
        bookRepository.save(book3);

        when(bookRepository.findByAuthor("auth1")).thenReturn(books);

        List<BookDTO> all = bookService.findByAuthor("auth1");
        System.out.println(books.size()+"  "+all.size());
        Assertions.assertEquals(books.size(), all.size());
    }
}
