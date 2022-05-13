package com.lab4.demo.frontoffice;

import com.lab4.demo.frontoffice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByTitleLikeOrAuthorLike(String title, String author);
    List<Book> findAllByQuantityEquals(int quantity);
    List<Book> findAllByTitleLikeOrAuthorLikeOrGenreLikeOrQuantityEqualsOrPriceEquals(String title, String author, String genre, int quantity, float price);
}
