package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lab4.demo.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.doNothing;
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
        assertEquals(books.size(), all.size());
    }


    @Test
    void create() {
        Book book = newItem();
        BookDTO bookDTO = newItemDTO();
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        BookDTO newBookDTO = bookService.create(bookDTO);
        assertEquals(newBookDTO,bookDTO);
    }

    @Test
    void delete() {
        Book book = newItem();
        when(bookRepository.save(book)).thenReturn(book);
        Long id = book.getId();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);
        bookService.delete(book.getId());
        assertFalse(bookRepository.existsById(id));
    }

    @Test
    void edit() {
        Long bookId = randomLong();
        Book book = newItem();
        BookDTO bookDTO = newItemDTO();
        book.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        BookDTO newBookDTO = bookService.edit(bookId, bookDTO);

        assertEquals(newBookDTO, bookDTO);
    }

    @Test
    void sell() {
        Long bookId = randomLong();
        Book book = newItem();
        BookDTO bookDTO = newItemDTO();
        book.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.save(book)).thenReturn(book);
        BookDTO newBookDTO = bookService.sell(bookId);

        assertEquals(newBookDTO, bookDTO);
    }
}
