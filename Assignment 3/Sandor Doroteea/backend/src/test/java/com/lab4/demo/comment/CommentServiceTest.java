package com.lab4.demo.comment;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.comment.model.Comment;
import com.lab4.demo.comment.model.dto.CommentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentRepositoryImpl commentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(commentRepository, commentMapper);
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
        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        when(commentRepository.findAll()).thenReturn(comments);

        List<CommentDTO> all = commentService.findAll();

        Assertions.assertEquals(comments.size(), all.size());
    }

    @Test
    void create() {
        CommentDTO commentDTO = CommentDTO.builder()
                .id("1L")
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        when(commentMapper.toDto(commentRepository.save(commentMapper.fromDto(commentDTO)))).thenReturn(commentDTO);

        CommentDTO commentDTO1 = commentService.save(commentDTO);

        Assertions.assertEquals(commentDTO,commentDTO1);
    }

    @Test
    void findById() {
        CommentDTO commentDTO = CommentDTO.builder()
                .id("1L")
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        when(commentMapper.toDto(commentRepository.findById("1L"))).thenReturn(commentDTO);

        CommentDTO commentDTO1 = commentService.findById(commentDTO.getId());

        Assertions.assertEquals(commentDTO,commentDTO1);
    }

    @Test
    void update() {
        CommentDTO commentDTO = CommentDTO.builder()
                .id("1L")
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        Comment comment = Comment.builder()
                .id("1L")
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        when(commentRepository.findById(comment.getId())).thenReturn(comment);
        when(commentMapper.toDto(commentRepository.save(comment))).thenReturn(commentDTO);


        CommentDTO newComment = commentService.update(comment.getId(), commentDTO);
        Assertions.assertEquals(commentDTO,newComment);
    }

    @Test
    void delete() {
        Comment comment = Comment.builder()
                .id("1L")
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentRepository.findById(comment.getId())).thenReturn(comment);

        commentService.delete(comment.getId());
        Assertions.assertNotNull(commentRepository.findById(comment.getId()));
    }
}