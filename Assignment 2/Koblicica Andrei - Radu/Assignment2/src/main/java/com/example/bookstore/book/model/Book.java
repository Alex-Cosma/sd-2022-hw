package com.example.bookstore.book.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 256, nullable = false)
    private String title;
    @Column(length = 256, nullable = false)
    private String author;
    @Column(length = 256, nullable = false)
    private String genre;
    @Column(nullable=false)
    @Min(0)
    private float price;
    @Column(nullable=false)
    @Min(0)
    private int quantity;
}
