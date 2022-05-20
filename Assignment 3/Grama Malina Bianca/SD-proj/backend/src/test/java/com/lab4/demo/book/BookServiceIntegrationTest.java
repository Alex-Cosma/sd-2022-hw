package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.dto.BookDTO;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.EGenre;
import com.lab4.demo.book.model.Genre;
import com.lab4.demo.book.repository.BookRepository;
import com.lab4.demo.book.repository.GenreRepository;
import com.lab4.demo.book.service.BookService;
import com.lab4.demo.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.book.model.EGenre.Bildungsroman;
import static com.lab4.demo.book.model.EGenre.Fiction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private OrderRepository orderRepository;


    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        bookRepository.deleteAll();
        genreRepository.deleteAll();
        for(EGenre value : EGenre.values()) {
            genreRepository.save(
                    Genre.builder()
                            .name(value)
                            .build()
            );
        }
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        for(Book book : books) {
            book.setGenre(genreRepository.findByName(Bildungsroman));
       }
        bookRepository.saveAll(books);

        List<BookDTO> booksFromRepo = bookService.findAll();

        assertEquals(books.size(), booksFromRepo.size());

        for (BookDTO bookDTO : booksFromRepo) {
            assertEquals(books.get(booksFromRepo.indexOf(bookDTO)).getTitle(), bookDTO.getTitle());
            assertEquals(books.get(booksFromRepo.indexOf(bookDTO)).getGenre().getName().toString(), bookDTO.getGenre());
            assertEquals(books.get(booksFromRepo.indexOf(bookDTO)).getAuthor(), bookDTO.getAuthor());
            assertEquals(books.get(booksFromRepo.indexOf(bookDTO)).getDescription(), bookDTO.getDescription());
            assertEquals(books.get(booksFromRepo.indexOf(bookDTO)).getPages(), bookDTO.getPages());
        }
    }

    private Book createBook() {
        return Book.builder().
                title(TestCreationFactory.randomString()).
                author(TestCreationFactory.randomString()).
                description(TestCreationFactory.randomString()).
                pages(TestCreationFactory.randomInt()).
                genre(genreRepository.findByName(Fiction)).
                quantity(TestCreationFactory.randomInt()).
                year(TestCreationFactory.randomInt()).
                build();
    }

    @Test
    void create() {
        BookDTO bookDTO = BookDTO.builder()
                .title(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString())
                .description(TestCreationFactory.randomString())
                .pages(TestCreationFactory.randomInt())
                .genre(Bildungsroman.toString())
                .quantity(TestCreationFactory.randomInt())
                .year(TestCreationFactory.randomInt())
                .build();

        BookDTO savedBook = bookService.create(bookDTO);
        assertEquals(bookDTO.getTitle(), savedBook.getTitle());
        assertEquals(bookDTO.getAuthor(), savedBook.getAuthor());
        assertEquals(bookDTO.getDescription(), savedBook.getDescription());
        assertEquals(bookDTO.getPages(), savedBook.getPages());
        assertEquals(bookDTO.getGenre(), savedBook.getGenre());
        assertEquals(bookDTO.getQuantity(), savedBook.getQuantity());
        assertEquals(bookDTO.getYear(), savedBook.getYear());
    }

    @Test
    void edit() {
        Book savedBook = bookRepository.save(createBook());
        BookDTO bookDTO = BookDTO.builder()
                .title("newTitle")
                .author("newAuthor")
                .genre(Bildungsroman.toString())
                .description(savedBook.getDescription())
                .pages(savedBook.getPages())
                .quantity(savedBook.getQuantity())
                .year(savedBook.getYear())
                .build();

        bookService.edit(savedBook.getId(), bookDTO);

        Book editedBook = bookRepository.findById(savedBook.getId()).get();

        assertEquals(bookDTO.getTitle(), editedBook.getTitle());
        assertEquals(bookDTO.getAuthor(), editedBook.getAuthor());
        assertEquals(bookDTO.getDescription(), editedBook.getDescription());
        assertEquals(bookDTO.getPages(), editedBook.getPages());
        assertEquals(bookDTO.getGenre(), editedBook.getGenre().getName().toString());
        assertEquals(bookDTO.getQuantity(), editedBook.getQuantity());
        assertEquals(bookDTO.getYear(), editedBook.getYear());
    }

    @Test
    void delete() {
        Book savedBook = bookRepository.save(createBook());
        bookService.delete(savedBook.getId());
        assertFalse(bookRepository.findById(savedBook.getId()).isPresent());

        List<BookDTO> books = TestCreationFactory.listOf(BookDTO.class);
        books.forEach(book -> book.setGenre(Fiction.toString()));
        List<BookDTO> savedBooks = new ArrayList<>();
        for(BookDTO book : books) {
            savedBooks.add(bookService.create(book));
        }
        bookService.delete(savedBooks.get(0).getId());
        assertFalse(bookRepository.findById(savedBooks.get(0).getId()).isPresent());
        assertEquals(savedBooks.size() - 1, bookRepository.findAll().size());
    }

    @Test
    void get() {
        Book savedBook = bookRepository.save(createBook());
        BookDTO getBook = bookService.get(savedBook.getId());

        assertEquals(savedBook.getTitle(), getBook.getTitle());
        assertEquals(savedBook.getAuthor(), getBook.getAuthor());
        assertEquals(savedBook.getDescription(), getBook.getDescription());
        assertEquals(savedBook.getPages(), getBook.getPages());
        assertEquals(savedBook.getGenre().getName().toString(), getBook.getGenre());
        assertEquals(savedBook.getQuantity(), getBook.getQuantity());
        assertEquals(savedBook.getYear(), getBook.getYear());
    }

    @Test
    void sellBook() {
        Book book = createBook();
        Book savedBook = bookRepository.save(book);
        bookService.sellBook(savedBook.getId());
        Book soldBook = bookRepository.findById(savedBook.getId()).get();
        assertEquals(savedBook.getQuantity() - 1, soldBook.getQuantity());
    }
}