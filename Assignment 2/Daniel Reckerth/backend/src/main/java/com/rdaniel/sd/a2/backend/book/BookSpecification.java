package com.rdaniel.sd.a2.backend.book;

import com.rdaniel.sd.a2.backend.book.dto.BookFilterRequestDto;
import com.rdaniel.sd.a2.backend.book.model.Book;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Optional.ofNullable;

public class BookSpecification {

  public static Specification<Book> similarTitle(String title) {
    return (root, query, cb) -> cb.like(root.get("title"), title);
  }

  public static Specification<Book> similarAuthor(String author) {
    return (root, query, cb) -> cb.like(root.get("author"), author);
  }

  public static Specification<Book> similarGenre(String genre) {
    return (root, query, cb) -> cb.like(root.get("genre"), genre);
  }

  public static Specification<Book> similarPrice(double price) {
    return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), price);
  }

  public static Specification<Book> specificationFromFilter(BookFilterRequestDto bookFilterRequestDto) {
    Specification<Book> specification = (root, query, cb) -> cb.conjunction();
    if(bookFilterRequestDto.getTitle() != null && !bookFilterRequestDto.getTitle().isEmpty()) {
      specification = specification.and(similarTitle(bookFilterRequestDto.getTitle()));
    }
    if(bookFilterRequestDto.getAuthor() != null && !bookFilterRequestDto.getAuthor().isEmpty()) {
      specification = specification.and(similarAuthor(bookFilterRequestDto.getAuthor()));
    }
    if(bookFilterRequestDto.getGenre() != null && !bookFilterRequestDto.getGenre().isEmpty()) {
      specification = specification.and(similarGenre(bookFilterRequestDto.getGenre()));
    }
      specification = specification.and(similarPrice(bookFilterRequestDto.getPrice()));

    return specification;
  }
}
