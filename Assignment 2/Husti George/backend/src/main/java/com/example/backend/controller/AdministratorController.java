package com.example.backend.controller;

import com.example.backend.dto.book.BookCreationDto;
import com.example.backend.dto.book.BookGetDto;
import com.example.backend.dto.book.BookUpdateDto;
import com.example.backend.dto.report.ReportRequestResponseDto;
import com.example.backend.dto.user.UserCreationDto;
import com.example.backend.dto.user.UserGetDto;
import com.example.backend.dto.user.UserUpdateDto;
import com.example.backend.service.BookService;
import com.example.backend.service.UserService;
import com.example.backend.service.booksApi.BooksApiService;
import com.example.backend.service.booksOutOfStockReport.ReportServiceFactory;
import com.example.backend.service.booksOutOfStockReport.ReportType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(UrlMappings.ADMINISTRATOR)
@CrossOrigin
public class AdministratorController {

    private final ReportServiceFactory reportServiceFactory;
    private final UserService userService;
    private final BookService bookService;
    private final BooksApiService booksApiService;

    public AdministratorController(
            ReportServiceFactory reportServiceFactory,
            UserService userService,
            BookService bookService,
            BooksApiService booksApiService) {
        this.reportServiceFactory = reportServiceFactory;
        this.userService = userService;
        this.bookService = bookService;
        this.booksApiService = booksApiService;
    }

    @PostMapping(UrlMappings.CREATE_USER)
    public UserGetDto createUser(@Valid @RequestBody UserCreationDto userCreationDto) {
        return userService.createUser(userCreationDto);
    }

    @PutMapping(UrlMappings.UPDATE_USER)
    public UserUpdateDto updateUser(@RequestBody UserUpdateDto userUpdateDto, @RequestParam("userId") int userId) {
        return userService.updateUser(userUpdateDto, userId);
    }

    @GetMapping(UrlMappings.GET_USER)
    public UserGetDto getUser(@RequestParam("userId") int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping(UrlMappings.DELETE_USER)
    public void deleteUser(@RequestParam("userId") int userId) {
        userService.deleteUser(userId);
    }

    @PostMapping(UrlMappings.CREATE_BOOK)
    public BookGetDto createBook(@RequestBody BookCreationDto bookCreationDto) {
        return bookService.createBook(bookCreationDto);
    }

    @PutMapping(UrlMappings.UPDATE_BOOK)
    public BookUpdateDto updateBook(@RequestBody BookUpdateDto bookUpdateDto, @RequestParam("bookId") int bookId) {
        return bookService.updateBook(bookUpdateDto, bookId);
    }

    @GetMapping(UrlMappings.GET_BOOK)
    public BookGetDto getBook(@RequestParam("bookId") int bookId) {
        return bookService.getBook(bookId);
    }

    @GetMapping(UrlMappings.DELETE_BOOK)
    public void deleteBook(@RequestParam("bookId") int bookId) {
        bookService.deleteBook(bookId);
    }

    @GetMapping(UrlMappings.GENERATE_REPORT_OUT_OF_STOCK_BOOKS)
    public ResponseEntity<ReportRequestResponseDto> generateBooksOutOfStockReport(
            @RequestParam("reportFormat") String reportFormat,
            @RequestParam("downloadPath") String downloadPath,
            @RequestParam("author") String author) {

        ReportType reportType;

        try {
            reportType = ReportType.valueOf(reportFormat);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new ReportRequestResponseDto().setResponse("Invalid report format"));
        }

        reportServiceFactory
                .getReportService(reportType)
                .export("downloadPath", "author");

        return ResponseEntity
                .ok()
                .body(new ReportRequestResponseDto().setResponse("Invalid report format"));
    }

    @GetMapping(UrlMappings.GET_BOOKS_BY_TITLE_FROM_API)
    public List<BookGetDto> getBooksByTitleFromApi(@RequestParam("title") String title) {
        return booksApiService.getBooksFromApiByTitle(title);
    }

    @GetMapping(UrlMappings.GET_BOOKS_BY_AUTHOR_FROM_API)
    public List<BookGetDto> getBooksByAuthorFromApi(@RequestParam("author") String author) {
        return booksApiService.getBooksFromApiByAuthor(author);
    }
}
