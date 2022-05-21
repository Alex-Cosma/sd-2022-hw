package com.lab4.demo.report;

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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PDFReportServiceIntegrationTest {

    @Autowired
    private PDFReportService pdfReportService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private RoleRepository roleRepository;

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
    void export() throws IOException {
        List<Order> orders = createOrders();
        List<Order> savedOrders = orderRepository.saveAll(orders);
        User user = savedOrders.get(0).getUser();
        for (int i = 0; i < orders.size(); i++) {
            if (i % 3 == 0) {
                savedOrders.get(i).setUser(user);
            }
        }

        Long userId = user.getId();

        pdfReportService.export(userId);

        List<Order> userOrders = orderRepository.findAllByUserId(userId);

        StringBuilder sb = new StringBuilder();
        StringBuilder booksSb = new StringBuilder();
        sb.append("Orders for User: ").append(user.getUsername());
        sb.append("\n");
        int i = 1;
        for (Order order : userOrders) {
            sb.append(i).append(". Order ID: ").append(order.getId());
            sb.append("\n");
            sb.append("Delivery Address: ").append(order.getAddress());
            sb.append("\n");
            sb.append("Delivery date: ").append(order.getDeliveryDate());
            sb.append("\n");
            sb.append("Return date: ").append(order.getReturnDate());
            sb.append("\n");
            booksSb.append("Books: ");
            List<Book> books = new ArrayList<>(order.getBooks());
            books.sort(Comparator.comparing(Book::getTitle));
            if (books.size() > 0) {
                for (int j = 0; j < books.size() - 1; j++) {
                    booksSb.append(books.get(j).getTitle()).append(", ");
                }
                booksSb.append(books.get(books.size() - 1).getTitle());
            } else
                booksSb.append("No books");
            booksSb.append("\n");
            i++;
        }

        StringBuilder sb2 = new StringBuilder();
        StringBuilder booksSb2 = new StringBuilder();
        try (PDDocument document = PDDocument.load(new File("C:\\UNI\\AN 3 - SEM 2\\SD\\sd-2022-hw\\Assignment 3\\Grama Malina Bianca\\SD-proj\\backend\\pdfBoxHelloWorld.pdf"))) {
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                String pdfFileInText = tStripper.getText(document);
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                    if (line.contains("Books: ")) {
                        booksSb2.append(line);
                        booksSb2.append("\n");
                    } else {
                        sb2.append(line);
                        sb2.append("\n");
                    }
                }
            }
        }

        assertEquals(sb.toString(), sb2.toString());
        assertEquals(booksSb.toString(), booksSb2.toString());
    }

    @Test
    void getType() {
        assertEquals(ReportType.PDF, pdfReportService.getType());
    }
}