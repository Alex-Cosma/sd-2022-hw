package com.example.bookstore.Report;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ReportServiceFactory {

    private final Map<ReportType, ReportService> reportServiceMap;

    public ReportServiceFactory(List<ReportService> reportServices){
        reportServiceMap = reportServices.stream()
                .collect(Collectors.toMap(
                    ReportService::getType, Function.identity()
                ));
    }

    public String export(List<BookDTO> books, ReportType type){
        return reportServiceMap.get(type).generateReport(books);
    }
}
