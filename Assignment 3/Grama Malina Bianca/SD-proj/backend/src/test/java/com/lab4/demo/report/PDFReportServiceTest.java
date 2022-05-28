package com.lab4.demo.report;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.order.model.Order;
import com.lab4.demo.order.repository.OrderRepository;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.UserRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PDFReportServiceTest {
    @InjectMocks
    private PDFReportService pdfReportService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pdfReportService = new PDFReportService(orderRepository, userRepository);
    }

    @Test
    void export() throws IOException {
        User user = TestCreationFactory.newUser();
        Long userId = user.getId();

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        List<Order> orders = TestCreationFactory.listOf(Order.class);
        List<Order> ordersWithUser = new ArrayList<>();
        for(int i = 0; i < orders.size(); i++) {
            if(i % 3 == 0) {
                orders.get(i).setUser(user);
                ordersWithUser.add(orders.get(i));
            }
        }

        when(orderRepository.findAllByUserId(userId)).thenReturn(ordersWithUser);

        pdfReportService.export(userId);

        StringBuilder sb = new StringBuilder();
        StringBuilder booksSb = new StringBuilder();
        sb.append("Orders for User: ").append(user.getUsername());
        sb.append("\n");
        int i = 1;
        for (Order order : ordersWithUser) {
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