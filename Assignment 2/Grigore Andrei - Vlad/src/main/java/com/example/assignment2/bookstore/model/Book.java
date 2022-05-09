package com.example.assignment2.bookstore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Data
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String genre;

    @Column(nullable = false)
    private String author;

    @Column
    @Builder.Default
    private int quantity = 0;

    @Column
    @Builder.Default
    private int price = 0;

}
