package com.example.demo.book;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findItemsByQuantityEquals() {
        List<Book> bookList = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(bookList);

        Book firstBook = bookRepository.findAll().get(0);

        List<Book> books = bookRepository.findItemsByQuantityEquals(firstBook.getQuantity());

        assertFalse(books.isEmpty());
    }

    @Test
    void sellBook() {
        Book book = TestCreationFactory.newBook();
        book = bookRepository.save(book);
        Integer updateQuantity = book.getQuantity() * 10;

        bookRepository.sellBook(book.getId(), updateQuantity);

        assertEquals(updateQuantity, bookRepository.findById(book.getId()).get().getQuantity());
    }

    @Test
    void findAllByTitleLikeOrAuthorLikeOrGenreLike() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);
        String string = "FITNESS";
        List<Book> bookList = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(string, string, string);
        assertFalse(bookList.isEmpty());
    }

    @Test
    void findAllByGenreEquals() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);
        String string = "";
        List<Book> bookList = bookRepository.findAllByGenreEquals(string);
        assertTrue(bookList.isEmpty());
        assertEquals(bookList.size(), 0);
    }
}