package com.app.bookingapp.controller;

import com.app.bookingapp.BaseControllerTest;
import com.app.bookingapp.TestCreationFactory;
import com.app.bookingapp.data.dto.model.BookDto;
import com.app.bookingapp.data.sql.entity.User;
import com.app.bookingapp.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.app.bookingapp.TestCreationFactory.buildUser;
import static com.app.bookingapp.TestCreationFactory.randomLong;
import static com.app.bookingapp.utils.URLMapping.*;
import static org.mockito.Mockito.*;
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
    void testAllBooks() throws Exception {
        List<BookDto> books = TestCreationFactory.listOf(BookDto.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = performGet(BOOK);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void testAllBooksByUser() throws Exception {
        User user = buildUser();
        List<BookDto> books = TestCreationFactory.listOf(BookDto.class, user);
        when(bookService.allBooksByUser(user.getUsername())).thenReturn(books);

        ResultActions response = performGetWithPathVariable(BOOK + USERNAME, user.getUsername());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }

    @Test
    void testCreate() throws Exception {                        //TODO
        BookDto reqBook = TestCreationFactory.newBookDto();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOK, reqBook);

        verify(bookService, times(1)).create(reqBook);

        ResultMatcher jsonReqItem = jsonContentToBe(reqBook);
        result.andExpect(status().isCreated());
        MvcResult mvcResult = result.andReturn();
        jsonReqItem.match(mvcResult);
    }

    @Test
    void testUpdate() throws Exception {                //TODO
        final long id = randomLong();
        BookDto reqBook = TestCreationFactory.newBookDto();

        when(bookService.update(id, reqBook)).thenReturn(reqBook);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(BOOK + ID, id, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();

        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOK + ID, id);

        result.andExpect(status().isOk());

    }
}
