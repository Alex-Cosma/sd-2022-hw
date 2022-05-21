package com.lab4.demo.comment;

import com.lab4.demo.comment.model.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> findAll();
    Comment findById(String id);
    Comment save(Comment comment);
    void delete(String id);
    Comment update(String id,Comment comment);
    void deleteAll();

}
