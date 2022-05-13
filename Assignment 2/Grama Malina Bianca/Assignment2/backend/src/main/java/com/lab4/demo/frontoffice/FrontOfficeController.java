package com.lab4.demo.frontoffice;

import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import com.lab4.demo.frontoffice.model.dto.BookFilterRequestDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;


@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class FrontOfficeController {

    private final BookService bookService;

    @CrossOrigin
    @GetMapping
    public List<BookDTO> allItems() {
        return bookService.findAll();
    }

//    @GetMapping(FILTERED)
//    public Page<BookDTO> filteredItems(@ModelAttribute("filter") BookFilterRequestDTO filter, @PageableDefault(sort
//            = {"name"}) Pageable pageable) {
//        return bookService.findAllFiltered(filter, pageable);
//    }

    @CrossOrigin
    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book) {
        return bookService.edit(id, book);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping(ENTITY)
    public BookDTO getItem(@PathVariable Long id) {
        return bookService.get(id);
    }

    @CrossOrigin
    @PatchMapping(ENTITY)
    public BookDTO sellBook(@PathVariable Long id, @RequestBody BookDTO book) {
        return bookService.sellBook(id, book);
    }

}
