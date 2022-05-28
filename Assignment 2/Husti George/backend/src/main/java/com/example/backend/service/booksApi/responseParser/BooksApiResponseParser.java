package com.example.backend.service.booksApi.responseParser;

import com.example.backend.entity.Book;

import java.util.List;

public interface BooksApiResponseParser {

    List<Book> getBooksListFromResponseByAuthor(String response);

    List<Book> getBooksListFromResponseByTitle(String response);

    int getAuthorIdFromResponseByAuthorName(String response);
}
