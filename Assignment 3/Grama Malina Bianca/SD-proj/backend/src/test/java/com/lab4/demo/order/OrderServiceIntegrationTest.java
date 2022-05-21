package com.lab4.demo.order;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.EGenre;
import com.lab4.demo.book.repository.BookRepository;
import com.lab4.demo.book.repository.GenreRepository;
import com.lab4.demo.order.dto.OrderDTO;
import com.lab4.demo.order.mapper.OrderMapper;
import com.lab4.demo.order.model.Order;
import com.lab4.demo.order.repository.OrderRepository;
import com.lab4.demo.order.service.OrderService;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.RoleRepository;
import com.lab4.demo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
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
    void getOrder() {
        List<Order> orders = createOrders();
        List<Order> savedOrders = orderRepository.saveAll(orders);
        int random = TestCreationFactory.randomBoundedInt(orders.size());
        OrderDTO orderDTO = orderService.getOrder(savedOrders.get(random).getId());
        OrderDTO savedOrderDTO = orderMapper.toDto(savedOrders.get(random));
        orderMapper.populateFields(savedOrders.get(random), savedOrderDTO);

        assertEquals(orderDTO.getId(), savedOrderDTO.getId());
        assertEquals(orderDTO.getBookIds(), savedOrderDTO.getBookIds());
        assertEquals(orderDTO.getUserId(), savedOrderDTO.getUserId());
        assertEquals(orderDTO.getDeliveryDate().toString(), savedOrderDTO.getDeliveryDate().toString());
        assertEquals(orderDTO.getReturnDate().toString(), savedOrderDTO.getReturnDate().toString());
        assertEquals(orderDTO.getAddress(), savedOrderDTO.getAddress());
    }

    @Test
    void findAll() {
        List<Order> orders = createOrders();
        List<Order> savedOrders = orderRepository.saveAll(orders);
        List<OrderDTO> foundOrders = orderService.findAll();
        List<OrderDTO> savedOrderDTOs = new ArrayList<>();
        for (Order order : savedOrders) {
            OrderDTO orderDTO = orderMapper.toDto(order);
            orderMapper.populateFields(order, orderDTO);
            savedOrderDTOs.add(orderDTO);
        }

        assertEquals(foundOrders.size(), savedOrderDTOs.size());
        for (int i = 0; i < foundOrders.size(); i++) {
            assertEquals(foundOrders.get(i).getId(), savedOrderDTOs.get(i).getId());
            assertEquals(foundOrders.get(i).getBookIds(), savedOrderDTOs.get(i).getBookIds());
            assertEquals(foundOrders.get(i).getUserId(), savedOrderDTOs.get(i).getUserId());
            assertEquals(foundOrders.get(i).getDeliveryDate().toString(), savedOrderDTOs.get(i).getDeliveryDate().toString());
            assertEquals(foundOrders.get(i).getReturnDate().toString(), savedOrderDTOs.get(i).getReturnDate().toString());
        }
    }

    @Test
    void create() {
        List<Order> orders = createOrders();
        List<Order> savedOrders = orderRepository.saveAll(orders);
        int random = TestCreationFactory.randomBoundedInt(orders.size());
        OrderDTO orderDTO = orderMapper.toDto(savedOrders.get(random));
        orderMapper.populateFields(savedOrders.get(random), orderDTO);

        OrderDTO createdOrderDTO = orderService.create(orderDTO);
        Order createdOrder = orderRepository.findById(createdOrderDTO.getId()).get();
        orderMapper.populateFields(createdOrder, createdOrderDTO);

        assertEquals(createdOrderDTO.getId(), orderDTO.getId());
        assertEquals(createdOrderDTO.getBookIds(), orderDTO.getBookIds());
        assertEquals(createdOrderDTO.getUserId(), orderDTO.getUserId());
        assertEquals(createdOrderDTO.getDeliveryDate().toString(), orderDTO.getDeliveryDate().toString());
        assertEquals(createdOrderDTO.getReturnDate().toString(), orderDTO.getReturnDate().toString());
        assertEquals(createdOrderDTO.getAddress(), orderDTO.getAddress());
    }

    @Test
    void update() {
        List<Order> orders = createOrders();
        List<Order> savedOrders = orderRepository.saveAll(orders);
        int random = TestCreationFactory.randomBoundedInt(orders.size());
        OrderDTO orderDTO = orderMapper.toDto(savedOrders.get(random));
        orderMapper.populateFields(savedOrders.get(random), orderDTO);

        OrderDTO updatedOrderDTO = orderService.update(orderDTO.getId(), orderDTO);
        Order updatedOrder = orderRepository.findById(updatedOrderDTO.getId()).get();
        orderMapper.populateFields(updatedOrder, updatedOrderDTO);

        assertEquals(updatedOrderDTO.getId(), orderDTO.getId());
        assertEquals(updatedOrderDTO.getBookIds(), orderDTO.getBookIds());
        assertEquals(updatedOrderDTO.getUserId(), orderDTO.getUserId());
        assertEquals(updatedOrderDTO.getDeliveryDate().toString(), orderDTO.getDeliveryDate().toString());
        assertEquals(updatedOrderDTO.getReturnDate().toString(), orderDTO.getReturnDate().toString());
        assertEquals(updatedOrderDTO.getAddress(), orderDTO.getAddress());
    }

    @Test
    void delete() {
        List<Order> orders = createOrders();
        List<Order> savedOrders = orderRepository.saveAll(orders);
        int random = TestCreationFactory.randomBoundedInt(orders.size());
        OrderDTO orderDTO = orderMapper.toDto(savedOrders.get(random));
        orderMapper.populateFields(savedOrders.get(random), orderDTO);

        List<Book> before = new ArrayList<>();
        List<Long> bookIds = new ArrayList<>(orderDTO.getBookIds());
        for (Long bookId : orderDTO.getBookIds()) {
            Book book = bookRepository.findById(bookId).get();
            before.add(book);
        }

        orderService.delete(orderDTO.getId());

        List<Book> after = new ArrayList<>();
        for (Long bookId : bookIds) {
            Book book = bookRepository.findById(bookId).get();
            after.add(book);
        }

        assertFalse(orderRepository.existsById(orderDTO.getId()));
        for (int i = 0; i < before.size(); i++) {
            assertEquals(before.get(i).getQuantity(), after.get(i).getQuantity() - 1);
        }
    }

    @Test
    void findAllForUser() {
        User user = TestCreationFactory.newUser();
        List<Role> customer = new ArrayList<>();
        customer.add(roleRepository.findByName(ERole.CUSTOMER).get());
        Set<Role> roles = new HashSet<>(customer);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        List<Order> orders = createOrders();
        for (int i = 0; i < orders.size(); i++) {
            if (i % 3 == 0) {
                orders.get(i).setUser(savedUser);
            }
        }

        List<Order> savedOrders = orderRepository.saveAll(orders);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : savedOrders) {
            if (order.getUser().getId().equals(savedUser.getId())) {
                OrderDTO orderDTO = orderMapper.toDto(order);
                orderMapper.populateFields(order, orderDTO);
                orderDTOs.add(orderDTO);
            }
        }

        List<OrderDTO> foundOrderDTOs = orderService.findAllForUser(savedUser.getId());

        assertEquals(orderDTOs.size(), foundOrderDTOs.size());

        for (int i = 0; i < orderDTOs.size(); i++) {
            assertEquals(orderDTOs.get(i).getId(), foundOrderDTOs.get(i).getId());
            assertEquals(orderDTOs.get(i).getBookIds(), foundOrderDTOs.get(i).getBookIds());
            assertEquals(orderDTOs.get(i).getUserId(), foundOrderDTOs.get(i).getUserId());
            assertEquals(orderDTOs.get(i).getDeliveryDate().toString(), foundOrderDTOs.get(i).getDeliveryDate().toString());
            assertEquals(orderDTOs.get(i).getReturnDate().toString(), foundOrderDTOs.get(i).getReturnDate().toString());
            assertEquals(orderDTOs.get(i).getAddress(), foundOrderDTOs.get(i).getAddress());
        }

    }
}