package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final ReportServiceFactory reportServiceFactory;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<BookDTO> findAllByTitleOrAuthorOrGenre(String searchedText){
        System.out.println(searchedText);
        return bookRepository.findAllByAuthorLikeOrGenreLikeOrTitleLike("%"+searchedText+"%","%"+searchedText+"%","%"+searchedText+"%")
                .stream().map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
    public BookDTO create(BookDTO item) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(item)
        ));
    }
    public void delete(Long id){
        Book book = findById(id);
        bookRepository.delete(book);
    }
    public BookDTO edit(Long id, BookDTO item) {
        Book actBook = findById(id);
        actBook.setTitle(item.getTitle());
        actBook.setAuthor(item.getAuthor());
        actBook.setGenre(item.getGenre());
        actBook.setPrice(item.getPrice());
        actBook.setQuantity(item.getQuantity());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }
public BookDTO sell(Long id) {
    Book actBook = findById(id);
    if(actBook.getQuantity() > 0){
            actBook.setQuantity(actBook.getQuantity() - 1);
        }
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public String export(ReportType type) throws IOException {

        return reportServiceFactory.getReportService(type).export(bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList()));
    }
}
