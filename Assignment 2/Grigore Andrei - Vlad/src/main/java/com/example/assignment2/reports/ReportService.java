package com.example.assignment2.reports;

import com.example.assignment2.bookstore.model.dto.BookDTO;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface ReportService {

    String export(List<BookDTO> bookList) throws IOException;
    ReportType getType();
}
