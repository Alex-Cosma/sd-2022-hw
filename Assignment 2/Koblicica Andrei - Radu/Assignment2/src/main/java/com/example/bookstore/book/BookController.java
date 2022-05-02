package com.example.bookstore.book;

import com.example.bookstore.book.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.bookstore.UrlMapping.*;


@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @CrossOrigin
    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @CrossOrigin
    @GetMapping(FILTERED)
    public List<BookDTO> filteredBooks(@PathVariable String filter) {
        System.out.println(filter);
        return bookService.findAllByFilter(filter);
    }


    @CrossOrigin
    @PostMapping
    public BookDTO create(@Valid @RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @CrossOrigin
    @PutMapping(BOOKS_ID_PART)
    public BookDTO update(@PathVariable Long id, @Valid @RequestBody BookDTO book) {
        return bookService.update(id, book);
    }

    @CrossOrigin
    @DeleteMapping(BOOKS_ID_PART)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @CrossOrigin
    @PatchMapping(BOOKS_ID_PART)
    public BookDTO sell(@PathVariable Long id, @RequestBody BookDTO book){
        return bookService.sell(id,book);
    }


}