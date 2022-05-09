package com.example.bookstore.report;

import com.example.bookstore.book.BookService;
import com.example.bookstore.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvReportService implements ReportService {

    @Override
    public String export(List<BookDTO> books) {
        try (PrintWriter writer = new PrintWriter("report.csv")) {
            if(!books.isEmpty()) {
                for (BookDTO item : books) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(item.getTitle());
                    sb.append(", ");
                    sb.append(item.getAuthor());
                    sb.append(", ");
                    sb.append(item.getGenre());
                    sb.append(", ");
                    sb.append(item.getPrice());
                    sb.append("\n");

                    writer.write(sb.toString());
                }
            }else {
                writer.write("All books are available");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return ReportType.CSV;
    }
}
