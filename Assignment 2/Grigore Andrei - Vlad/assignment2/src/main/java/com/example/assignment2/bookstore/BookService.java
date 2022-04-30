package com.example.assignment2.bookstore;

import com.example.assignment2.bookstore.model.Book;
import com.example.assignment2.bookstore.model.dto.BookDTO;
import com.example.assignment2.reports.ReportServiceFactory;
import com.example.assignment2.reports.ReportType;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ReportServiceFactory reportServiceFactory;


    public List<BookDTO> findAll(){
        return bookRepository.findAll().stream()
                .map(bookMapper :: toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findAllByTitle(String title){
        return bookRepository.findAllByTitle(title).stream()
                .map(bookMapper :: toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findAllByAuthor(String author){
        return bookRepository.findAllByAuthor(author).stream()
                .map(bookMapper :: toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findAllByGenre(String genre){
        return bookRepository.findAllByGenre(genre).stream()
                .map(bookMapper :: toDto)
                .collect(Collectors.toList());
    }

    public Book findById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Book not found: "+id));
    }

    public BookDTO create(BookDTO item) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(item)
        ));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findById(id);
        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getAuthor());
        actBook.setGenre(book.getGenre());
        actBook.setQuantity(book.getQuantity());
        actBook.setPrice(book.getPrice());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public BookDTO sell(Long id){
        Book actBook = findById(id);
        actBook.setTitle(actBook.getTitle());
        actBook.setAuthor(actBook.getTitle());
        actBook.setGenre(actBook.getGenre());
        if(actBook.getQuantity()>0) {
            actBook.setQuantity(actBook.getQuantity() - 1);
        }
        actBook.setPrice(actBook.getPrice());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }
    public void delete(Long id){
        Book book = findById(id);
        bookRepository.delete(book);
    }

    public String export(ReportType type) throws IOException, JRException {
        return reportServiceFactory.getReportService(type).export(findAll());
    }


}
