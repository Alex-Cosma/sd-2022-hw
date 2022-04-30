package com.example.bookstore.book;

import com.example.bookstore.Report.ReportType;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.Genre;
import com.example.bookstore.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bookstore.UrlMappings.*;

@CrossOrigin
@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        List <BookDTO> books = bookService.findForReport();
        return bookService.export(books,type);
    }

    @PostMapping("/create")
    public BookDTO create(@RequestBody BookDTO book){return bookService.create(book);}

    @PutMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book){
        return bookService.edit(id,book);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

    /*@GetMapping(FILTERED)
    public List<BookDTO> findAllByTitleLikeOrAuthorLikeOrGenreLike(@RequestParam String title, @PathVariable String author, @PathVariable String genre){
        System.out.println("here");
        return bookService.findAllByTitleLikeOrAuthorLikeOrGenreLike('%' + title + '%', '%' + author + '%', '%' + genre + '%');
    }*/

    @GetMapping(FILTERED)
    public List<BookDTO> findAllGenre(@PathVariable String genre){
        return bookService.findAllGenre(genre);
    }

    @GetMapping(FILTEREDTITLE)
    public List<BookDTO> findAllTitle(@PathVariable String title){
        return bookService.findAllTitle(title);
    }

    @GetMapping(FILTEREDAUTHOR)
    public List<BookDTO> findAllAuthor(@PathVariable String author){
        return bookService.findAllAuthor(author);
    }

    @PatchMapping(ENTITY)
    public BookDTO sell(@PathVariable Long id){
        return bookService.sell(id);
    }
}
