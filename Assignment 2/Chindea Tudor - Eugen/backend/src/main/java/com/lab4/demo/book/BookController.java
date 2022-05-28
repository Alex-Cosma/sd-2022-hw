package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allItems() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO item) {
        return bookService.create(item);
    }
    @DeleteMapping(ITEMS_ID_PART)
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }
    @PatchMapping(ITEMS_ID_PART)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO item) {
        return bookService.edit(id, item);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) throws IOException {
        return bookService.export(type);
    }
    @PatchMapping(ITEMS_SELL_ID_PART)
    public BookDTO sell (@PathVariable Long id) {
        return bookService.sell(id);
    }



}
