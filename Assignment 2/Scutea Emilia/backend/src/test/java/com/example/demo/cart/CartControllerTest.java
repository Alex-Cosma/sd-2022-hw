package com.example.demo.cart;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookMapper;
import com.example.demo.book.BookRepository;
import com.example.demo.book.BookstoreController;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartDTO;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerTest extends BaseControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        cartController = new CartController(cartService);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    void create() throws Exception {
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Long id = TestCreationFactory.randomLong();
        when(cartService.create(id, bookDTO)).thenReturn(true);

        ResultActions resultActions = performPostWithRequestBodyAndPathVariables(CART + CREATE_CART, bookDTO, id);

        resultActions.andExpect(status().isOk());
    }

    @Test
    void getCart() throws Exception {
        List<BookDTO> bookDTOList = TestCreationFactory.listOf(BookDTO.class);
        Long id = TestCreationFactory.randomLong();
        when(cartService.getCart(id)).thenReturn(bookDTOList);

        ResultActions resultActions = performGetWithPathVariables(CART + GET_CART, id);

        resultActions.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTOList));
    }


    @Test
    void deleteFromCart() throws Exception {
        Long user_id = TestCreationFactory.randomLong();
        Long book_id = TestCreationFactory.randomLong();
        BookDTO bookDTO = TestCreationFactory.newBookDTO();

        when(cartService.deleteFromCart(user_id, book_id, bookDTO)).thenReturn(true);

        ResultActions resultActions = performPutWithRequestBodyAndPathVariables(CART + DELETE_FROM_CART, bookDTO, user_id, book_id);

        resultActions.andExpect(status().isOk());
    }

    @Test
    void deleteCart() throws Exception {
        Long user_id = TestCreationFactory.randomLong();
        doNothing().when(cartService).deleteCart(user_id);

        ResultActions resultActions = performDeleteWIthPathVariable(CART + DELETE_CART, user_id);

        resultActions.andExpect(status().isOk());
    }

    @Test
    void placeOrder() throws Exception {
        Long user_id = TestCreationFactory.randomLong();
        doNothing().when(cartService).placeOrder(user_id);

        ResultActions resultActions = performDeleteWIthPathVariable(CART + PLACE_ORDER, user_id);

        resultActions.andExpect(status().isOk());
    }
}