package com.example.bookstore.book.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String title;

    @Column(nullable = false, length = 512)
    private String author;

    @Column(nullable = false, length = 256)
    private String genre;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

}
