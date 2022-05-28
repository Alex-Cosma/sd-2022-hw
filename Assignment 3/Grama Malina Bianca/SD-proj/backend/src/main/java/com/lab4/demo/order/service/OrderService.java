package com.lab4.demo.order.service;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.repository.BookRepository;
import com.lab4.demo.book.service.BookService;
import com.lab4.demo.order.dto.OrderDTO;
import com.lab4.demo.order.mapper.OrderMapper;
import com.lab4.demo.order.model.Order;
import com.lab4.demo.order.repository.OrderRepository;
import com.lab4.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

    private Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    public OrderDTO getOrder(Long id) {
        Order order = findById(id);
        OrderDTO orderDTO = orderMapper.toDto(order);
        orderMapper.populateFields(order, orderDTO);
        return orderDTO;
    }

    public List<OrderDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return getOrderDTOS(orders);
    }

    private List<OrderDTO> getOrderDTOS(List<Order> orders) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = orderMapper.toDto(order);
            orderMapper.populateFields(order, orderDTO);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    public List<OrderDTO> findAllForUser(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return getOrderDTOS(orders);
    }

    public OrderDTO create(OrderDTO orderDTO) {
        Order order = orderMapper.fromDto(orderDTO);
        order.setUser(userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        List<Long> bookIds = new ArrayList<>(orderDTO.getBookIds());
        Set<Book> books = new HashSet<>();
        for (Long bookId : bookIds) {
            Book book = bookService.sellBook(bookId);
            books.add(book);
        }
        order.setBooks(books);
        Date deliveryDate = new Date(System.currentTimeMillis());
        order.setDeliveryDate(deliveryDate);
        Date returnDate = new Date(deliveryDate.getTime() + (1000 * 60 * 60 * 24 * 7 * 2));
        order.setReturnDate(returnDate);
        Order savedOrder = orderRepository.save(order);
        OrderDTO savedOrderDTO = orderMapper.toDto(savedOrder);
        orderMapper.populateFields(savedOrder, savedOrderDTO);
        return savedOrderDTO;
    }

    public OrderDTO update(Long id, OrderDTO orderDTO) {
        Order order = findById(id);
        order.setUser(userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        List<Long> bookIds = new ArrayList<>(orderDTO.getBookIds());
        Set<Book> books = new HashSet<>();
        for (Long bookId : bookIds) {
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
            books.add(book);
        }
        order.setBooks(books);
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setAddress(orderDTO.getAddress());

        OrderDTO savedOrderDTO = orderMapper.toDto(orderRepository.save(order));
        orderMapper.populateFields(order, savedOrderDTO);
        return savedOrderDTO;
    }

    public void delete(Long id) {
        Order order = findById(id);
        Set<Book> books = order.getBooks();
        for (Book book : books) {
            bookService.returnBook(book.getId());
        }
        orderRepository.delete(findById(id));
    }
}
