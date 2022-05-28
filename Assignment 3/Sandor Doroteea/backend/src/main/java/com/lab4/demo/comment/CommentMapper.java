package com.lab4.demo.comment;

import com.lab4.demo.comment.model.Comment;
import com.lab4.demo.comment.model.dto.CommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDTO toDto(Comment comment);

    Comment fromDto(CommentDTO commentDTO);

}
