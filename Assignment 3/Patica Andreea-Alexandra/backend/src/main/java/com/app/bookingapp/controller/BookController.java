package com.app.bookingapp.controller;

import com.app.bookingapp.data.dto.model.BookDto;
import com.app.bookingapp.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.bookingapp.utils.URLMapping.*;

@RestController
@RequestMapping(BOOK)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> allCards(){
        return bookService.findAll();
    }

    @GetMapping(USERNAME)
    public List<BookDto> allBooksByUser(@PathVariable String username){
        return bookService.allBooksByUser(username);
    }

    @PostMapping()
    public BookDto create(@RequestBody BookDto book){
        return bookService.create(book);
    }

    @PatchMapping(ID)
    public BookDto update(@PathVariable Long id, @RequestBody BookDto book) {
        return bookService.update(id, book);
    }

    @DeleteMapping(ID)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
