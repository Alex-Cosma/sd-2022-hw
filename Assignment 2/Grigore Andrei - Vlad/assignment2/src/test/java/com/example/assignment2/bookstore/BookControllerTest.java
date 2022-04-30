package com.example.assignment2.bookstore;

import com.example.assignment2.BaseControllerTest;
import com.example.assignment2.TestCreationFactory;
import com.example.assignment2.bookstore.model.Book;
import com.example.assignment2.bookstore.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.assignment2.TestCreationFactory.randomLong;
import static com.example.assignment2.TestCreationFactory.randomString;
import static com.example.assignment2.UrlMappings.EXPORT_REPORT;
import static com.example.assignment2.reports.ReportType.CSV;
import static com.example.assignment2.reports.ReportType.PDF;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;
    @Mock
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        controller = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void getAllTest() throws Exception{
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = performGet("/api/books");
        response.andExpect(status().isOk());
    }


    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody("/api/books/create", reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .build();

        when(bookService.edit(id, reqBook)).thenReturn(reqBook);
        ResultActions result = performPostWithRequestBodyAndPathVariables("/api/books/edit/{id}" , reqBook,id);
        result.andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void exportReport() throws Exception {
        final String csv = "csv";
        final String pdf = "pdf";
        when(bookService.export(CSV)).thenReturn(csv);
        when(bookService.export(PDF)).thenReturn(pdf);

        ResultActions responseCsv = mockMvc.perform(get("/api/books" + EXPORT_REPORT, CSV.name()));
        ResultActions responsePdf = mockMvc.perform(get("/api/books" + EXPORT_REPORT, PDF.name()));

        responseCsv.andExpect(status().isOk());
        responsePdf.andExpect(status().isOk());
    }
}