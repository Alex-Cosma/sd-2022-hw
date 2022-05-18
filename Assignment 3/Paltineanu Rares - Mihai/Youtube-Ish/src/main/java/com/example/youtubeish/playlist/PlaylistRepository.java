package com.example.youtubeish.playlist;

import com.example.youtubeish.playlist.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> getAllByUserId(Long id);
}
