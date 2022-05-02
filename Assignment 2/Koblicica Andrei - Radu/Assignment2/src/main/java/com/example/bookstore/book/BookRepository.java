package com.example.bookstore.book;



import com.example.bookstore.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);

    List<Book> findAll();

    List<Book> findAllByTitleLikeOrAuthorLikeOrGenreLike(String title, String author, String genre);

    List<Book> findAllByQuantityEquals(Integer quantity);

    void deleteById(Long id);

    void deleteAll();

    //BookDTO updateTitle(Long id, BookDTO book);





}
