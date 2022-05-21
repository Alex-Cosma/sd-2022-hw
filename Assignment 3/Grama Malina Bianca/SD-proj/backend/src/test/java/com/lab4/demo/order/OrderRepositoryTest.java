package com.lab4.demo.order;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.EGenre;
import com.lab4.demo.book.repository.BookRepository;
import com.lab4.demo.book.repository.GenreRepository;
import com.lab4.demo.order.model.Order;
import com.lab4.demo.order.repository.OrderRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.RoleRepository;
import com.lab4.demo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        bookRepository.deleteAll();
    }

    private List<Order> createOrders() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        for (Book book : books) {
            book.setGenre(genreRepository.findByName(EGenre.Fiction));
        }

        List<User> users = TestCreationFactory.listOf(User.class);
        Role customer = roleRepository.findByName(ERole.CUSTOMER).get();
        Set<Role> roles = new HashSet<>();
        roles.add(customer);
        for (User user : users) {
            user.setRoles(roles);
        }

        List<Book> savedBooks = bookRepository.saveAll(books);
        List<User> savedUsers = userRepository.saveAll(users);

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Set<Book> orderBooks = new HashSet<>();
            for (int j = 0; j < TestCreationFactory.randomBoundedInt(10); j++) {
                orderBooks.add(savedBooks.get(TestCreationFactory.randomBoundedInt(savedBooks.size())));
            }
            Date date = new Date(System.currentTimeMillis());
            Date returnDate = new Date(System.currentTimeMillis() + 2 * 7 * 24 * 60 * 60 * 1000);
            Order order = Order.builder()
                    .books(orderBooks)
                    .user(savedUsers.get(TestCreationFactory.randomBoundedInt(savedUsers.size())))
                    .deliveryDate(date)
                    .returnDate(returnDate)
                    .address(TestCreationFactory.randomString())
                    .build();
            orders.add(order);
        }
        return orders;
    }

    @Test
    void findAllByUserId() {
        List<Order> orders = createOrders();
        List<Order> savedOrders = orderRepository.saveAll(orders);

        User user = savedOrders.get(0).getUser();

        List<Order> expected = new ArrayList<>();
        for(Order order : savedOrders) {
            if(order.getUser().getId().equals(user.getId())) {
                expected.add(order);
            }
        }

        List<Order> actual = orderRepository.findAllByUserId(user.getId());

        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
        }
    }
}