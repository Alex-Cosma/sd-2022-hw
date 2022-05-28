package com.example.bookstore.book;

import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
    public void testSave() {
        Book bookSaved = repository.save(Book.builder()
                .title("title")
                .author("author")
                .genre("genre")
                .quantity(50)
                .price(19.99)
                .build());

        assertNotNull(bookSaved);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void testFindAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        repository.saveAll(books);
        List<Book> all = repository.findAll();
        assertEquals(books.size(), all.size());
    }

    @Test
    public void findByTitleAndAuthor(){
        Book book = Book.builder()
                .title("title")
                .author("author")
                .genre("genre")
                .quantity(10)
                .price(19.99)
                .build();
        repository.save(book);
        Book bookFound = repository.findByTitleAndAuthor("title", "author");
        assertNotNull(bookFound);
    }

    @Test
    public void testFilterBooks(){
        Book book1 = Book.builder()
                .title("title1")
                .author("author1")
                .genre("genre1")
                .quantity(50)
                .price(19.99)
                .build();

        Book book2 = Book.builder()
                .title("amazingTitle")
                .author("amazingAuthor")
                .genre("amazingGenre")
                .quantity(50)
                .price(10.99)
                .build();

        repository.save(book1);
        repository.save(book2);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Book> filtered = repository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%amazing%", "%amazing%",
                "%amazing%",pageRequest);

        assertEquals(1, filtered.getNumberOfElements());
    }

    @Test
    public void testDelete(){
        Book book = repository.save(Book.builder()
                .title("title")
                .author("author")
                .genre("genre")
                .quantity(10)
                .price(19.99)
                .build());
        repository.delete(book);
        assertTrue(repository.findAll().isEmpty());
    }
}
