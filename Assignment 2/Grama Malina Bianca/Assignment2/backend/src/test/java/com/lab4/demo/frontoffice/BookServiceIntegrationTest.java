package com.lab4.demo.frontoffice;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void findOutOfStock() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Book book = Book.builder().title("title" + i).author("author" + i).genre("genre" + i).quantity(0).build();
            books.add(book);
        }

        for (int i = 0; i < 3; i++) {
            Book book = Book.builder().title("title" + i).author("author" + i).genre("genre" + i).quantity(2).build();
            books.add(book);
        }

        bookRepository.saveAll(books);

        Assertions.assertEquals(5, bookService.findAllOutOfStock().size());
        Assertions.assertNotEquals(books.size(), bookService.findAllOutOfStock().size());
    }

}