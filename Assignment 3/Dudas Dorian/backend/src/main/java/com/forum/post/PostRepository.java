package com.forum.post;

import com.forum.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTopicId(Long topicId);

    List<Post> findAllByUserId(Long userId);
}
