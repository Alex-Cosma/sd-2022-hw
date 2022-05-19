package com.example.demo.cart;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookstoreController;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.cart.model.CartDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
        Long id = 1L;
        when(cartService.create(id, bookDTO)).thenReturn(true);

        ResultActions resultActions = performPostWithRequestBodyAndPathVariables(CART + CREATE_CART, bookDTO, id);

        resultActions.andExpect(status().isOk());
    }

    @Test
    void getCart() throws Exception {
//        CartDTO cartDTO = TestCreationFactory.newCartDTO();
//        Long id = 1L;
//        when(cartService.getCart(id)).thenReturn(cartDTO);
//
//        ResultActions resultActions = performGetWithPathVariables(CART, id);
//
//        resultActions.andExpect(status().isOk());
//                .andExpect(jsonContentToBe(cartDTO));
    }

    @Test
    void addBook() throws Exception {
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Long id = 1L;
        when(cartService.addBook(id, bookDTO)).thenReturn(true);

        ResultActions resultActions = performPutWithRequestBodyAndPathVariables(CART + ADD_TO_CART, bookDTO, id);

        resultActions.andExpect(status().isOk());
    }


    @Test
    void deleteFromCart() throws Exception {
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Long id = 1L;
        when(cartService.deleteFromCart(id, bookDTO)).thenReturn(true);

        ResultActions resultActions = performPutWithRequestBodyAndPathVariables(CART + DELETE_FROM_CART, bookDTO, id);

        resultActions.andExpect(status().isOk());
    }

    @Test
    void deleteCart() throws Exception {
        Long id = 1L;
        doNothing().when(cartService).deleteCart(id);

        ResultActions resultActions = performDeleteWIthPathVariable(CART + DELETE_CART, id);

        resultActions.andExpect(status().isOk());
    }

    @Test
    void placeOrder() throws Exception {
        Long id = 1L;
        Long user_id = 1L;
        doNothing().when(cartService).placeOrder(id, user_id);

        ResultActions resultActions = performDeleteWIthPathVariable(CART + PLACE_ORDER, id, user_id);

        resultActions.andExpect(status().isOk());
    }
}