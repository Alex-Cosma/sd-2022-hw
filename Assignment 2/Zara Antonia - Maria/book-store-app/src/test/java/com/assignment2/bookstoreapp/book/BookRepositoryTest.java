package com.assignment2.bookstoreapp.book;

import com.assignment2.bookstoreapp.TestCreationFactory;
import com.assignment2.bookstoreapp.book.model.Book;
import com.assignment2.bookstoreapp.book.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = repository.save(Book.builder().
                title("whatever")
                .author("me")
                .genre("us")
                .price(20.03)
                .quantity(40)
                .build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void deleteAll(){
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Pride and Prejudice", "Jane Austen", "ROMANCE",20, 30));
        books.add(new Book(1L, "War and Peace", "Lev Tolstoi", "CLASSIC",5, 40));
        books.add(new Book(1L, "The Art of War", "Sun Tzu", "WAR",0, 100));
        books.add(new Book(1L, "The Stranger", "Albert Camus", "PHILOSOPHY",0, 46));

        for(Book b: books){
            repository.save(b);
        }

        Assertions.assertTrue(repository.findAll().size() > 0);

        repository.deleteAll();

        Assertions.assertEquals(repository.findAll().size(), 0);
    }

    @Test
    public void testFindAll() {

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Pride and Prejudice", "Jane Austen", "ROMANCE",20, 30));
        books.add(new Book(1L, "War and Peace", "Lev Tolstoi", "CLASSIC",5, 40));
        books.add(new Book(1L, "The Art of War", "Sun Tzu", "WAR",0, 100));
        books.add(new Book(1L, "The Stranger", "Albert Camus", "PHILOSOPHY",0, 46));

        repository.saveAll(books);
        List<Book> all = repository.findAll();
        assertEquals(books.size(), all.size());
    }
}