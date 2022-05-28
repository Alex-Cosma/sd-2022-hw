package com.forum.thread;


import com.forum.category.model.Category;
import com.forum.thread.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllByCategory(Category category);

    List<Topic> findAllByCategoryId(Long categoryId);

    List<Topic> findByUserId(Long userId);

    Optional<Topic> findByTitle(String title);
}
