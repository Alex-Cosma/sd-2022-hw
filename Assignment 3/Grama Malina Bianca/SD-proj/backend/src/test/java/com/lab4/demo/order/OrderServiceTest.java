package com.lab4.demo.order;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.repository.BookRepository;
import com.lab4.demo.book.service.BookService;
import com.lab4.demo.order.dto.OrderDTO;
import com.lab4.demo.order.mapper.OrderMapper;
import com.lab4.demo.order.model.Order;
import com.lab4.demo.order.repository.OrderRepository;
import com.lab4.demo.order.service.OrderService;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, orderMapper, userRepository, bookRepository, bookService);
    }

    @Test
    void getOrder() {
        Order order = TestCreationFactory.newOrder();
        when(orderRepository.findById(order.getId())).thenReturn(java.util.Optional.of(order));

        OrderDTO expected = TestCreationFactory.newOrderDTO();
        when(orderMapper.toDto(order)).thenReturn(expected);

        OrderDTO actual = orderService.getOrder(order.getId());

        assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        List<Order> orders = TestCreationFactory.listOf(Order.class);
        when(orderRepository.findAll()).thenReturn(orders);
        List<OrderDTO> orderDTOs = orderService.findAll();
        assertEquals(orders.size(), orderDTOs.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void create() {
        Order order = TestCreationFactory.newOrder();
        OrderDTO orderDTO = TestCreationFactory.newOrderDTO();
        when(orderMapper.fromDto(orderDTO)).thenReturn(order);

        User user = TestCreationFactory.newUser();
        when(userRepository.findById(orderDTO.getUserId())).thenReturn(java.util.Optional.of(user));
        order.setUser(user);

        List<Long> bookIds = new ArrayList<>();
        for (int i = 0; i < TestCreationFactory.randomBoundedInt(20); i++) {
            bookIds.add(TestCreationFactory.randomLong());
        }

        Set<Book> books = new HashSet<>();
        for (Long bookId : bookIds) {
            Book book = TestCreationFactory.newBook();
            when(bookService.sellBook(bookId)).thenReturn(book);
            books.add(book);
        }

        order.setBooks(books);
        when(orderRepository.save(order)).thenReturn(order);
        OrderDTO expected = TestCreationFactory.newOrderDTO();
        when(orderMapper.toDto(order)).thenReturn(expected);

        OrderDTO actual = orderService.create(orderDTO);

        assertEquals(expected, actual);
    }

    @Test
    void update() {
        Order order = TestCreationFactory.newOrder();
        when(orderRepository.findById(order.getId())).thenReturn(java.util.Optional.of(order));

        OrderDTO input = TestCreationFactory.newOrderDTO();

        User user = TestCreationFactory.newUser();
        when(userRepository.findById(input.getUserId())).thenReturn(java.util.Optional.of(user));
        order.setUser(user);

        List<Long> bookIds = new ArrayList<>(input.getBookIds());
        Set<Book> booksSet = new HashSet<>();
        for (Long bookId : bookIds) {
            Book book = TestCreationFactory.newBook();
            when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(book));
            booksSet.add(book);
        }
        order.setBooks(booksSet);
        order.setDeliveryDate(input.getDeliveryDate());
        order.setReturnDate(input.getReturnDate());
        order.setAddress(input.getAddress());

        when(orderRepository.save(order)).thenReturn(order);

        OrderDTO expected = TestCreationFactory.newOrderDTO();
        when(orderMapper.toDto(order)).thenReturn(expected);

        OrderDTO actual = orderService.update(order.getId(), input);

        assertEquals(expected, actual);
    }

    @Test
    void delete() {
        Order order = TestCreationFactory.newOrder();
        when(orderRepository.findById(order.getId())).thenReturn(java.util.Optional.of(order));

        orderService.delete(order.getId());

        verify(orderRepository, times(1)).delete(order);
    }
}