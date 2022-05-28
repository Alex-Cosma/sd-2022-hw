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
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CartServiceTest {

    @InjectMocks
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
    private UserService userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(cartRepository,cartMapper,bookMapper,userService,bookstoreService,bookRepository);
    }

    @Test
    void create(){
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Book book = TestCreationFactory.newBook();

        CartDTO cartDTO = TestCreationFactory.newCartDTO();
        Cart cart = TestCreationFactory.newCart();

        Long id = TestCreationFactory.randomLong();
        Integer quantity = book.getQuantity()-1;


        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        doNothing().when(bookRepository).sellBook(id, quantity);

        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(cartMapper.fromDto(cartDTO)).thenReturn(cart);
        when(cartRepository.save(cart)).thenReturn(cart);

        Long user_id = TestCreationFactory.randomLong();
        Assertions.assertFalse(cartService.create(user_id,bookDTO));
    }

    @Test
    void getCart(){
        Book book = TestCreationFactory.newBook();
        when(bookRepository.save(book)).thenReturn(book);


        BookDTO bookDTO = TestCreationFactory.newBookDTO();

        Cart cart = TestCreationFactory.newCart();
        cart.setBooks(List.of(book));
        when(cartRepository.save(cart)).thenReturn(cart);

        Long user_id = cart.getUser_id();
        Long book_id = cart.getBooks().get(0).getId();

        List<Long> book_ids = List.of(book_id);


        when(cartRepository.findAllBooksByUser_id(user_id)).thenReturn(book_ids);
        when(bookRepository.findById(book_id)).thenReturn(Optional.ofNullable(book));
        when(bookMapper.toDto(book)).thenReturn(bookDTO);

        Assertions.assertFalse(cartService.getCart(user_id).isEmpty());
    }

    @Test
    void findById(){
        Cart cart = TestCreationFactory.newCart();
        when(cartRepository.save(cart)).thenReturn(cart);

        Long id = cart.getId();
        when(cartRepository.findById(id)).thenReturn(Optional.ofNullable(cart));

        Assertions.assertEquals(cart.getUser_id(), cartService.findById(id).getUser_id());
    }

    @Test
    void deleteFromCart(){
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Book book = TestCreationFactory.newBook();
        Cart cart = TestCreationFactory.newCart();

        Long user_id = TestCreationFactory.randomLong();
        Long book_id = TestCreationFactory.randomLong();

        when(cartRepository.save(cart)).thenReturn(cart);

        Long cart_id = cart.getId();
        List<Long> ids = List.of(cart_id);

        when(cartRepository.findById(cart_id)).thenReturn(Optional.ofNullable(cart));
        when(cartRepository.findBookToDelete(user_id,book_id)).thenReturn(ids);

        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        doNothing().when(cartRepository).delete(cart);

        Assertions.assertTrue(cartService.deleteFromCart(user_id,book_id,bookDTO));
    }

    @Test
    void deleteCart(){
        User user = TestCreationFactory.newUser();

        when(userRepository.save(user)).thenReturn(user);

        Long user_id = TestCreationFactory.randomLong();

        Cart cart = TestCreationFactory.newCart();
        cart.setUser_id(user_id);

        when(cartRepository.save(cart)).thenReturn(cart);

        Long cart_id = cart.getId();

        List<Long> cart_ids = List.of(cart_id);

        when(cartRepository.findCartToDeleteByUser_id(user_id)).thenReturn(cart_ids);

        when(cartRepository.findById(cart_id)).thenReturn(Optional.ofNullable(cart));
        doNothing().when(cartRepository).delete(cart);

        cartService.deleteCart(user_id);

        Assertions.assertFalse(cartRepository.existsById(cart_id));
    }

    @Test
    void placeOrder(){
        User user = TestCreationFactory.newUser();

        when(userRepository.save(user)).thenReturn(user);

        Long user_id = TestCreationFactory.randomLong();

        Cart cart = TestCreationFactory.newCart();
        Cart cart1 = TestCreationFactory.newCart();
        CartDTO cartDTO = TestCreationFactory.newCartDTO();

        cart.setUser_id(user_id);

        when(cartRepository.save(cart)).thenReturn(cart);

        Long cart_id = cart.getId();

        List<Long> cart_ids = List.of(cart_id);

        when(cartRepository.findCartToDeleteByUser_id(user_id)).thenReturn(cart_ids);

        when(cartRepository.findById(cart_id)).thenReturn(Optional.ofNullable(cart));
        doNothing().when(cartRepository).delete(cart);

        cartService.placeOrder(user_id);

        Assertions.assertFalse(cartRepository.existsById(cart_id));

    }


}