package com.assignment2.bookstoreapp.book;

import com.assignment2.bookstoreapp.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.net.ContentHandler;
import java.util.ArrayList;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE books SET quantity = quantity - 1 WHERE id = :id AND quantity > 0", nativeQuery = true)
    void sell(Long id);

    ArrayList<Book> findAllByQuantityEquals(int quantity);

    ArrayList<Book> findAllByTitleLikeOrAuthorLikeOrGenreLike(String title, String author, String genre);

    Book findByTitle(String title);
}
