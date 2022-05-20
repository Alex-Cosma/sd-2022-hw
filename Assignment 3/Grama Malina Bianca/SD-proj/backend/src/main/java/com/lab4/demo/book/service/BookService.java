package com.lab4.demo.book.service;

import com.lab4.demo.book.dto.BookDTO;
import com.lab4.demo.book.mapper.BookMapper;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.EGenre;
import com.lab4.demo.book.repository.BookRepository;
import com.lab4.demo.book.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final GenreRepository genreRepository;

    private Book findById(Long id) {
        return bookRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
    }

    public List<BookDTO> findAll() {
        List<Book> books = bookRepository.findAll();

        List<BookDTO> bookDTOS = books.stream().map(bookMapper::toDto).collect(java.util.stream.Collectors.toList());

        for (Book book : books) {
            bookMapper.populateBookDTOGenre(book, bookDTOS.get(books.indexOf(book)));
        }

        return bookDTOS;
    }

    public BookDTO create(BookDTO bookDTO) {
        Book book = bookMapper.fromDto(bookDTO);
        book.setGenre(genreRepository.findByName(EGenre.valueOf(bookDTO.getGenre())));
        BookDTO bookDTO1 =  bookMapper.toDto(bookRepository.save(book));
        bookMapper.populateBookDTOGenre(book, bookDTO1);
        return bookDTO1;
    }

    public BookDTO edit(Long id, BookDTO bookDTO) {
        Book book = findById(id);
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(genreRepository.findByName(EGenre.valueOf(bookDTO.getGenre())));
        book.setDescription(bookDTO.getDescription());
        book.setPages(bookDTO.getPages());
        book.setYear(bookDTO.getYear());
        book.setQuantity(bookDTO.getQuantity());
        BookDTO bookDTO1 = bookMapper.toDto(bookRepository.save(book));
        bookMapper.populateBookDTOGenre(book, bookDTO1);
        return bookDTO1;
    }

    public void delete(Long id) {
        bookRepository.delete(findById(id));
    }

    public BookDTO get(Long id) {
        return bookMapper.toDto(findById(id));
    }

    public Book sellBook(Long id) {
        Book book = findById(id);
        book.setQuantity(book.getQuantity() - 1);
        return bookRepository.save(book);
    }
}
