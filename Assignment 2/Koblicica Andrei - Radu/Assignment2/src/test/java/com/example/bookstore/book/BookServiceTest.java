package com.example.bookstore.book;

import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.dto.BookDTO;
import com.example.bookstore.book.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void findAllOutOfStock() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAllByQuantityEquals(0)).thenReturn(books);

        List<BookDTO> all = bookService.findAllOutOfStock();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void findAllByFilter() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%a%","%a%","%a%")).thenReturn(books);

        List<BookDTO> all = bookService.findAllByFilter("a");

        Assertions.assertEquals(books.size(), all.size());
    }




}