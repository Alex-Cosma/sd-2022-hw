package com.lab4.demo.book.repository;


import com.lab4.demo.book.model.EGenre;
import com.lab4.demo.book.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(EGenre name);
}
