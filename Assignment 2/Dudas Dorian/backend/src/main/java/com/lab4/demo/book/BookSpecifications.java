package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookFilterRequestDTO;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Optional.ofNullable;

public class BookSpecifications {
    public static Specification<Book> similarTitle(String title) {
        return (root, query, cb) -> cb.like(root.get("title"), title);
    }

    public static Specification<Book> similarAuthor(String author) {
        return (root, query, cb) -> cb.like(root.get("author"), author);
    }

    public static Specification<Book> similarGenre(String genre) {
        return (root, query, cb) -> cb.like(root.get("genre"), genre);
    }


    public static Specification<Book> specificationFromFilter(BookFilterRequestDTO filter) {
        final Specification<Book> spec = (root, query, cb) -> cb.and();
        ofNullable(filter.getTitle()).ifPresent(t -> spec.and(similarTitle(t)));
        ofNullable(filter.getAuthor()).ifPresent(a -> spec.and(similarAuthor(a)));
        ofNullable(filter.getGenre()).ifPresent(g -> spec.and(similarGenre(g)));
        return spec;
    }
}
