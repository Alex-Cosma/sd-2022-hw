package com.example.youtubeish.comment;

import com.example.youtubeish.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByVideoId(Long id);

    List<Comment> getAllByUserId(Long id);
}
