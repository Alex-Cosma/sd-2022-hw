package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static com.example.bookstore.book.model.Genre.FANTASY;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ComponentScan("com.example.bookstore.security")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp(){
        bookRepository.deleteAll();
    }

    @Test
    public void findByTitle(){
        final String title = "Two Towers";
        final Book book = Book.builder().title(title).author("JRRT").genre("FANTASY").quantity(2).price(2).build();

        final Book savedBook = bookRepository.save(book);
        final Optional<Book> optionalBook = bookRepository.findByTitle(savedBook.getTitle());
        assertTrue(optionalBook.isPresent());
        final Book book1 = optionalBook.get();
        assertEquals(title,book1.getTitle());

    }

    @Test
    public void findByAuthor(){
        final String author = "JRRT";
        final Book book = Book.builder().title("Two Towers").author(author).genre("FANTASY").quantity(2).price(2).build();

        final Book savedBook = bookRepository.save(book);
        final List<Book> optionalBook = bookRepository.findAllByAuthor(savedBook.getAuthor());
        final Book book1 = optionalBook.get(0);
        assertEquals(author,book1.getAuthor());

    }

    @Test
   public void findByGenre(){
        final String genre = "FANTASY";
        final Book book = Book.builder().title("Two Towers").author("JRRT").genre(genre).quantity(2).price(2).build();

        final Book savedBook = bookRepository.save(book);
        final List<Book> optionalBook = bookRepository.findAllByGenre(savedBook.getGenre());
        final Book book1 = optionalBook.get(0);
        assertEquals(genre,book1.getGenre());

    }

}