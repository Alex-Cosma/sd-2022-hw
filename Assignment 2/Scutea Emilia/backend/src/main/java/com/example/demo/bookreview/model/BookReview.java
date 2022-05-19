package com.example.demo.bookreview.model;

import com.example.demo.book.model.Book;
import com.example.demo.bookreview.model.Rating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookReview {
    @Id
    @GeneratedValue
    private Long id;
    private String text;

    @Column(length = 512, nullable = false)
    private String rating;

    @ManyToOne
    @JoinColumn(name="book_id")
    @JsonIgnore
    private Book book;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final BookReview other = (BookReview) obj;
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        if (!Objects.equals(this.book, other.book)) {
            return false;
        }
        return this.rating.equals(other.rating);
    }
}
