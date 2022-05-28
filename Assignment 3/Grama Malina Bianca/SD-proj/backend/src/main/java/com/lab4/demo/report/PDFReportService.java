package com.lab4.demo.report;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.order.model.Order;
import com.lab4.demo.order.repository.OrderRepository;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.lab4.demo.report.ReportType.PDF;

@Service
@RequiredArgsConstructor
public class PDFReportService implements ReportService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<byte[]> export(Long userId) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Order> ordersToExport = orderRepository.findAllByUserId(userId);

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.beginText();

        //Setting the font to the Content stream
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);

        //Setting the leading
        contentStream.setLeading(14.5f);

        //Setting the position for the line
        contentStream.newLineAtOffset(25, 725);

        String userToWrite = "Orders for User: " + user.getUsername();
        contentStream.showText(userToWrite);
        contentStream.newLine();
        contentStream.newLine();
        int counter = 1;
        for (Order order : ordersToExport) {
            String id = counter + ". Order ID: " + order.getId();
            String address = "Delivery Address: " + order.getAddress();
            String deliveryDate = "Delivery date: " + order.getDeliveryDate();
            String returnDate = "Return date: " + order.getReturnDate();
            StringBuilder books = new StringBuilder("Books: ");
            List<Book> booksList = new ArrayList<>(order.getBooks());
            booksList.sort(Comparator.comparing(Book::getTitle));
            if(booksList.size() > 0) {
                for (int i = 0; i < booksList.size() - 1; i++) {
                    books.append(booksList.get(i).getTitle()).append(", ");
                }
                books.append(booksList.get(booksList.size() - 1).getTitle());
            } else {
                books.append("No books");
            }
            //Adding text in the form of string
            contentStream.showText(id);
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText(address);
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText(deliveryDate);
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText(returnDate);
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText(String.valueOf(books));
            contentStream.newLine();
            contentStream.newLine();
            counter++;
        }

        contentStream.endText();
        contentStream.close();

        document.save("pdfBoxHelloWorld.pdf");
        document.close();

        String pathFile = "C:\\UNI\\AN 3 - SEM 2\\SD\\sd-2022-hw\\Assignment 3\\Grama Malina Bianca\\SD-proj";
        byte[] contents = Files.readAllBytes(Paths.get(pathFile + "\\pdfBoxHelloWorld.pdf"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "pdfBoxHelloWorld.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
