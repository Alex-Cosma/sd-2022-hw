package com.example.bookstore.book;

import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.report.ReportType;
import com.example.bookstore.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.bookstore.UrlMapping.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allItems() {
        return bookService.findAllAvailable();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(FILTER)
    public List<BookDTO> filterBooks(@PathVariable String filter){
        return bookService.filterBooks(filter);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BookDTO book) {
        if(bookService.findByTitleAndAuthor(book.getTitle(),book.getAuthor()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Title and author already exist!"));
        }

        bookService.create(book);

        return ResponseEntity.ok(new MessageResponse("Book created successfully"));
    }

    @CrossOrigin(origins = "*")
    @PatchMapping(BOOK_ID_PATH)
    public ResponseEntity<?> edit(@PathVariable Long id ,@Valid  @RequestBody BookDTO book) {
        if(!bookService.findById(id).getTitle().equals(book.getTitle())) {
            if (bookService.findByTitleAndAuthor(book.getTitle(), book.getAuthor()) != null) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Title and author already exist!"));
            }
        }

        bookService.edit(id, book);

        return ResponseEntity.ok(new MessageResponse("Book edited successfully"));
    }

    @DeleteMapping(BOOK_ID_PATH)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Book deleted successfully"));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(BOOK_ID_PATH)
    public ResponseEntity<?> sell(@PathVariable Long id) {

        bookService.sell(id);

        return ResponseEntity.ok(new MessageResponse("Book sold successfully"));
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return bookService.export(type);
    }
}
