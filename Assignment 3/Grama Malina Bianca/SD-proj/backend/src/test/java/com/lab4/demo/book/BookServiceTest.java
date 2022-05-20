package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.dto.BookDTO;
import com.lab4.demo.book.mapper.BookMapper;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.EGenre;
import com.lab4.demo.book.model.Genre;
import com.lab4.demo.book.repository.BookRepository;
import com.lab4.demo.book.repository.GenreRepository;
import com.lab4.demo.book.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.lab4.demo.TestCreationFactory.newBook;
import static com.lab4.demo.TestCreationFactory.newBookDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper, genreRepository);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        assertEquals(books.size(), all.size());
    }

    @Test
    void create() {
        BookDTO input = newBookDTO();
        Book book = newBook();
        when(bookMapper.fromDto(input)).thenReturn(book);
        EGenre gen = EGenre.valueOf(input.getGenre());
        Genre genre = new Genre();
        when(genreRepository.findByName(gen)).thenReturn(genre);

        when(bookRepository.save(book)).thenReturn(book);

        BookDTO result = newBookDTO();
        when(bookMapper.toDto(book)).thenReturn(result);

        BookDTO expected = bookService.create(input);

        assertEquals(result, expected);
    }


    @Test
    void edit() {
        Long id = TestCreationFactory.randomLong();
        BookDTO input = newBookDTO();

        Book book = newBook();
        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));
        assert book != null;
        book.setTitle(input.getTitle());
        book.setAuthor(input.getAuthor());
        book.setDescription(input.getDescription());
        book.setPages(input.getPages());
        book.setDescription(input.getDescription());
        book.setYear(input.getYear());
        book.setQuantity(input.getQuantity());

        EGenre gen = EGenre.valueOf(input.getGenre());
        Genre genre = new Genre();
        when(genreRepository.findByName(gen)).thenReturn(genre);

        book.setGenre(genre);

        when(bookRepository.save(book)).thenReturn(book);

        BookDTO result = newBookDTO();

        when(bookMapper.toDto(book)).thenReturn(result);

        BookDTO expected = bookService.edit(id, input);

        assertEquals(result, expected);
    }

    @Test
    void delete() {
        Long id = TestCreationFactory.randomLong();
        Book book = newBook();
        book.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        bookService.delete(id);

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void get() {
        Long id = TestCreationFactory.randomLong();
        Book book = newBook();
        book.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookDTO result = newBookDTO();
        when(bookMapper.toDto(book)).thenReturn(result);

        BookDTO expected = bookService.get(id);

        assertEquals(result, expected);
    }

    @Test
    void sellBook() {
        Long id = TestCreationFactory.randomLong();
        Book book = newBook();
        book.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        book.setQuantity(1);
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.sellBook(id);

        assertEquals(result.getQuantity(), 0);
    }
}
