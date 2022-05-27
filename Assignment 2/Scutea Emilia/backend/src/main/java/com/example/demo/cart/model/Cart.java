package com.example.demo.cart.model;

import com.example.demo.book.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    @ManyToMany(fetch = FetchType.EAGER, cascade =CascadeType.MERGE)
            // , mappedBy = "cart")
    @JoinTable(name = "cart_books",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "books_id"))
    private List<Book> books;


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Cart other = (Cart) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.books, other.books)) {
            return false;
        }
        return this.user_id.equals(other.user_id);
    }


}
