package com.example.assignment2.reports;

import com.example.assignment2.bookstore.model.dto.BookDTO;
import com.example.assignment2.bookstore.BookRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.example.assignment2.reports.ReportType.CSV;

@Service
public class ReportCsvService implements ReportService {
    private BookRepository bookRepository;


    @Override
    public String export(List<BookDTO> bookList) throws IOException {
        FileWriter fw = new FileWriter("test.csv");
        StringBuilder sb = new StringBuilder();
        sb.append("title");
        sb.append(",");
        sb.append("author");
        sb.append(",");
        sb.append("genre");
        sb.append("\n");
        for(BookDTO book:bookList){
            if(book.getQuantity()==0){
                sb.append(book.getTitle());
                sb.append(",");
                sb.append(book.getAuthor());
                sb.append(",");
                sb.append(book.getGenre());
                sb.append("\n");
            }
        }
        fw.write(sb.toString());
        System.out.println("Done");
        fw.flush();
        fw.close();
        return "csv";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
