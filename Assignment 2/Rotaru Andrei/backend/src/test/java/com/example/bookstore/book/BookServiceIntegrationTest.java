package com.example.bookstore.book;

import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.bookstore.TestCreationFactory.randomLong;
import static com.example.bookstore.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAllAvailableAndUnavailable() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);

        List<BookDTO> allAvailable = bookService.findAllAvailable();
        List<BookDTO> allUnavailable = bookService.findAllUnavailable();

        assertEquals(books.size(), allAvailable.size());
        assertEquals(0, allUnavailable.size());
    }

    @Test
    void filter(){
        Book book1 = Book.builder()
                .title("title1")
                .author("author1")
                .genre("genre1")
                .quantity(20)
                .price(19.99)
                .build();

        Book book2 = Book.builder()
                .title("amazingTitle")
                .author("amazingAuthor")
                .genre("amazingGenre")
                .quantity(20)
                .price(19.99)
                .build();
        bookRepository.saveAll(List.of(book1,book2));
        List<BookDTO> filterBooks = bookService.filterBooks("%amazin%");

        assertEquals(1, filterBooks.size());
    }

    @Test
    void create(){
        BookDTO book = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(10)
                .price(19.99)
                .build();
        book = bookService.create(book);

        assertTrue(bookRepository.findById(book.getId()).isPresent());
    }

    @Test
    void edit() {
        BookDTO book = BookDTO.builder()
                .id(randomLong())
                .title("title1")
                .author(randomString())
                .genre(randomString())
                .quantity(10)
                .price(19.99)
                .build();
        book = bookService.create(book);
        book.setTitle("newTitle");
        bookService.edit(book.getId(),book);

        assertEquals(bookRepository.findById(book.getId()).get().getTitle(), book.getTitle());
    }

    @Test
    void delete() {
        BookDTO book = BookDTO.builder()
                .title("title")
                .author(randomString())
                .genre(randomString())
                .quantity(10)
                .price(19.99)
                .build();
        book = bookService.create(book);
        bookService.delete(book.getId());

        assertTrue(bookRepository.findById(book.getId()).isEmpty());
    }

    @Test
    void sell(){
        BookDTO book = BookDTO.builder()
                .title("title")
                .author(randomString())
                .genre(randomString())
                .quantity(10)
                .price(19.99)
                .build();

        book = bookService.create(book);
        bookService.sell(book.getId());

        assertEquals(9, bookRepository.findById(book.getId()).get().getQuantity());
    }
}
