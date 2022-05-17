package com.raulp.book;

import com.raulp.book.model.Genre;
import com.raulp.book.model.SearchObject;
import com.raulp.book.model.dto.BookDTO;
import com.raulp.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.raulp.UrlMapping.EXPORT_REPORT;
import static com.raulp.UrlMapping.BOOKS;
import static com.raulp.UrlMapping.BOOKS_ID_PART;
import static com.raulp.UrlMapping.FILTER;
import static com.raulp.UrlMapping.GENRES;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allItems() {
        return bookService.findAll();
    }

    @GetMapping(GENRES)
    public List<String> getAllGenres() {
        return Arrays.stream(Genre.values()).map(Genre::name).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/outOfStock")
    public List<BookDTO> outOfStock() {
        return bookService.findOutOfStock();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PutMapping(BOOKS_ID_PART)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book) {
        return bookService.edit(id, book);
    }

    @PutMapping(BOOKS_ID_PART + "/sellOne")
    public BookDTO sellOne(@PathVariable Long id) {
        return bookService.sellOne(id);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return bookService.export(type);
    }

    @PostMapping(FILTER)
    public List<BookDTO> filter(@RequestBody SearchObject filterObject) {
        System.out.println(bookService.filter(filterObject.filter));
        return bookService.filter(filterObject.filter);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
