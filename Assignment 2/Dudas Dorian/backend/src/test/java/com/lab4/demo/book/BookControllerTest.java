package com.lab4.demo.book;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends BaseControllerTest {
    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void allItems() throws Exception {
        List<BookDTO> books = listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = performGet(BOOKS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }

    @Test
    void exportReport() throws Exception {
        final String csv = "csv";
        final String pdf = "pdf";
        when(bookService.export(CSV)).thenReturn(csv);
        when(bookService.export(PDF)).thenReturn(pdf);

        ResultActions responseCsv = mockMvc.perform(get(BOOKS + EXPORT_REPORT, CSV.name()));
        ResultActions responsePdf = mockMvc.perform(get(BOOKS + EXPORT_REPORT, PDF.name()));

        responseCsv.andExpect(status().isOk())
                .andExpect(content().string(csv));
        responsePdf.andExpect(status().isOk())
                .andExpect(content().string(pdf));
    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = newBookDTO();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions response = performPostWithRequestBody(BOOKS, reqBook);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        BookDTO reqBook = newBookDTO();

        when(bookService.edit(id, reqBook)).thenReturn(reqBook);

        ResultActions response = performPutWithRequestBodyAndPathVariables(BOOKS + BOOKS_ID_PART, reqBook, id);
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void deleteBook() throws Exception {
        final BookDTO book = newBookDTO();
        when(bookService.delete(book.getId())).thenReturn(book);

        ResultActions response = performDeleteWithPathVariables(BOOKS + BOOKS_ID_PART, book.getId());
        response.andExpect(status().isOk());
    }

    @Test
    void testFilter() throws Exception {
        List<BookDTO> books = listOf(BookDTO.class);
        List<BookDTO> filteredBooks = books.stream()
                .filter(book -> book.getTitle().contains("a") || book.getAuthor().contains("a") || book.getGenre().contains("a"))
                .collect(Collectors.toList());

        for(BookDTO book : books) {
            when(bookService.create(book)).thenReturn(book);
        }
        when(bookService.findAllFilteredBooks("a")).thenReturn(filteredBooks);

        ResultActions result = performGetWithRequestParams(BOOKS + FILTERED, "a");
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(filteredBooks));
    }

    @Test
    void testSellBook() throws Exception {
        final BookDTO book = newBookDTO();
        when(bookService.sellBooks(book.getId(), 0)).thenReturn(book);

        ResultActions response = performPutWithRequestBodyAndPathVariables(BOOKS + SELL + BOOKS_ID_PART, 0, book.getId());
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(book));
    }
}
