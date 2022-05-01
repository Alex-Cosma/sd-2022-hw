package com.example.backend.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 50)
    private String genre;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;

    @Column
    private Date publishedDate;

    public int getId() {
        return id;
    }

    public Book setId(int id) {
        this.id = id;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public Book setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Book setPrice(double price) {
        this.price = price;
        return this;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public Book setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Book setStock(int stock) {
        this.stock = stock;
        return this;
    }
}