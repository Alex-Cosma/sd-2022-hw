package com.example.backend.controller;

import com.example.backend.dto.book.BookFilterDto;
import com.example.backend.dto.book.BookGetDto;
import com.example.backend.dto.book.BookSoldDto;
import com.example.backend.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlMappings.REGULAR_USER)
@CrossOrigin
public class RegularUserController {

    private final BookService bookService;

    public RegularUserController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping(UrlMappings.SELL_BOOK)
    public BookSoldDto sellBook(@RequestParam("bookId") int bookId, @RequestParam("quantity") int quantity) {
        return bookService.sellBook(bookId, quantity);
    }

    @PostMapping(UrlMappings.FILTER_BOOKS)
    public Page<BookGetDto> filterBooks(
            @RequestBody BookFilterDto bookFilterDto,
            @PageableDefault(sort = {"title"}) Pageable pageable) {
        return bookService.filterBooks(bookFilterDto, pageable);
    }

    @GetMapping(UrlMappings.GET_ALL_BOOKS)
    public List<BookGetDto> getAllBooks() {
        return bookService.getAllBooks();
    }
}
