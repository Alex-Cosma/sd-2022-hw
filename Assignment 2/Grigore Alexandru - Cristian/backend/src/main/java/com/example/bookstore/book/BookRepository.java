package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> , JpaSpecificationExecutor<Book> {

    Optional<Book> findById(Long id);

    Optional<Book> findByTitle(String title);

    List<Book> findAll();

    List<Book> findAllByAuthor(String author);

    List<Book> findAllByGenre(String genre);

    List<Book> findAllByTitle(String title);

    List<Book> findAllByTitleLikeOrAuthorLikeOrGenreLike(String title, String author, String genre);

    List<Book> findAllByQuantityEquals(int zero);
}
