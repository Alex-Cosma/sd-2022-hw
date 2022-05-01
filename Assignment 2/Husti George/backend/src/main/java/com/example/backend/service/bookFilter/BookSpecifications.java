package com.example.backend.service.bookFilter;

import com.example.backend.dto.book.BookFilterDto;
import com.example.backend.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Optional.ofNullable;

public class BookSpecifications {

    public static Specification<Book> propertyLikeValue(String property, String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(property), value);
    }

    public static Specification<Book> specificationsFromFilter(BookFilterDto filter) {

        final Specification<Book> specification = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and();

        ofNullable(filter.author).ifPresent(author -> specification.and(propertyLikeValue("author", author)));
        ofNullable(filter.genre).ifPresent(genre -> specification.and(propertyLikeValue("genre", genre)));
        ofNullable(filter.title).ifPresent(title -> specification.and(propertyLikeValue("title", title)));

        return specification;
    }
}
