package com.example.youtubeish.video;

import com.example.youtubeish.video.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> getAllByUserId(Long id);
}
