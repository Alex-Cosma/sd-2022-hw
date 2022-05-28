package com.example.demo.book;

import com.example.demo.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Component
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByQuantityEquals(Integer quantity);

    @Transactional
    @Modifying
    @Query (value =  "UPDATE book SET book.quantity = ?2 where book.id = ?1", nativeQuery = true)
    void sellBook(Long id, Integer quantity);

    List<Book> findAllByTitleLikeOrAuthorLikeOrGenreLike(String str1, String str2, String str3);


    List<Book> findAllByGenreEquals(String type);
}
