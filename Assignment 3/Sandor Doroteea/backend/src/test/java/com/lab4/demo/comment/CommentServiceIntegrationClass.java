package com.lab4.demo.comment;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.comment.model.Comment;
import com.lab4.demo.comment.model.dto.CommentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentServiceIntegrationClass {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepositoryImpl commentRepository;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
    }

    @Test
    void findAll() {
        Comment comment1 = commentRepository.save(Comment.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L).build());
        Comment comment2 = commentRepository.save(Comment.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L).build());
        List<Comment> all1 = commentRepository.findAll();

        List<CommentDTO> all2 = commentService.findAll();

        Assertions.assertEquals(all1.size(), all2.size());
    }

    @Test
    void create(){
        CommentDTO commentDTO = CommentDTO.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();
        CommentDTO commentDTO1 = commentService.save(commentDTO);

        Assertions.assertNotNull(commentDTO1);
        Assertions.assertEquals(commentDTO.getContent(),commentDTO1.getContent());
    }

    @Test
    void update(){
        CommentDTO commentDTO = CommentDTO.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        CommentDTO commentDTO1=  commentService.save(commentDTO);

        commentDTO1.setContent("Another Comment");
        CommentDTO commentDTO2 = commentService.update(commentDTO1.getId(),commentDTO1);

        Assertions.assertNotNull(commentDTO2);
        Assertions.assertEquals(commentDTO2.getContent(),commentDTO1.getContent());
    }

    @Test
    void delete(){
        CommentDTO commentDTO = CommentDTO.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        CommentDTO commentDTO1 =  commentService.save(commentDTO);

        commentService.delete(commentDTO1.getId());
        List<CommentDTO> all = commentService.findAll();

        Assertions.assertEquals(0, all.size());
    }
}
