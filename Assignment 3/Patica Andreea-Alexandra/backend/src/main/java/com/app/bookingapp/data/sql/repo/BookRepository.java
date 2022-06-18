package com.app.bookingapp.data.sql.repo;

import com.app.bookingapp.data.sql.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    //List<Book> findAllByUserId(Long id);

    List<Book> findAllByUserUsername(String username);
}
