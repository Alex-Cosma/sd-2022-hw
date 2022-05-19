package com.example.demo.book;

import com.example.demo.book.model.GenreType;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.report.ReportType;
import com.example.demo.report.storage.StorageService;
import com.example.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
public class BookstoreController {

    private final BookstoreService bookstoreService;

    private final StorageService storageService;

    // BOOKSTORE
    @GetMapping(BOOKSTORE)
    public List<BookDTO> allBooks() {
        return bookstoreService.findAll();
    }

    @GetMapping(BOOKSTORE_SEARCH_BOOKS)
    public List<BookDTO> searchBooks(@PathVariable String str) {
        return bookstoreService.searchItems(str);
    }

    @PutMapping(BOOKSTORE_ID_SELL)
    public ResponseEntity<?> sellBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        if(bookstoreService.decreaseBookQuantity(id, bookDTO)){
            return ResponseEntity.ok(new MessageResponse(String.format("Book with id %d decreased quantity",id)));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: Book with id %d not in stock",id)));
    }

    @GetMapping(BOOKSTORE_GENRE_TYPES)
    public List<GenreType> getAllGenreTypes(){
        return bookstoreService.getAllGenreTypes();
    }

    @GetMapping(BOOKSTORE_BOOK_BY_GENRE)
    public List<BookDTO> getBooksByGenre(@PathVariable GenreType genre){
        return bookstoreService.getBooksByGenre(genre);
    }


    // CRUD ON BOOKS
    @GetMapping(BOOKS)
    public List<BookDTO> getAllBooks() {
        return bookstoreService.findAll();
    }

    @PostMapping(BOOKS_CREATE)
    public BookDTO create(@RequestBody BookDTO item) {
        return bookstoreService.create(item);
    }

    @PutMapping(BOOKS_ID_EDIT)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO item) {
        return bookstoreService.edit(id, item);
    }

    @DeleteMapping(BOOKS_ID_DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        bookstoreService.delete(id);
            return ResponseEntity.ok(new MessageResponse(String.format("Book with id %d successfully deleted",id)));

//        return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: Book with id %d not found",id)));
    }

    @GetMapping(EXPORT_REPORT)
    public ResponseEntity<Resource> exportReport(@PathVariable ReportType type) {
        String filename = bookstoreService.export(type);
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
