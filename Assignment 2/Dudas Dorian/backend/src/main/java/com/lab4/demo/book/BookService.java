package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.model.dto.BookFilterRequestDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.lab4.demo.book.BookSpecifications.specificationFromFilter;

@Service
@RequiredArgsConstructor
public class BookService {
    private final ReportServiceFactory reportServiceFactory;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO bookDTO) {
        return bookMapper.toDto(bookRepository.save(bookMapper.fromDto(bookDTO)));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findById(id);
        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getAuthor());
        actBook.setGenre(book.getGenre());
        actBook.setQuantity(book.getQuantity());
        actBook.setPrice(book.getPrice());
        return bookMapper.toDto(bookRepository.save(actBook));
    }

//    public Page<BookDTO> findAllFiltered(String searchTerm, Pageable pageable) {
//        final Page<Book> bookPage = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(searchTerm, pageable);
//        return bookPage.map(bookMapper::toDto);
//    }

    public BookDTO sellBooks(Long id, int quantity) {
        Book book = findById(id);
        if (book.getQuantity() < quantity) { // should be checked in frontend and conditioned in controller
            throw new IllegalArgumentException("Not enough books in stock");
        }
        book.setQuantity(book.getQuantity() - quantity);
        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO delete(Long id) {
        Book book = findById(id);
        bookRepository.delete(book);
        return bookMapper.toDto(book);
    }

    public Page<BookDTO> findAllFilteredBooks(BookFilterRequestDTO filter, Pageable pageable) {
        return bookRepository.findAll(specificationFromFilter(filter), pageable)
                .map(bookMapper::toDto);
    }

    public List<BookDTO> findAllFilteredBooks(String filter) {
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(filter).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<BookDTO> findAllOutOfStock() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getQuantity() <= 0)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public String export(ReportType type) {
        return reportServiceFactory.getReportService(type).export(findAllOutOfStock());
    }
}
