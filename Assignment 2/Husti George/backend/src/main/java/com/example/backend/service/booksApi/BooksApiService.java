package com.example.backend.service.booksApi;

import com.example.backend.dto.book.BookGetDto;
import com.example.backend.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksApiService {

    private final BooksApiFetcher booksApiFetcher;
    private final BookMapper bookMapper;

    public BooksApiService(BooksApiFetcher booksApiFetcher, BookMapper bookMapper) {
        this.booksApiFetcher = booksApiFetcher;
        this.bookMapper = bookMapper;
    }

    public List<BookGetDto> getBooksFromApiByAuthor(String author) {
        return this
                .booksApiFetcher
                .getBooksByAuthor(author)
                .stream()
                .map(bookMapper::bookToBookGetDto)
                .collect(Collectors.toList());
    }

    public List<BookGetDto> getBooksFromApiByTitle(String title) {
        return this
                .booksApiFetcher
                .getBooksByTitle(title)
                .stream()
                .map(bookMapper::bookToBookGetDto)
                .collect(Collectors.toList());
    }
}
