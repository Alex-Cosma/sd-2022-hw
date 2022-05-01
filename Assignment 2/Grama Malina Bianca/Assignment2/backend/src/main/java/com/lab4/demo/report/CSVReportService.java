package com.lab4.demo.report;

import com.lab4.demo.frontoffice.BookService;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService {

    private final BookService bookService;

    @Override
    public String export() {
        try (PrintWriter writer = new PrintWriter("csv-report.csv")) {

            List<BookDTO> booksOutOfStock = bookService.findAllOutOfStock();

            StringBuilder sb = new StringBuilder();
            for(BookDTO book : booksOutOfStock) {
                sb.append(book.getTitle());
                sb.append("\n");
            }

            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return "Exported to csv-report.csv";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
