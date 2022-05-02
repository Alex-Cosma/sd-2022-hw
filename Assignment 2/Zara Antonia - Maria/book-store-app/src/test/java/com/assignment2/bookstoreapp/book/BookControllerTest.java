package com.assignment2.bookstoreapp.book;

import com.assignment2.bookstoreapp.BaseControllerTest;
import com.assignment2.bookstoreapp.TestCreationFactory;
import com.assignment2.bookstoreapp.book.model.Book;
import com.assignment2.bookstoreapp.book.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Random;

import static com.assignment2.bookstoreapp.URLMapping.*;
import static com.assignment2.bookstoreapp.report.ReportType.CSV;
import static com.assignment2.bookstoreapp.report.ReportType.PDF;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = performGet(BOOKS + FIND_ALL);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .title(TestCreationFactory.randomString())
                .genre(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString())
                .price(TestCreationFactory.randomDouble())
                .quantity(new Random().nextInt(1000))
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOKS + ADD, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        final long id = TestCreationFactory.randomLong();
        BookDTO reqBook = BookDTO.builder()
                .title(TestCreationFactory.randomString())
                .genre(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString())
                .price(TestCreationFactory.randomDouble())
                .quantity(new Random().nextInt(1000))
                .build();

        when(bookService.edit(id, reqBook)).thenReturn(reqBook);

        ResultActions result = performPutWithRequestBodyAndPathVariables(BOOKS + UPDATE + BOOKS_ID_PART, reqBook, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }
}