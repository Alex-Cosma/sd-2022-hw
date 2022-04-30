package com.example.bookstore.book;

import com.example.bookstore.BaseControllerTest;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.bookstore.UrlMappings.ENTITY;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static com.example.bookstore.UrlMappings.BOOKS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @BeforeEach
    protected void setUp(){
        MockitoAnnotations.openMocks(this);
        controller = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).
                setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void testController() throws Exception{

        ResultActions response = mockMvc.perform(get(BOOKS));
        response.andExpect(status().isOk());
        ResultActions invalidRequest = mockMvc.perform(get("/invalidSomething"));
        invalidRequest.andExpect(status().isNotFound());

    }

    @Test
    void findAll() throws Exception{
        List<BookDTO> bookDTOList = new ArrayList<>();
        final BookDTO book = BookDTO.builder().title("Two Towers").author("JRRT").genre("FANTASY").quantity(0).price(2).build();
        final BookDTO book1 = BookDTO.builder().title("Fellowship").author("JRRT").genre("ACTION").quantity(100).price(2000).build();
        bookDTOList.add(book);
        bookDTOList.add(book1);
        when(bookService.findAll()).thenReturn(bookDTOList);
        ResultActions resultActions = mockMvc.perform(get(BOOKS));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTOList));
    }


    @Test
    void edit() throws Exception {
        long id = new Random().nextInt(1999);
        BookDTO reqItem = BookDTO.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(11)
                .price(11)
                .build();

        when(bookService.edit(id, reqItem)).thenReturn(reqItem);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS + ENTITY, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }


}
