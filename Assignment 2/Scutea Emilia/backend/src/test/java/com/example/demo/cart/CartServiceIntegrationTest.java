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
    void create() {
        BookDTO book = TestCreationFactory.newBookDTO();
        bookRepository.save(bookMapper.fromDto(book));

        UserDTO user = TestCreationFactory.newUserDTO();
        userRepository.save(userMapper.fromDto(user));

        Long user_id = userRepository.findAll().get(0).getId();

        cartService.create(user_id, book);

        CartDTO cartDTO = cartMapper.toDto(cartRepository.findAll().get(0));

        assertEquals(cartDTO.getItems().get(0).getId(), bookMapper.fromDto(book).getId());
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

        assertEquals(cartDTO.getItems(), cartDTO1.getItems());
        assertEquals(cartDTO.getId(), cartDTO1.getId());
        assertEquals(cartDTO.getUser_id(), cartDTO1.getUser_id());

    }

    @Test
    void addBook() {
        BookDTO book = TestCreationFactory.newBookDTO();
        bookRepository.save(bookMapper.fromDto(book));

        UserDTO user = TestCreationFactory.newUserDTO();
        userRepository.save(userMapper.fromDto(user));

        Long user_id = userRepository.findAll().get(0).getId();

        book = bookMapper.toDto(bookRepository.findAll().get(0));

        cartService.create(user_id, book);

        CartDTO cartDTO = cartMapper.toDto(cartRepository.findAll().get(0));

        assertEquals(cartDTO.getItems().get(0).getId(), bookMapper.fromDto(book).getId());

        BookDTO book2 = TestCreationFactory.newBookDTO();
        bookRepository.save(bookMapper.fromDto(book2));

        book2 = bookMapper.toDto(bookRepository.findAll().get(1));

        cartService.addBook(cartDTO.getId(), book2);

        cartDTO = cartMapper.toDto(cartRepository.findAll().get(0));

        assertEquals(cartDTO.getItems().get(1).getId(), bookMapper.fromDto(book2).getId());
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

        cartService.deleteFromCart(cartDTO.getId(), book);

        cartDTO = cartMapper.toDto(cartRepository.findAll().get(0));

        assertTrue(cartDTO.getItems().isEmpty());
    }

    @Test
    void deleteCart() {
        BookDTO book = TestCreationFactory.newBookDTO();
        bookRepository.save(bookMapper.fromDto(book));

        UserDTO user = TestCreationFactory.newUserDTO();
        userRepository.save(userMapper.fromDto(user));

        Long user_id = userRepository.findAll().get(0).getId();

        book = bookMapper.toDto(bookRepository.findAll().get(0));

        cartService.create(user_id, book);

        CartDTO cartDTO = cartMapper.toDto(cartRepository.findAll().get(0));

        cartService.deleteCart(cartDTO.getId());

        assertTrue(cartRepository.findAll().isEmpty());
    }
}