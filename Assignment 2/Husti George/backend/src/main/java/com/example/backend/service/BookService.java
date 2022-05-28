package com.example.backend.service;

import com.example.backend.dto.book.*;
import com.example.backend.entity.Book;
import com.example.backend.mapper.BookMapper;
import com.example.backend.repository.BookRepository;
import com.example.backend.service.bookFilter.BookSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(
            BookRepository bookRepository,
            BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookGetDto createBook(BookCreationDto bookCreationDto) {

        Book book = bookMapper.bookCreationDtoToBook(bookCreationDto);
        bookRepository.save(book);

        return bookMapper.bookToBookGetDto(book);
    }

    public BookGetDto getBook(int bookId) {
        return bookRepository
                .findById(bookId)
                .map(bookMapper::bookToBookGetDto)
                .orElse(null);
    }

    public void deleteBook(int bookId) {
        bookRepository.deleteById(bookId);
    }

    public BookUpdateDto updateBook(BookUpdateDto bookUpdateDto, int bookId) {
        bookRepository.save(
                bookRepository
                        .getById(bookId)
                        .setAuthor(bookUpdateDto.author)
                        .setGenre(bookUpdateDto.genre)
                        .setPrice(bookUpdateDto.price)
                        .setTitle(bookUpdateDto.title)
                        .setPublishedDate(bookUpdateDto.publishedDate)
                        .setStock(bookUpdateDto.stock));
        return bookUpdateDto;
    }

    public BookSoldDto sellBook(int bookId, int quantity) {

        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        book.setStock(book.getStock() - quantity);
        bookRepository.save(book);

        return bookMapper.bookSoldDtoToBook(bookRepository
                .findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found")));
    }

    public List<BookGetDto> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(bookMapper::bookToBookGetDto)
                .collect(Collectors.toList());
    }

    public Page<BookGetDto> filterBooks(BookFilterDto filter, Pageable pageable) {
        return bookRepository
                .findAll(
                        BookSpecifications.specificationsFromFilter(filter),
                        pageable
                )
                .map(bookMapper::bookToBookGetDto);
    }
}
