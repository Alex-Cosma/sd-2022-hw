package com.example.backend.service.booksApi;

import com.example.backend.entity.Book;
import com.example.backend.service.booksApi.responseParser.BooksApiResponseParser;
import io.joshworks.restclient.http.HttpResponse;
import io.joshworks.restclient.http.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksRapidApiFetcher implements BooksApiFetcher {

    @Value("${rapidApiKey}")
    private String rapidApiKey;

    private final BooksApiResponseParser booksResponseParser;

    public BooksRapidApiFetcher(BooksApiResponseParser booksResponseParser) {
        this.booksResponseParser = booksResponseParser;
    }

    @Override
    public List<Book> getBooksByTitle(String title) {

        HttpResponse<String> response = Unirest
                .post("https://books17.p.rapidapi.com/works/title")
                .header("content-type", "application/json")
                .header("X-RapidAPI-Host", "books17.p.rapidapi.com")
                .header("X-RapidAPI-Key", "dc5a996233msh2d17122f5352722p1fc1d4jsn5c912d137d53")
                .body("{\n    \"cursor\": 1,\n    \"title\": " + title + ",\n    \"subtitle\": false\n}")
                .asString();

        return this
                .booksResponseParser
                .getBooksListFromResponseByTitle(response.body());
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {

        int authorId = getAuthorId(author);

        HttpResponse<String> response = Unirest
                .post("https://books17.p.rapidapi.com/authors/" + authorId + "/works")
                .header("content-type", "application/json")
                .header("X-RapidAPI-Host", "books17.p.rapidapi.com")
                .header("X-RapidAPI-Key", rapidApiKey)
                .asString();

        return this
                .booksResponseParser
                .getBooksListFromResponseByAuthor(response.body());
    }

    private int getAuthorId(String author) {

        HttpResponse<String> response = Unirest.post("https://books17.p.rapidapi.com/authors/name")
                .header("content-type", "application/json")
                .header("X-RapidAPI-Host", "books17.p.rapidapi.com")
                .header("X-RapidAPI-Key", rapidApiKey)
                .body("{\n    \"cursor\": 1,\n    \"name\": " + author + ",\n    \"alternative_names\": false\n}")
                .asString();

        return this
                .booksResponseParser
                .getAuthorIdFromResponseByAuthorName(response.body());
    }
}
