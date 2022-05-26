package com.app.movie;

import com.app.movie.model.EGenre;
import com.app.movie.model.Genre;
import com.app.user.model.ERole;
import com.app.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(EGenre genre);
}
