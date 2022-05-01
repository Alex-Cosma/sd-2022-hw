package com.example.backend.service.booksOutOfStockReport.jasperReport;

import com.example.backend.entity.Book;
import com.example.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// jasper reports have a similar initialization process
@Service
public class BooksOutOfStockReportInitializer {

    private final BookRepository bookRepository;

    public BooksOutOfStockReportInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private List<Book> getBooksOutOfStock() {
        return bookRepository.findByStockEquals(0);
    }

    public SimpleReportExporter getSimpleReportExporter() {

        SimpleReportFiller simpleReportFiller = new SimpleReportFiller();
        simpleReportFiller.setReportFileName("booksOutOfStockReport.jrxml");
        simpleReportFiller.compileReport();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("booksOutOfStock", getBooksOutOfStock());

        simpleReportFiller.setParameters(parameters);

        SimpleReportExporter simpleExporter = new SimpleReportExporter();
        simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());

        return simpleExporter;
    }
}
