package com.assignment2.bookstoreapp.book;

import com.assignment2.bookstoreapp.book.model.Book;
import com.assignment2.bookstoreapp.book.model.dto.BookDTO;
import com.assignment2.bookstoreapp.book.validator.BookValidator;
import com.assignment2.bookstoreapp.report.ReportServiceFactory;
import com.assignment2.bookstoreapp.report.ReportType;
import com.assignment2.bookstoreapp.report.dto.ReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.assignment2.bookstoreapp.report.ReportMapping.BOOKS_OUT_OF_STOCK;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final ReportServiceFactory reportServiceFactory;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public BookDTO findDTOById(Long id){
        return bookMapper.toDto(
                bookRepository.findById(id).get()
                );
    }


    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findById(id);
        BookValidator.updateValidation(actBook, book);
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public boolean exportBooksOutOfStock(ReportType type) {
        ArrayList<BookDTO> data =  (ArrayList) bookRepository.findAllByQuantityEquals(0).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());

        return reportServiceFactory.getReportService(type)
                .export(new ReportDTO<>(BOOKS_OUT_OF_STOCK, data));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public void sell(Long id) {
        bookRepository.sell(id);
    }

    public List<BookDTO> search(String name) {

        String query = "%" + name + "%";

         return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(query, query, query)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
}
