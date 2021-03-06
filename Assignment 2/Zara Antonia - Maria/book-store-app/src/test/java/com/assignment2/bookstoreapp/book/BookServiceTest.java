package com.assignment2.bookstoreapp.book;

import com.assignment2.bookstoreapp.TestCreationFactory;
import com.assignment2.bookstoreapp.book.model.Book;
import com.assignment2.bookstoreapp.book.model.dto.BookDTO;
import com.assignment2.bookstoreapp.report.ReportServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

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
        for(Book b: books){
            System.out.println(b.getTitle());
        }

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create(){
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        when(bookService.create(bookDTO)).thenReturn(bookDTO);

        BookDTO created = bookService.create(bookDTO);

        Assertions.assertEquals(created, bookDTO);
    }

    @Test
    void sell(){
        Book book = TestCreationFactory.newBook();
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Assumptions.assumeTrue(book.getQuantity() > 0);
        bookService.create(bookDTO);
        when(bookService.findByTitle(book.getTitle()))
                .thenReturn(book);

        bookService.sell(book.getId());

        Assertions.assertEquals(
                bookService.findByTitle(book.getTitle()).getQuantity(),
                book.getQuantity());
    }


}