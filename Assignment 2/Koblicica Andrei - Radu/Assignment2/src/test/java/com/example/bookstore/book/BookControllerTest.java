package com.example.bookstore.book;

import com.example.bookstore.BaseControllerTest;
import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.dto.BookDTO;
import com.example.bookstore.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


import static com.example.bookstore.TestCreationFactory.randomLong;
import static com.example.bookstore.TestCreationFactory.randomString;
import static com.example.bookstore.UrlMapping.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
    void allItems() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = performGet(BOOKS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }


    @Test
    void create() throws Exception {
        BookDTO bookDTO = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .build();

        when(bookService.create(bookDTO)).thenReturn(bookDTO);

        ResultActions result = performPostWithRequestBody(BOOKS, bookDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTO));
    }

    @Test
    void update() throws Exception {
        final long id = randomLong();
        BookDTO bookDTO = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .build();

        when(bookService.update(id, bookDTO)).thenReturn(bookDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariables(BOOKS + BOOKS_ID_PART, bookDTO, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTO));
    }

    @Test
    void delete() throws Exception {
        BookDTO book=TestCreationFactory.newBookDTO();
        bookService.create(book);
        ResultActions result = performDeleteWithPathVariables(BOOKS + BOOKS_ID_PART, book.getId());
        result.andExpect(status().isOk());

    }

    @Test
    void sell() throws Exception {
       BookDTO book=TestCreationFactory.newBookDTO();

        when(bookService.sell(book.getId(), book)).thenReturn(book);

        ResultActions result = performPatchWithRequestBodyAndPathVariables(BOOKS + BOOKS_ID_PART, book, book.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(book));
    }
}