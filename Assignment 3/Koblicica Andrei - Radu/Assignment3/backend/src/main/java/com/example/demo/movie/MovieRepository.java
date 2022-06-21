package com.example.demo.movie;

import com.example.demo.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findById(Long id);

    List<Movie> findAll();

    List<Movie> findAllByTitleLike(@Param("title") String title);

    void deleteById(Long id);
}
