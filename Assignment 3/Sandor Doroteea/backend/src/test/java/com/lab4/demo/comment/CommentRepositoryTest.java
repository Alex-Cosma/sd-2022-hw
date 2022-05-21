package com.lab4.demo.comment;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.comment.model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepositoryImpl commentRepository;

    @BeforeEach
    public void beforeEach() {
        commentRepository.deleteAll();
    }

//    @Test
//    public void testMock() {
//        Comment commentSaved = commentRepository.save(Comment.builder()
//                        .id("sdkbfja123nsda")
//                .content("Content")
//                .userId(1L)
//                .trackId(1L).build());
//        assertNotNull(commentSaved);
//
//        assertThrows(DataIntegrityViolationException.class, () -> {
//            commentRepository.save(Comment.builder().build());
//        });
//    }

    @Test
    public void testFindAll() {
        Comment comment1 = commentRepository.save(Comment.builder()
                .content("Content1")
                .userId(1L)
                .trackId(1L).build());
        Comment comment2 = commentRepository.save(Comment.builder()
                .content("Content2")
                .userId(1L)
                .trackId(1L).build());
        List<Comment> all = commentRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    public void create(){
        Comment comment = Comment.builder()
                .content("Content2")
                .userId(2L)
                .trackId(2L)
                .build();

        Comment savedComment = commentRepository.save(comment);
        System.out.println(savedComment.getId());
        assertEquals(savedComment, commentRepository.findById(savedComment.getId()));
    }

    @Test
    public void update(){
        Comment comment = Comment.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();
        Comment comment1 = commentRepository.save(comment);
        String oldTitle = comment1.getContent();
        comment1.setContent("new content");
        commentRepository.update(comment1.getId(),comment1);
        assertNotEquals(oldTitle, commentRepository.findById(comment1.getId()).getContent());
    }

    @Test
    public void deleteComment() {
        Comment comment = Comment.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();
        Comment comment1 = commentRepository.save(comment);
        commentRepository.delete(comment1.getId());
        assertEquals(0, commentRepository.findAll().size());
    }
}