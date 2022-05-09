package com.example.backend.service.booksApi.responseParser;

import com.example.backend.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RapidApiBooksResponseParser implements BooksApiResponseParser {
    @Override
    public List<Book> getBooksListFromResponseByAuthor(String response) {
        return null;
    }

    @Override
    public List<Book> getBooksListFromResponseByTitle(String response) {
        return null;
    }

    @Override
    public int getAuthorIdFromResponseByAuthorName(String response) {
        return 0;
    }
}
