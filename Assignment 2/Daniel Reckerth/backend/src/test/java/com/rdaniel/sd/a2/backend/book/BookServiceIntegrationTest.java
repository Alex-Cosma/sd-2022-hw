package com.rdaniel.sd.a2.backend.book;

import com.rdaniel.sd.a2.backend.TestCreationFactory;
import com.rdaniel.sd.a2.backend.book.dto.BookDto;
import com.rdaniel.sd.a2.backend.book.dto.BookFilterRequestDto;
import com.rdaniel.sd.a2.backend.book.model.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.rdaniel.sd.a2.backend.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

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
        final List<Book> books = listOf(Book.class,20);
        bookRepository.saveAll(books);

        final List<BookDto> bookDtos = bookService.findAll();

        assertEquals(books.size(),bookDtos.size());
    }

    @Test
    void findById() {
        final Book book = newBook();
        final Book saved = bookRepository.save(book);
        final BookDto byId = bookService.findById(saved.getId());
        assertEquals(book.getAuthor(), byId.getAuthor());
        assertEquals(book.getTitle(), byId.getTitle());
        assertEquals(book.getPrice(), byId.getPrice());
        assertEquals(book.getQuantity(), byId.getQuantity());
    }

    @Test
    void save() {
        final BookDto bookDto = newBookDto();
        final BookDto save = bookService.save(bookDto);
        assertEquals(bookDto.getAuthor(), save.getAuthor());
        assertEquals(bookDto.getTitle(), save.getTitle());
        assertEquals(bookDto.getPrice(), save.getPrice());
        assertEquals(bookDto.getQuantity(), save.getQuantity());
    }

    @Test
    void update() {
        final Book book = newBook();
        final Book saved = bookRepository.save(book);

        final BookDto updatingBookDto = BookDto.builder()
                .author("updatingAuthor")
                .title(book.getTitle())
                .genre(book.getGenre())
                .price(book.getPrice())
                .quantity(book.getQuantity())
                .build();

        final BookDto updatedBookDto = bookService.update(saved.getId(), updatingBookDto);

        assertNotEquals(updatedBookDto.getAuthor(), book.getAuthor());
        assertEquals(updatedBookDto.getTitle(), book.getTitle());
        assertEquals(updatedBookDto.getPrice(), book.getPrice());
        assertEquals(updatedBookDto.getQuantity(), book.getQuantity());
    }

    @Test
    void delete() {
        final Book book = newBook();
        final Book saved = bookRepository.save(book);
        bookService.delete(saved.getId());

        final EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> bookService.findById(saved.getId()));
        assertEquals("Book with id " + saved.getId() + " not found", entityNotFoundException.getMessage());
    }

    @Test
    void findAllFiltered() {
        Book firstBook = Book.builder()
                .author("John R.R. Tolkien")
                .title("Lord Of The Rings: The Fellowship of the Ring")
                .genre("Fantasy Fiction")
                .quantity(10)
                .price(35.2)
                .build();

        Book secondBook = Book.builder()
                .author("John R.R. Tolkien")
                .title("Lord Of The Rings: The Return of the Kings")
                .genre("Fantasy Fiction")
                .quantity(12)
                .price(30.00)
                .build();

        Book thirdBook = Book.builder()
                .author("John Suzuki")
                .title("The Ring")
                .genre("Fantasy Fiction")
                .quantity(3)
                .price(45.00)
                .build();

        List<Book> books = TestCreationFactory.listOf(Book.class, 1200);
        books.addAll(List.of(firstBook, secondBook, thirdBook));

        bookRepository.saveAll(books);
        BookFilterRequestDto bookFilterRequestDto = BookFilterRequestDto.builder()
                .author("%John%")
                .title("%Ring%")
                .genre("%Fantasy%")
                .price(35.00)
                .build();

        final int sortedPage = 0;
        final int sortedPageSize = 100;

        final Sort complex = Sort.by(ASC, "author").and(Sort.by(ASC, "title")).and(Sort.by(ASC, "genre"));
        final PageRequest pageable = PageRequest.of(sortedPage, sortedPageSize, complex);

        final Page<BookDto> result = bookService.findAllFiltered(bookFilterRequestDto, pageable);

        assertEquals(result.getNumberOfElements(), 2);
    }

    @Test
    void sellBooks() {
        final Book book = newBook();
        int initialQuantity = book.getQuantity();
        final Book saved = bookRepository.save(book);

        int sellingQuantity = 1;
        BookDto bookDto = bookService.sellBooks(saved.getId(), sellingQuantity);
        assertEquals(bookDto.getQuantity(), initialQuantity - sellingQuantity);
    }
}