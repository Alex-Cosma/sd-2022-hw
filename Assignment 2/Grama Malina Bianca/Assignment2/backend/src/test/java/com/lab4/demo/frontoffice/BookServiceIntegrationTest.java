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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    void findById() {
        List<Book> books = generateBooks();
        List<Book> savedBooks = new ArrayList<>();
        for (Book book : books) {
            savedBooks.add(bookRepository.save(book));
        }

        for (int i = 0; i < books.size(); i++) {
            assertEquals(bookService.findById(savedBooks.get(i).getId()).getTitle(), savedBooks.get(i).getTitle());
        }
    }

    @Test
    void create() {
        List<BookDTO> books = generateBooksDTO();
        List<BookDTO> savedBooks = new ArrayList<>();
        for (BookDTO book : books) {
            savedBooks.add(bookService.create(book));
        }

        for (int i = 0; i < books.size(); i++) {
            assertEquals(books.get(i).getTitle(), savedBooks.get(i).getTitle());
            assertEquals(books.get(i).getAuthor(), savedBooks.get(i).getAuthor());
            assertEquals(books.get(i).getGenre(), savedBooks.get(i).getGenre());
            assertEquals(books.get(i).getQuantity(), savedBooks.get(i).getQuantity());
            assertEquals(books.get(i).getPrice(), savedBooks.get(i).getPrice());
        }
    }

    @Test
    void update() {
        List<Book> books = generateBooks();
        List<Book> savedBooks = new ArrayList<>();
        for (Book book : books) {
            savedBooks.add(bookRepository.save(book));
        }

        for (int i = 0; i < books.size(); i++) {
            BookDTO bookDTO = BookDTO.builder().id(savedBooks.get(i).getId()).title("title" + i).author("author" + i).genre("genre" + i).quantity(2).price(10.0f).build();
            BookDTO updatedBook = bookService.edit(savedBooks.get(i).getId(), bookDTO);
            assertEquals(bookDTO.getTitle(), updatedBook.getTitle());
            assertEquals(bookDTO.getAuthor(), updatedBook.getAuthor());
            assertEquals(bookDTO.getGenre(), updatedBook.getGenre());
            assertEquals(bookDTO.getQuantity(), updatedBook.getQuantity());
            assertEquals(bookDTO.getPrice(), updatedBook.getPrice());
        }
    }

    @Test
    void delete() {
        List<Book> books = generateBooks();
        List<Book> savedBooks = new ArrayList<>();
        for (Book book : books) {
            savedBooks.add(bookRepository.save(book));
        }

        bookService.delete(savedBooks.get(0).getId());
        assertEquals(savedBooks.size() - 1, bookRepository.findAll().size());
        bookService.delete(savedBooks.get(1).getId());
        assertEquals(savedBooks.size() - 2, bookRepository.findAll().size());
    }

    @Test
    void sell() {
        List<BookDTO> books = generateBooksDTO();
        List<BookDTO> savedBooks = new ArrayList<>();
        for (BookDTO book : books) {
            savedBooks.add(bookService.create(book));
        }

        for (int i = 0; i < books.size(); i++) {
            bookService.sellBook(savedBooks.get(i).getId(), savedBooks.get(i));
            assertEquals(savedBooks.get(i).getQuantity() - 1, bookService.findById(savedBooks.get(i).getId()).getQuantity());
        }
    }

    @Test
    void changeTitle() {
        List<Book> books = generateBooks();
        List<Book> savedBooks = new ArrayList<>();
        for (Book book : books) {
            savedBooks.add(bookRepository.save(book));
        }

        for (int i = 0; i < books.size(); i++) {
            bookService.changeTitle(savedBooks.get(i).getId(), "newTitle" + i);
            assertEquals("newTitle" + i, bookService.findById(savedBooks.get(i).getId()).getTitle());
        }
    }

    List<Book> generateBooks() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Book book = Book.builder().
                    title("title" + i).
                    author("author" + i).
                    genre("genre" + i).
                    price(i*10).
                    quantity(i).
                    build();
            books.add(book);
        }
        return books;
    }

    List<BookDTO> generateBooksDTO() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BookDTO book = BookDTO.builder().
                    title("title" + i).
                    author("author" + i).
                    genre("genre" + i).
                    price(i*10).
                    quantity(i).
                    build();
            books.add(book);
        }
        return books;
    }
}