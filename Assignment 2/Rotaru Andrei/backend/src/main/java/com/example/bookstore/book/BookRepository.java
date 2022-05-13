package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitleAndAuthor(String title, String author);

    Page<Book> findAllByTitleLikeOrAuthorLikeOrGenreLike(String title, String author, String genre, Pageable pageable);
}
