package com.example.demo.book.model;
import com.example.demo.bookreview.model.BookReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String author;

    @Column(length = 512, nullable = false)
    private String genre;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;

    @Column(length = 3500)
    private String description;

    @Column
    private String imageUrl;

    @Column
    private Integer pageCount;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "book")
    // @Builder.Default
   // private Set<Review> reviews = new HashSet<>();
    private List<BookReview> reviews;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Book other = (Book) obj;
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if ((this.reviews == null) ? (other.reviews != null) : !this.reviews.equals(other.reviews)) {
            System.out.println("reviews " + this.reviews);
            System.out.println("other " + other.reviews);
            return false;
        }
        System.out.println("reviews 2" + this.reviews);
        System.out.println("other 2" + other.reviews);
        return this.author.equals(other.author);
    }

    @Override
    public int hashCode(){
        return 0;
    }
}