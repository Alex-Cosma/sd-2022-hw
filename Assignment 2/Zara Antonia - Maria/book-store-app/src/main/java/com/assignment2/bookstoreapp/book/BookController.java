package com.assignment2.bookstoreapp.book;

import com.assignment2.bookstoreapp.book.model.dto.BookDTO;
import com.assignment2.bookstoreapp.report.ReportType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.assignment2.bookstoreapp.URLMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
@CrossOrigin
public class BookController {

    private final BookService bookService;

    @GetMapping(FIND_ALL)
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping(ADD)
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PostMapping(DELETE + BOOKS_ID_PART)
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

    @PutMapping(UPDATE + BOOKS_ID_PART)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO bookUpdate) {
        return bookService.edit(id, bookUpdate);
    }

    @GetMapping(FIND + BOOKS_ID_PART)
    public BookDTO findBook(@PathVariable Long id){
        return bookService.findDTOById(id);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return bookService.export(type);
    }
}
