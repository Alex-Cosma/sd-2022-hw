package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll();
    }

    @Test
    public void testMock(){
        Book bookSaved = bookRepository.save(Book.builder().title("test").quantity(0L).build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            bookRepository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);
        List<Book> booksFound = bookRepository.findAll();
        assertEquals(books.size(), booksFound.size());
    }

    @Test
    public void testDeleteByID(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);
        List<Book> booksFound = bookRepository.findAll();
        assertEquals(books.size(), booksFound.size());
        bookRepository.deleteById(booksFound.get(0).getId());
        assertEquals(books.size() - 1, bookRepository.findAll().size());
    }
}
