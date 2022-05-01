package com.example.backend.dto.book;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class BookGetDto {

    @NotNull
    public int id;

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

    public BookGetDto setId(int id) {
        this.id = id;
        return this;
    }

    public BookGetDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookGetDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookGetDto setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public BookGetDto setPrice(double price) {
        this.price = price;
        return this;
    }

    public BookGetDto setStock(double stock) {
        this.stock = stock;
        return this;
    }

    public BookGetDto setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }
}
