package com.example.backend.dto.book;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class BookCreationDto {

    @NotNull
    @Size(min = 1, max = 100)
    public String author;

    @NotNull
    @Size(min = 1, max = 100)
    public String title;

    @NotNull
    @Size(min = 1, max = 100)
    public String genre;

    @NotNull
    public double price;

    @NotNull
    public double stock;

    public Date publishedDate;

    public BookCreationDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookCreationDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookCreationDto setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public BookCreationDto setPrice(double price) {
        this.price = price;
        return this;
    }

    public BookCreationDto setStock(double stock) {
        this.stock = stock;
        return this;
    }

    public BookCreationDto setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }
}
