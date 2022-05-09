package com.example.bookstore.Report;

import com.example.bookstore.book.model.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.bookstore.Report.ReportType.CSV;

@Service
public class CsvReportService implements ReportService{
    @Override
    public String generateReport(List<BookDTO> books) {
        try {
            FileWriter file = new FileWriter("C:\\Users\\Alex\\Desktop\\report.csv");
            PrintWriter write = new PrintWriter(file);
            for(BookDTO book : books){
                write.println(book.getTitle());
            }
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "CSV";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
