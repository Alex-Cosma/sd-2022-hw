package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.report.ReportServiceFactory;
import com.example.bookstore.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final ReportServiceFactory reportServiceFactory;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookDTO findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id)));
    }

    public List<BookDTO> findAllAvailable() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getQuantity() > 0)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findAllUnavailable() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getQuantity() == 0)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO findByTitleAndAuthor(String title, String author) {
        return bookMapper.toDto(bookRepository.findByTitleAndAuthor(title, author));
    }

    public List<BookDTO> filterBooks(String filter){
        PageRequest pageRequest = PageRequest.of(0, 10);
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%"+filter+"%",
                        "%"+filter+"%","%"+filter+"%",pageRequest).stream()
                .filter(book -> book.getQuantity() > 0)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.fromDto(book)));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book bookToEdit = bookRepository.findById(id).get();
        bookToEdit.setTitle(book.getTitle());
        bookToEdit.setAuthor(book.getAuthor());
        bookToEdit.setGenre(book.getGenre());
        bookToEdit.setQuantity(book.getQuantity());
        bookToEdit.setPrice(book.getPrice());

        return bookMapper.toDto(bookRepository.save(bookToEdit));
    }

    public BookDTO sell(Long id){
        Book bookToSell = bookRepository.findById(id).get();
        bookToSell.setQuantity(bookToSell.getQuantity() - 1);

        return bookMapper.toDto(bookRepository.save(bookToSell));
    }

    public String export(ReportType type) {
        return reportServiceFactory.getReportService(type).export(findAllUnavailable());
    }
}
