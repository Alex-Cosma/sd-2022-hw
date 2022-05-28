package com.example.backend.service.booksApi;

import com.example.backend.entity.Book;

import java.util.List;

public interface BooksApiFetcher {

    List<Book> getBooksByTitle(String title);

    List<Book> getBooksByAuthor(String author);
}
