package com.example.bookstore.report;

import com.example.bookstore.book.BookService;
import com.example.bookstore.book.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.bookstore.report.ReportType.CSV;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService {

    private final BookService bookService;

    @Override
    public String export() {
        List<BookDTO> books=bookService.findAllOutOfStock();

        try {
            PrintWriter writer = new PrintWriter("F:\\faculta an3sem2\\SD\\sd-2022-hw\\Assignment 2\\Koblicica Andrei - Radu\\Assignment2\\OutOfStockBooks.csv");
            writer.println("Out of stock books:");
            for (BookDTO book: books) {
                writer.println(toCSVEntry(book));
            }
            writer.close();
            return "CSV Report!";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String toCSVEntry(BookDTO book){
        StringBuilder stringBuilder = new StringBuilder();
        appendFieldValue(stringBuilder, String.valueOf(book.getId()));
        appendFieldValue(stringBuilder, book.getTitle());
        appendFieldValue(stringBuilder, book.getAuthor());
        appendFieldValue(stringBuilder, book.getGenre());
        appendFieldValue(stringBuilder, String.valueOf(book.getPrice()));
        appendFieldValue(stringBuilder, String.valueOf(book.getQuantity()));
        return stringBuilder.toString();
    }

    private void appendFieldValue(StringBuilder stringBuilder, String fieldValue) {
        if(fieldValue != null) {
            stringBuilder.append(fieldValue).append(",");
        } else {
            stringBuilder.append(",");
        }
    }


    @Override
    public ReportType getType() {
        return CSV;
    }
}
