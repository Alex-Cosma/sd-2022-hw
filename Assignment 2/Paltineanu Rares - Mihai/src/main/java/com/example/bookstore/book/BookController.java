package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.bookstore.UrlMapping.*;


@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    //private final ReportServiceFactory reportServiceFactory;

    @GetMapping(GET_BOOKS)
    public List<Book> allItems() {
        List<Book> books = bookService.findAll();
        return books;
    }

    @PostMapping(ADD_BOOK)
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        System.out.println(bookDTO.getName());
        return bookService.create(bookDTO);
    }

    @DeleteMapping(DELETE_BOOK)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

//    @GetMapping(EXPORT_REPORT)
//    public String exportReport(@PathVariable ReportType type) {
//        return reportServiceFactory.getReportService(type).export();
//    }
}