package com.app.bookingapp.controller;

import com.app.bookingapp.data.dto.model.BookDto;
import com.app.bookingapp.data.dto.model.SimpleBookDto;
import com.app.bookingapp.data.sql.entity.Book;
import com.app.bookingapp.service.BookService;
import com.app.bookingapp.service.PdfService;
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
    public List<Book> allBooks(){
        return bookService.findAll();
    }

    @GetMapping(USERNAME)
    public List<Book> allBooksByUser(@PathVariable String username){
        return bookService.allBooksByUser(username);
    }

    @PostMapping()
    public BookDto create(@RequestBody SimpleBookDto book){
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


    @PostMapping(PDF)
    public byte[] getPDF(@RequestBody BookDto book) {
        try {
            return PdfService.createPdf(book);
        } catch (Exception ex) {
            return null;
        }
    }
}
