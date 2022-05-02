package com.lab4.demo.frontoffice;

import com.lab4.demo.frontoffice.mapper.BookMapper;
import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findAllOutOfStock() {
        return bookRepository.findAllByQuantityEquals(0).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO changeTitle(Long id, String newName) {
        Book book = findById(id);
        book.setTitle(newName);
        return bookMapper.toDto(bookRepository.save(book));
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

    public BookDTO sellBook(Long id, BookDTO bookDTO) {
        Book book = findById(id);
        book.setQuantity(book.getQuantity() - 1);
        return bookMapper.toDto(
                bookRepository.save(book)
        );
    }

    public BookDTO get(Long id) {
        return bookMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}
