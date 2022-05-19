package com.example.demo.cart;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookstoreService;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartDTO;
import com.example.demo.book.BookMapper;
import com.example.demo.book.BookRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CartServiceTest {

    @Mock
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartMapper cartMapper;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookstoreService bookstoreService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(cartRepository,cartMapper,bookMapper,userService,bookstoreService);
    }

    @Test
    void create(){
        BookDTO book = TestCreationFactory.newBookDTO();
        Long user_id = 1L;
        when(cartService.create(user_id,book)).thenReturn(true);
    }

    @Test
    void findById(){
//        Cart cart = TestCreationFactory.newCart();
//        Long id = 1L;
//        when(cartService.findById(id)).thenReturn(cart);
    }

    @Test
    void addBook(){
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Long id = 1L;
        when(cartService.addBook(id, bookDTO)).thenReturn(true);
    }

    @Test
    void deleteFromCart(){
//        BookDTO bookDTO = TestCreationFactory.newBookDTO();
//        Long id = 1L;
//        when(cartService.deleteFromCart(id, bookDTO)).thenReturn(true);
    }

    @Test
    void deleteCart(){
//        CartDTO cartDTO = TestCreationFactory.newCartDTO();
//        doNothing().when(cartService).deleteCart(cartDTO.getId());
    }




}