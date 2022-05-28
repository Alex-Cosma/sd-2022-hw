package com.lab4.demo.book;

import com.lab4.demo.book.dto.BookDTO;
import com.lab4.demo.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.BOOKS;
import static com.lab4.demo.UrlMapping.ENTITY;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @CrossOrigin
    @GetMapping
    public List<BookDTO> getBooks() {
        return bookService.findAll();
    }

    @CrossOrigin
    @PostMapping
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        return bookService.create(bookDTO);
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public BookDTO update(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return bookService.edit(id, bookDTO);
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


}
