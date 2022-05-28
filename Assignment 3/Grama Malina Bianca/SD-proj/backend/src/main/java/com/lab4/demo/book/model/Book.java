package com.lab4.demo.book.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column
    private int year;

    @Column
    private int pages;

    @Column
    @Builder.Default
    private int quantity = 1;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "genre")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Genre genre;

    @Column(length = 1024)
    private String description;

}
