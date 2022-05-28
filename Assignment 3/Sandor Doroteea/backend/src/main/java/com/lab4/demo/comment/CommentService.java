package com.lab4.demo.comment;

import com.lab4.demo.comment.model.Comment;
import com.lab4.demo.comment.model.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepositoryImpl commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentDTO> findAll() {
        return commentRepository.findAll().stream().map(commentMapper::toDto).collect(Collectors.toList());
    }


    public CommentDTO findById(String id) {
        return commentMapper.toDto(commentRepository.findById(id));
    }


    public CommentDTO save(CommentDTO comment) {
        return commentMapper
                .toDto(commentRepository.save(commentMapper.fromDto(comment)));
    }


    public void delete(String id) {
       commentRepository.delete(id);
    }


    public CommentDTO update(String id,CommentDTO comment) {
        Comment actualComment = commentRepository.findById(id);
        actualComment.setContent(comment.getContent());
        return commentMapper.toDto(commentRepository.update(id,actualComment));
    }

}
