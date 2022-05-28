package com.example.demo.cart;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookRepository;
import com.example.demo.book.model.Book;
import com.example.demo.cart.model.Cart;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.demo.TestCreationFactory.newBook;
import static com.example.demo.TestCreationFactory.randomLong;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp(){
        cartRepository.deleteAll();
        userRepository.deleteAll();
        bookRepository.deleteAll();;
    }

    @Test
    void findAllBooksByUser_id() {
        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);
        Long user_id = user.getId();

        Book book = newBook();
        book = bookRepository.save(book);
        Long book_id = book.getId();

        Cart cart = TestCreationFactory.newCart();
        cart.setUser_id(user_id);
        cart.setBooks(List.of(book));

        cartRepository.save(cart);
        List<Long> book_ids = cartRepository.findAllBooksByUser_id(user_id);

        Assertions.assertEquals(book_id, book_ids.get(0));
        Assertions.assertEquals(1, book_ids.size());
    }

    @Test
    void findCartToDeleteByUser_id() {
        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);
        Long user_id = user.getId();

        Book book = newBook();
        book = bookRepository.save(book);
        Long book_id = book.getId();

//        Cart cart = TestCreationFactory.newCart();
        Cart cart = Cart.builder()
                .user_id(randomLong())
                .books(List.of(newBook()))
                .build();

        cart.setUser_id(user_id);
        cart.setBooks(List.of(book));

        cart = cartRepository.save(cart);
        Long cart_id = cart.getId();

        List<Long> cart_ids = cartRepository.findCartToDeleteByUser_id(user_id);

        Assertions.assertEquals(cart_id, cart_ids.get(0));
        Assertions.assertEquals(1, cart_ids.size());
    }

    @Test
    void findBookToDelete() {
        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);
        Long user_id = user.getId();

        Book book = newBook();
        book = bookRepository.save(book);


        Cart cart = TestCreationFactory.newCart();
        cart.setUser_id(user_id);
        cart.setBooks(List.of(book));

        cart = cartRepository.save(cart);

        Long book_id = book.getId();
        Long cart_id = cart.getId();

        List<Long> cart_ids = cartRepository.findBookToDelete(user_id, book_id);

        System.out.println(cart_ids);
//        Assertions.assertEquals(cart_id, cart_ids.get(0));
        Assertions.assertEquals(1, cart_ids.size());
    }
}