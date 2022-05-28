package com.example.backend.service.booksOutOfStockReport;

import com.example.backend.service.booksOutOfStockReport.jasperReport.BooksOutOfStockReportInitializer;
import org.springframework.stereotype.Service;

@Service
public class PdfReportService implements ReportService {

    private final BooksOutOfStockReportInitializer booksOutOfStockReportInitializer;

    public PdfReportService(BooksOutOfStockReportInitializer booksOutOfStockReportInitializer) {
        this.booksOutOfStockReportInitializer = booksOutOfStockReportInitializer;
    }

    @Override
    public void export(String downloadPath, String author) {
        this
                .booksOutOfStockReportInitializer
                .getSimpleReportExporter()
                .exportToPdf(downloadPath, author);
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF_JASPER;
    }
}
