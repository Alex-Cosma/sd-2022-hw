package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @GetMapping(FILTERED)
    public List<BookDTO> filteredBooks(@PathVariable String filter){
        return bookService.findAllFilteredBooks("%" + filter + "%");
    }

    @PostMapping
    public BookDTO create(@Valid @RequestBody BookDTO book){
        return bookService.create(book);
    }

    @PutMapping(BOOKS_ID_PART)
    public BookDTO edit(@PathVariable Long id, @Valid @RequestBody BookDTO book){
        return bookService.edit(id, book);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return bookService.export(type);
    }

    @PutMapping(SELL + BOOKS_ID_PART)
    public BookDTO sellBooks(@PathVariable Long id, @RequestBody int quantity){
        return bookService.sellBooks(id, quantity);
    }

    @DeleteMapping(BOOKS_ID_PART)
    public BookDTO delete(@PathVariable Long id){
        return bookService.delete(id);
    }
}