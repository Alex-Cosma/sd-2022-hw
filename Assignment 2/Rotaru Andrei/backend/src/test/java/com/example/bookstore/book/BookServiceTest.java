package com.example.bookstore.book;

import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BookServiceTest {
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
    void findAllAvailableAndUnavailable() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> allAvailable = bookService.findAllAvailable();
        List<BookDTO> allUnavailable = bookService.findAllUnavailable();

        assertEquals(books.size(), allAvailable.size());
        assertEquals(0, allUnavailable.size());
    }

    @Test
    void create(){
        BookDTO book = BookDTO.builder()
                .id(1L)
                .title("title")
                .author("author")
                .genre("genre")
                .quantity(10)
                .price(19.99)
                .build();
        when(bookMapper.toDto(bookRepository.save(bookMapper.fromDto(book)))).thenReturn(book);
        BookDTO createdBook = bookService.create(book);
        assertEquals(createdBook,book);
    }

    @Test
    void edit(){
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .quantity(10)
                .price(19.99)
                .build();

        BookDTO bookDTO = BookDTO.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .quantity(10)
                .price(19.99)
                .build();

        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(book));
        when(bookMapper.toDto(bookRepository.save(bookMapper.fromDto(bookDTO)))).thenReturn(bookDTO);
        BookDTO createdBook = bookService.create(bookDTO);
        assertEquals("Title",createdBook.getTitle());

        createdBook.setTitle("NewTitle");
        BookDTO editedBook = bookService.edit(createdBook.getId(),createdBook);
        assertEquals("NewTitle",editedBook.getTitle());
    }

    @Test
    void delete(){
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .quantity(20)
                .price(19.99)
                .build();

        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        when(bookRepository.existsById(1L)).thenReturn(false);

        bookService.delete(1L);
        assertFalse(bookRepository.existsById(1L));
    }

    @Test
    void sell(){
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .quantity(20)
                .price(19.99)
                .build();

        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        when(bookRepository.existsById(1L)).thenReturn(false);

        bookService.sell(1L);
        assertEquals(19,book.getQuantity());
    }
}
