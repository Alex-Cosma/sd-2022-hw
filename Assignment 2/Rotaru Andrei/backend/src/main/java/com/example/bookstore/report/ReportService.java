package com.example.bookstore.report;

import com.example.bookstore.book.model.dto.BookDTO;

import java.util.List;

public interface ReportService {
    String export(List<BookDTO> books);

    ReportType getType();
}
