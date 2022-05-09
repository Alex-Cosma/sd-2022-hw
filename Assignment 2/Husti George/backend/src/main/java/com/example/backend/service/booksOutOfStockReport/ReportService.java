package com.example.backend.service.booksOutOfStockReport;

public interface ReportService {
    void export(String downloadPath, String author);
    ReportType getType();
}
