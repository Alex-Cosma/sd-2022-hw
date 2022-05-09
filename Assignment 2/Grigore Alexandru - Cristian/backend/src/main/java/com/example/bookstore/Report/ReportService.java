package com.example.bookstore.Report;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;

import java.util.List;

public interface ReportService {
    String generateReport(List<BookDTO> books);

    ReportType getType();
}
