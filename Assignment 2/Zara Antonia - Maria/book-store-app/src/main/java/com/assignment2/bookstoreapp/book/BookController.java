package com.assignment2.bookstoreapp.book;

import com.assignment2.bookstoreapp.book.model.dto.BookDTO;
import com.assignment2.bookstoreapp.report.ReportType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.assignment2.bookstoreapp.URLMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
@CrossOrigin
public class BookController {

    private final BookService bookService;

    @GetMapping(FIND_ALL)
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping(ADD)
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PostMapping(DELETE + BOOKS_ID_PART)
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

    @PutMapping(UPDATE + BOOKS_ID_PART)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO bookUpdate) {
        return bookService.edit(id, bookUpdate);
    }

    @GetMapping(FIND + BOOKS_ID_PART)
    public BookDTO findBook(@PathVariable Long id){
        return bookService.findDTOById(id);
    }

    /*
    Tried to make the files downloadable, did not work, but they are generated :(
     */
    @RequestMapping(EXPORT_REPORT + BOOKS_OUT_OF_STOCK_REPORT)
    public boolean exportReport(HttpServletResponse response, @PathVariable ReportType type) throws IOException {

        String file;

        switch(type) {
            case PDF:
                file = "report.pdf";
                response.setContentType("application/pdf");
                break;
            case CSV:
                file = "report.csv";
                response.setContentType("text/csv");
                break;
            default:
                file = "";
        }

        boolean state = bookService.exportBooksOutOfStock(type);

        Path path = Paths.get(file);

        if(state) {
            response.addHeader("Content-Disposition", "attachment; filename=" + file);

            Files.copy(path, response.getOutputStream());
            response.getOutputStream().flush();
        }

        return state;
    }

    @PostMapping(SELL + BOOKS_ID_PART)
    public void sellBook(@PathVariable Long id){
        bookService.sell(id);
    }

    /*
    Ran into this bug, on my machine (or in my project) I can only search once successfully
    Must click twice on search because React doesn't synchronize in first try
    https://github.com/spring-projects/spring-data-jpa/issues/2476
     */
    @GetMapping(SEARCH)
    public List<BookDTO> search(@PathVariable String detail){
        return bookService.search(detail);
    }
}
