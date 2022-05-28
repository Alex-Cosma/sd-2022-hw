package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomBoundedInt;
import static com.lab4.demo.TestCreationFactory.randomString;
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
        Book bookSaved = repository.save(Book.builder().title("whatever")
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        repository.saveAll(books);
        List<Book> all = repository.findAll();
        assertEquals(books.size(), all.size());
    }
    @Test
    public void testFindByTitle(){
        Book book1 = Book.builder().title("title1")
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        repository.save(book1);
        assertNotNull(repository.findByTitle("title1"));
    }
    @Test
    public void testFindByAuthor(){
        Book book1 = Book.builder().title(randomString())
                .author("auth1")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Book book2 = Book.builder().title(randomString())
                .author("auth1")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Book book3 = Book.builder().title(randomString())
                .author("auth1 2")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        repository.save(book1);
        repository.save(book2);
        repository.save(book3);
        assertEquals(2,repository.findByAuthor("auth1").size());
    }
    @Test
    public void testFindByGenre(){
        Book book1 = Book.builder().title(randomString())
                .author(randomString())
                .genre("horror")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Book book2 = Book.builder().title(randomString())
                .author(randomString())
                .genre("horror")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Book book3 = Book.builder().title(randomString())
                .author(randomString())
                .genre("genre")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        repository.save(book1);
        repository.save(book2);
        repository.save(book3);
        assertEquals(2,repository.findByGenre("horror").size());
    }
}
