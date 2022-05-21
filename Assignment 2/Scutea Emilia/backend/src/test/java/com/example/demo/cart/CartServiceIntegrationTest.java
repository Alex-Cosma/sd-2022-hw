package com.example.demo.cart;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookMapper;
import com.example.demo.book.BookRepository;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartDTO;
import com.example.demo.user.UserRepository;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceIntegrationTest {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        cartRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getCart() {
        BookDTO book = TestCreationFactory.newBookDTO();
        Book book1 = bookRepository.save(bookMapper.fromDto(book));

        UserDTO user = TestCreationFactory.newUserDTO();
        User user1 = userRepository.save(userMapper.fromDto(user));

        Long user_id = user1.getId();

        cartService.create(user_id, bookMapper.toDto(book1));

        Assertions.assertEquals(1, cartService.getCart(user_id).size());
    }

    @Test
    void create() {
        BookDTO book = TestCreationFactory.newBookDTO();
        Book book2 = bookRepository.save(bookMapper.fromDto(book));

        UserDTO user = TestCreationFactory.newUserDTO();
        userRepository.save(userMapper.fromDto(user));

        Long user_id = userRepository.findAll().get(0).getId();

        BookDTO bookDTO = bookMapper.toDto(book2);

        cartService.create(user_id, bookDTO);

        CartDTO cartDTO = cartMapper.toDto(cartRepository.findAll().get(0));

        assertEquals(cartDTO.getItems().get(0).getId(), bookDTO.getId());
    }

    @Test
    void findById() {
        BookDTO book = TestCreationFactory.newBookDTO();
        bookRepository.save(bookMapper.fromDto(book));

        UserDTO user = TestCreationFactory.newUserDTO();
        userRepository.save(userMapper.fromDto(user));

        Long user_id = userRepository.findAll().get(0).getId();

        book = bookMapper.toDto(bookRepository.findAll().get(0));
        cartService.create(user_id, book);

        CartDTO cartDTO = cartMapper.toDto(cartRepository.findAll().get(0));

        CartDTO cartDTO1 = cartMapper.toDto(cartService.findById(cartDTO.getId()));

        assertEquals(cartDTO.getItems().get(0).getTitle(), cartDTO1.getItems().get(0).getTitle());
        assertEquals(cartDTO.getId(), cartDTO1.getId());
        assertEquals(cartDTO.getUser_id(), cartDTO1.getUser_id());
    }


    @Test
    void deleteFromCart() {
        BookDTO book = TestCreationFactory.newBookDTO();
        bookRepository.save(bookMapper.fromDto(book));

        UserDTO user = TestCreationFactory.newUserDTO();
        userRepository.save(userMapper.fromDto(user));

        Long user_id = userRepository.findAll().get(0).getId();

        book = bookMapper.toDto(bookRepository.findAll().get(0));

        cartService.create(user_id, book);

        CartDTO cartDTO = cartMapper.toDto(cartRepository.findAll().get(0));

        assertEquals(cartDTO.getItems().get(0).getId(), bookMapper.fromDto(book).getId());

        cartService.deleteFromCart(user_id, book.getId(), book);

        assertTrue(cartRepository.findAll().isEmpty());
    }

    @Test
    void deleteCart() {
        Book book = TestCreationFactory.newBook();
        book = bookRepository.save(book);

        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);

        Long user_id = user.getId();

        cartService.create(user_id, bookMapper.toDto(book));

        CartDTO cartDTO = cartMapper.toDto(cartRepository.findAll().get(0));

        cartService.deleteCart(cartDTO.getId());

        assertTrue(cartRepository.findAll().isEmpty());
    }

    @Test
    void placeOrder(){
        Book book = TestCreationFactory.newBook();
        book = bookRepository.save(book);

        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);

        Long user_id = user.getId();

        cartService.create(user_id, bookMapper.toDto(book));

        CartDTO cartDTO = cartMapper.toDto(cartRepository.findAll().get(0));

        cartService.placeOrder(cartDTO.getId());

        assertTrue(cartRepository.findAll().isEmpty());

    }
}