package com.lab4.demo.book.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    @NotEmpty(message = "Title is required")
    private String title;

    @Column(length = 256)
    private String author;

    @Column(length = 256)
    private String genre;

    @Column(nullable = false)
    @Min(value = 0, message = "Quantity must be greater than 0")
    private Long quantity;

    @Column
    @Min(value = 0, message = "Price must be greater than 0")
    private Long price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return id != null && Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
