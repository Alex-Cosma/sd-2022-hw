package com.lab4.demo.frontoffice;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private FrontOfficeController controller;

    @Mock
    private BookService bookService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new FrontOfficeController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allItems() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void create() throws Exception {
        BookDTO reqItem = BookDTO.builder().title(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString()).genre(TestCreationFactory.randomString())
                .build();

        when(bookService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(BOOKS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void edit() throws Exception {
        long id = TestCreationFactory.randomLong();
        BookDTO reqItem = BookDTO.builder()
                .id(id)
                .title(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString())
                .build();

        when(bookService.edit(id, reqItem)).thenReturn(reqItem);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS + ENTITY, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void getItem() throws Exception {
        long id = TestCreationFactory.randomLong();
        BookDTO reqItem = BookDTO.builder()
                .id(id)
                .title(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString())
                .genre(TestCreationFactory.randomString())
                .price(TestCreationFactory.randomLong())
                .quantity(1)
                .build();
        when(bookService.get(id)).thenReturn(reqItem);

        ResultActions result = performGetWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void delete() throws Exception {
        long id = TestCreationFactory.randomLong();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOKS + ENTITY, id);
        verify(bookService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }


}