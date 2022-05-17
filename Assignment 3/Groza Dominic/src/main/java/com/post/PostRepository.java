package com.post;

import com.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface PostRepository  extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    @Modifying
    void deleteById(Long id);
}
