package com.example.backend.dto.book;

import javax.validation.constraints.Size;
import java.util.Date;

public class BookUpdateDto {

    @Size(min = 1, max = 100)
    public String author;

    @Size(min = 1, max = 100)
    public String title;

    @Size(min = 1, max = 100)
    public String genre;

    public double price;

    public int stock;

    public Date publishedDate;

    public BookUpdateDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookUpdateDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookUpdateDto setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public BookUpdateDto setPrice(double price) {
        this.price = price;
        return this;
    }

    public BookUpdateDto setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public BookUpdateDto setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }
}
