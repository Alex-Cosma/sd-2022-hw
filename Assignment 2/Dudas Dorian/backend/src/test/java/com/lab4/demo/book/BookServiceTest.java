package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.CSVReportService;
import com.lab4.demo.report.PdfReportService;
import com.lab4.demo.report.ReportService;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lab4.demo.TestCreationFactory.listOf;
import static com.lab4.demo.TestCreationFactory.newBook;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(reportServiceFactory, bookRepository, bookMapper);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void testCreateBook(){
        Book book = newBook();
        when(bookRepository.save(book)).thenReturn(book);

        given(bookMapper.toDto(book)).willReturn(
                BookDTO.builder()
                        .author(book.getAuthor())
                        .title(book.getTitle())
                        .genre(book.getGenre())
                        .price(book.getPrice())
                        .quantity(book.getQuantity())
                        .build()
        );

        given(bookMapper.fromDto(bookMapper.toDto(book))).willReturn(book);

        BookDTO result = bookService.create(bookMapper.toDto(book));

        assertEquals(book.getAuthor(), result.getAuthor());
    }

    @Test
    void testEditBook(){
        Book book = newBook();
        Book book2 = Book.builder()
                .title(book.getTitle())
                .price(book.getPrice())
                .quantity(book.getQuantity())
                .genre(book.getGenre())
                .author(book.getAuthor())
                .build();
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        given(bookMapper.toDto(book)).willReturn(
                BookDTO.builder()
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .genre(book.getGenre())
                        .price(book.getPrice())
                        .quantity(book.getQuantity())
                        .build()
        );

        given(bookMapper.toDto(book2)).willReturn(
                BookDTO.builder()
                        .title(book2.getTitle())
                        .author(book2.getAuthor())
                        .genre(book2.getGenre())
                        .price(book2.getPrice())
                        .quantity(book2.getQuantity())
                        .build()
        );

        when(bookService.edit(book.getId(), bookMapper.toDto(book2))).thenReturn(
                BookDTO.builder()
                        .title(book2.getTitle())
                        .author(book2.getAuthor())
                        .genre(book2.getGenre())
                        .price(book2.getPrice())
                        .quantity(book2.getQuantity())
                        .build()
        );

        BookDTO result = bookService.edit(book.getId(), bookMapper.toDto(book2));

        assertEquals(book2.getTitle(), result.getTitle());
    }

    @Test
    void testDeleteBook(){
        Book book = newBook();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        given(bookMapper.toDto(book)).willReturn(
                BookDTO.builder()
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .genre(book.getGenre())
                        .price(book.getPrice())
                        .quantity(book.getQuantity())
                        .build()
        );

        BookDTO result = bookService.delete(book.getId());

        assertEquals(book.getTitle(), result.getTitle());
    }

    @Test
    void findAllFiltered(){
        List<Book> books = listOf(Book.class);

        when(bookRepository.findAll()).thenReturn(books);

        for(Book book : books){
            given(bookMapper.toDto(book)).willReturn(
                    BookDTO.builder()
                            .title(book.getTitle())
                            .author(book.getAuthor())
                            .genre(book.getGenre())
                            .price(book.getPrice())
                            .quantity(book.getQuantity())
                            .build()
                    );
        }

        List<Book> filteredBooks = books.stream()
                .filter(book -> book.getTitle().contains("a") || book.getAuthor().contains("b") || book.getGenre().contains("b"))
                .collect(Collectors.toList());

        List<BookDTO> result = bookService.findAllFilteredBooks("a");

        assertEquals(filteredBooks.size(), result.size());
    }

    @Test
    void testExport(){
        final String csv = "csv";
        final String pdf = "pdf";

        Book book = newBook();

        when(bookRepository.findAll()).thenReturn(List.of(book));

        given(bookMapper.toDto(book)).willReturn(
                BookDTO.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .genre(book.getGenre())
                        .price(book.getPrice())
                        .quantity(book.getQuantity())
                        .build()
        );

        ReportService csvReportService = mock(CSVReportService.class);
        ReportService pdfReportService = mock(PdfReportService.class);

        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);
        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);

        when(csvReportService.export(List.of(bookMapper.toDto(book)))).thenReturn(csv);
        when(pdfReportService.export(List.of(bookMapper.toDto(book)))).thenReturn(pdf);

        when(bookService.export(CSV)).thenReturn(csv);
        when(bookService.export(PDF)).thenReturn(pdf);

        assertEquals(csv, bookService.export(CSV));
        assertEquals(pdf, bookService.export(PDF));
    }

    @Test
    void testSellBooks(){
        Book book = newBook();
        book.setQuantity(book.getQuantity() + 2); // make sure we can sell 2 books
        Book bookCopy = Book.builder()
                .title(book.getTitle())
                .price(book.getPrice())
                .quantity(book.getQuantity())
                .genre(book.getGenre())
                .author(book.getAuthor())
                .build();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(bookCopy));

        given(bookMapper.toDto(bookCopy)).willReturn(
                BookDTO.builder()
                        .title(bookCopy.getTitle())
                        .author(bookCopy.getAuthor())
                        .genre(bookCopy.getGenre())
                        .price(bookCopy.getPrice())
                        .quantity(bookCopy.getQuantity())
                        .build()
        );

        when(bookService.sellBooks(book.getId(), 1)).thenReturn(
                BookDTO.builder()
                        .title(bookCopy.getTitle())
                        .author(bookCopy.getAuthor())
                        .genre(bookCopy.getGenre())
                        .price(bookCopy.getPrice())
                        .quantity(bookCopy.getQuantity() - 1)
                        .build()
        );

        BookDTO resultBook = bookService.sellBooks(book.getId(), 1);

        // was sold twice
        assertEquals(book.getQuantity() - 2, resultBook.getQuantity());
    }
}
