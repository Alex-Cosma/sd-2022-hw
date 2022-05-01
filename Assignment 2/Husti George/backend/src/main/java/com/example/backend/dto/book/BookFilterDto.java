package com.example.backend.dto.book;

public class BookFilterDto {
    public String title;
    public String author;
    public String genre;

    public BookFilterDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookFilterDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookFilterDto setGenre(String genre) {
        this.genre = genre;
        return this;
    }
}
