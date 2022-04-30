package com.example.bookstore.book;

import com.example.bookstore.Report.ReportServiceFactory;
import com.example.bookstore.Report.ReportType;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.Genre;
import com.example.bookstore.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final ReportServiceFactory reportServiceFactory;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public List<BookDTO> findAllGenre(String genre){
        return bookRepository.findAllByGenre(genre).stream().
                map(bookMapper::toDto).
                collect(Collectors.toList());
    }

    public List<BookDTO> findAllByTitleLikeOrAuthorLikeOrGenreLike(String title, String author, String genre){
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(title,author,genre).stream().
                map(bookMapper::toDto).
                collect(Collectors.toList());
    }

    public Book findById(Long id){
        return bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Not good mate"));
    }

    public BookDTO create(BookDTO book){
        return bookMapper.toDto(bookRepository.save(bookMapper.fromDto(book)
        ));
    }

    public List<BookDTO> findAll(){
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findForReport(){
        return bookRepository.findAllByQuantityEquals(0).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public String export(List<BookDTO> books, ReportType type) {
        return reportServiceFactory.export(books, type);
    }

    public BookDTO edit(Long id, BookDTO book){
        Book actBook = findById(id);
        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getAuthor());
        actBook.setGenre(book.getGenre());
        actBook.setPrice(book.getPrice());
        actBook.setQuantity(book.getQuantity());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public BookDTO sell(Long id){
        Book book = findById(id);
        if(book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
        }
        return bookMapper.toDto(bookRepository.save(book));
    }

    public List<BookDTO> findAllAuthor(String author){
        return bookRepository.findAllByAuthor(author).stream().
                map(bookMapper::toDto).
                collect(Collectors.toList());
    }

    public List<BookDTO> findAllTitle(String title){
        return bookRepository.findAllByTitle(title).stream().
                map(bookMapper::toDto).
                collect(Collectors.toList());
    }


}
