package com.rdaniel.sd.a2.backend.book;

import com.rdaniel.sd.a2.backend.book.dto.BookDto;
import com.rdaniel.sd.a2.backend.book.dto.BookFilterRequestDto;
import com.rdaniel.sd.a2.backend.book.mapper.BookMapper;
import com.rdaniel.sd.a2.backend.book.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static com.rdaniel.sd.a2.backend.TestCreationFactory.listOf;
import static com.rdaniel.sd.a2.backend.TestCreationFactory.newBook;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Test
    void findAll() {
        final List<Book> books = listOf(Book.class, 15);
        final List<BookDto> bookDtos = BookMapper.INSTANCE.toDtoList(books);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toDtoList(books)).thenReturn(bookDtos);

        final List<BookDto> result = bookService.findAll();

        assertEquals(books.size(), result.size());

        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(1)).toDtoList(books);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void findById() {
        final Book book = newBook();
        final BookDto bookDto = BookMapper.INSTANCE.toDto(book);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        final BookDto result = bookService.findById(book.getId());

        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getGenre(), result.getGenre());
        assertEquals(book.getPrice(), result.getPrice());
        assertEquals(book.getQuantity(), result.getQuantity());
        assertEquals(book.getTitle(), result.getTitle());

        verify(bookRepository, times(1)).findById(book.getId());
        verify(bookMapper, times(1)).toDto(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void save() {
        final Book book = newBook();
        final BookDto bookDto = BookMapper.INSTANCE.toDto(book);

        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        final BookDto result = bookService.save(bookDto);

        assertEquals(book.getAuthor(), result.getAuthor());

        verify(bookMapper, times(1)).toEntity(bookDto);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).toDto(book);
        verifyNoMoreInteractions(bookMapper, bookRepository);
    }

    @Test
    void update() {
        final Book book = newBook();
        final BookDto updatingBookDto = BookMapper.INSTANCE.toDto(book);
        updatingBookDto.setAuthor("new author");

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        book.setAuthor("new author");
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(updatingBookDto);

        final BookDto result = bookService.update(book.getId(), updatingBookDto);

        assertEquals(updatingBookDto.getAuthor(), result.getAuthor());

        verify(bookRepository, times(1)).findById(book.getId());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookMapper, times(1)).toDto(any(Book.class));
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void delete() {
        final Book book = newBook();

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        bookService.delete(book.getId());

        verify(bookRepository, times(1)).findById(book.getId());
        verify(bookRepository, times(1)).deleteById(book.getId());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void findAllFiltered() {
        final BookFilterRequestDto filter = BookFilterRequestDto.builder()
                .author("%a%")
                .genre("%b%")
                .title("%c%")
                .price(1.0)
                .build();


        final List<Book> books = listOf(Book.class, 15);

        final Sort complex = Sort.by(ASC, "author").and(Sort.by(ASC, "title")).and(Sort.by(ASC, "genre"));
        final PageRequest pageable = PageRequest.of(1, 10, complex);
        final Page<Book> page = new PageImpl<>(listOf(Book.class, 15), pageable, 15);
        when(bookRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        page.getContent().forEach(book -> when(bookMapper.toDto(book)).thenReturn(BookMapper.INSTANCE.toDto(book)));

        final Page<BookDto> filteredResult = bookService.findAllFiltered(filter, pageable);

        verify(bookRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(bookMapper, times(books.size())).toDto(any(Book.class));
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void sellBooks() {
        final Book book = newBook();
        int initialQuantity = book.getQuantity();

        int quantityToSell = 1;
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        book.setQuantity(book.getQuantity() - quantityToSell);

        when(bookRepository.save(book)).thenReturn(book);

        final BookDto bookDto = BookMapper.INSTANCE.toDto(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        final BookDto result = bookService.sellBooks(book.getId(), quantityToSell);
        assertTrue(result.getQuantity() < initialQuantity);
        assertEquals(result.getQuantity() + quantityToSell, initialQuantity);

        verify(bookRepository, times(1)).findById(book.getId());
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).toDto(book);
    }
}