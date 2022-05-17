package com.raulp.book;

import com.raulp.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByQuantityLessThanEqual(int quantity);
    List<Book> findBooksByTitleContainsIgnoreCaseOrAuthorContainingIgnoreCase(String filter1,
                                                                     String filter2);
}
