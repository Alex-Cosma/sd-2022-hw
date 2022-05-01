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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.BOOKS;
import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

//    @Test
//    void testPaginationQuery(){
//        List<Book> books = new ArrayList<>();
//        for (int a1 = 'a'; a1 <= 'z'; a1++) {
//            for (int a2 = 'a'; a2 <= 'z'; a2++) {
//                for (int a3 = 'a'; a3 <= 'z'; a3++) {
//                    String title = String.valueOf((char) a1) +
//                            (char) a2 +
//                            (char) a3;
//                    books.add(Book.builder()
//                            .title(title)
//                            .author("author")
//                            .genre("genre")
//                            .price(1L)
//                            .quantity(1L)
//                            .build());
//                }
//            }
//        }
//
//        final int page = 1;
//        final int pageSize = 10;
//        final PageRequest pageable = PageRequest.of(page, pageSize);
//        // OBSERVATION: The test can take too long to run if you don't limit the bookMapper mock. (The full size of books is 17576)
//        for(Book book : books.stream().limit(500).collect(Collectors.toList())){
//            given(bookMapper.toDto(book)).willReturn(
//                     BookDTO.builder()
//                            .title(book.getTitle())
//                            .author(book.getAuthor())
//                            .genre(book.getGenre())
//                            .price(book.getPrice())
//                            .quantity(book.getQuantity())
//                            .build()
//            );
//        }
//        when(bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%b%", pageable))
//                .thenReturn(
//                        new PageImpl<>(
//                                books.stream()
//                                .filter(book -> book.getTitle().contains("b") || book.getAuthor().contains("b") || book.getGenre().contains("b"))
//                                .limit(pageSize)
//                                .collect(Collectors.toList()), pageable, books.size()
//                        )
//                );
//        Page<BookDTO> pagedResult = bookService.findAllFiltered("%b%", pageable);
//
//        assertTrue(pagedResult.hasContent());
//        assertEquals(pageSize, pagedResult.getNumberOfElements());
//        assertEquals(page, pagedResult.getNumber());
//
//        // what if now we'd also want to add sorting?
//
//        final int sortedPage = 4;
//        final int sortedPageSize = 100;
//        final PageRequest first100AscByName = PageRequest.of(sortedPage, sortedPageSize, Sort.by("title").ascending());
//        when(bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%b%", first100AscByName))
//                .thenReturn(
//                        new PageImpl<>(
//                                books.stream()
//                                        .filter(book -> book.getTitle().contains("b") || book.getAuthor().contains("b") || book.getGenre().contains("b"))
//                                        .limit(sortedPageSize)
//                                        .collect(Collectors.toList()), first100AscByName, books.size()
//                        )
//                );
//        final Page<BookDTO> pagedResultSortAsc = bookService.findAllFiltered("%b%", first100AscByName);
//
//        assertTrue(pagedResultSortAsc.hasContent());
//        assertEquals(sortedPageSize, pagedResultSortAsc.getNumberOfElements());
//        assertEquals(sortedPage, pagedResultSortAsc.getNumber());
//
//        final List<BookDTO> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
//        assertEquals(sortedPageSize, pagedResultSortedContent.size());
//
//        final BookDTO firstItemAsc = pagedResultSortedContent.get(0);
//        pagedResultSortedContent.remove(0);
//
//        assertTrue(
//                pagedResultSortedContent.stream().anyMatch(bookDTO ->
//                        firstItemAsc.getTitle().compareTo(bookDTO.getTitle()) < 0
//                )
//        );
//    }

//    @Test
//    void testSearchQuery() {
//        List<Book> books = new ArrayList<>();
//        for (int a1 = 'c'; a1 <= 'z'; a1++) {
//            for (int a2 = 'c'; a2 <= 'z'; a2++) {
//                for (int a3 = 'c'; a3 <= 'z'; a3++) {
//                    String title = String.valueOf((char) a1) +
//                            (char) a2 +
//                            (char) a3;
//                    books.add(Book.builder()
//                            .title(title)
//                            .price(1L)
//                            .quantity(1L)
//                            .genre("bruh")
//                            .author("blorbo")
//                            .build());
//                }
//            }
//        }
//
//        final int page = 0;
//        final int pageSize = 10;
//        final PageRequest pageable = PageRequest.of(page, pageSize);
//
//        for(Book book : books.stream().limit(50).collect(Collectors.toList())){
//            given(bookMapper.toDto(book)).willReturn(
//                    BookDTO.builder()
//                            .title(book.getTitle())
//                            .author(book.getAuthor())
//                            .genre(book.getGenre())
//                            .price(book.getPrice())
//                            .quantity(book.getQuantity())
//                            .build()
//            );
//        }
//
//        // successful search:
//
//        List<Book> filterB = books.stream()
//                .filter(book -> book.getTitle().contains("b") || book.getAuthor().contains("b") || book.getGenre().contains("b"))
//                .collect(Collectors.toList());
//
//        when(bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("b", pageable))
//                .thenReturn(new PageImpl<>(filterB));
//
//        List<BookDTO> result = bookService.findAllFilteredBooks("b");
//
//        assertFalse(result.isEmpty());
//        assertEquals(filterB.size(), result.size());
//
//        // search failure:
//
//        when(bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("a", pageable))
//                .thenReturn(
//                        new PageImpl<>(
//                                books.stream()
//                                        .filter(book -> book.getTitle().contains("a") || book.getAuthor().contains("a") || book.getGenre().contains("a"))
//                                        .limit(pageSize)
//                                        .collect(Collectors.toList()), pageable, books.size()
//                        )
//                );
//
//        result = bookService.findAllFilteredBooks("a");
//
//        assertFalse(pagedResult.hasContent());
//        assertEquals(0, pagedResult.getNumberOfElements());
//        assertNotEquals(pageSize, pagedResult.getNumberOfElements());
//        assertEquals(page, pagedResult.getNumber());
//    }

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
