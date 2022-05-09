package com.example.bookstore.report;

public interface ReportService {
    String export();

    ReportType getType();
}