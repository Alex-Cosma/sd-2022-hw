package com.example.assignment2.bookstore;

import com.example.assignment2.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {

    Optional<Book> findByTitleAndQuantity(String title, int quantity);
    List<Book> findAll();
    List<Book> findAllByTitle(String title);
    List<Book> findAllByGenre(String genre);
    List<Book> findAllByAuthor(String author);


}
