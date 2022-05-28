package com.lab4.demo.playlist;

import com.lab4.demo.playlist.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByTitle(String title);
}
