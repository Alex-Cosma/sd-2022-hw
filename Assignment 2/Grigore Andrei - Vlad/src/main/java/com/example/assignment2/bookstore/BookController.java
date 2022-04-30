package com.example.assignment2.bookstore;

import com.example.assignment2.bookstore.model.dto.BookDTO;
import com.example.assignment2.reports.ReportType;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

import static com.example.assignment2.UrlMappings.*;

@RestController
@CrossOrigin
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    public final BookService bookService;

    @GetMapping
    public List<BookDTO> allItems(){
        return bookService.findAll();
    }

    @GetMapping("/filter/{title}")
    public List<BookDTO> findAllByTitleLike(@PathVariable String title){
        return bookService.findAllByTitle(title);
    }

    @GetMapping(FILTER_AUTHOR)
    public List<BookDTO> findAllByAuthorLike(@PathVariable String author){
        return bookService.findAllByAuthor(author);
    }

    @GetMapping(FILTER_GENRE)
    public List<BookDTO> findAllByGenreLike(@PathVariable String genre){
        return bookService.findAllByGenre(genre);
    }

    @PostMapping("/create")
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PostMapping("/edit"+ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book) {
        return bookService.edit(id, book);
    }

    @DeleteMapping("/delete"+ENTITY)
    public void delete(@PathVariable Long id){bookService.delete(id);}

    @PatchMapping("/sell"+ENTITY)
    public BookDTO sell(@PathVariable Long id) {
        return bookService.sell(id);
   }

    @GetMapping(EXPORT_REPORT)
    public void exportReport(@PathVariable ReportType type) throws IOException, JRException {
        bookService.export(type);
    }



}
