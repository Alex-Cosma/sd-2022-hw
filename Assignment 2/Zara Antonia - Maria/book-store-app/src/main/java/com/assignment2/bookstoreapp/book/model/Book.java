package com.assignment2.bookstoreapp.book.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Getter
@Setter
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String author;

    @Column(length = 255, nullable = false)
    private String genre;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;
}
