package com.example.backend.service.booksOutOfStockReport;

import com.example.backend.service.booksOutOfStockReport.jasperReport.BooksOutOfStockReportInitializer;

public class CsvReportService implements ReportService {
    private final BooksOutOfStockReportInitializer booksOutOfStockReportInitializer;

    public CsvReportService(BooksOutOfStockReportInitializer booksOutOfStockReportInitializer) {
        this.booksOutOfStockReportInitializer = booksOutOfStockReportInitializer;
    }

    @Override
    public void export(String downloadPath, String author) {
        this
                .booksOutOfStockReportInitializer
                .getSimpleReportExporter()
                .exportToCsv(downloadPath);
    }

    @Override
    public ReportType getType() {
        return ReportType.CSV_JASPER;
    }
}
