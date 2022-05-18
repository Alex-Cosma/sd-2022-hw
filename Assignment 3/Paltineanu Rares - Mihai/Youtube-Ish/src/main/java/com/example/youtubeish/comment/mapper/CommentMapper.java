package com.example.youtubeish.comment.mapper;

import com.example.youtubeish.comment.model.Comment;
import com.example.youtubeish.comment.dto.CommentDTO;
import com.example.youtubeish.user.UserService;
import com.example.youtubeish.video.VideoService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserService.class, VideoService.class})
public interface CommentMapper {

    @Mappings({
            @Mapping(target = "author", source = "comment.user.username")
    })
    CommentDTO toDto(Comment comment);

    @Mappings({
            @Mapping(target = "video", ignore = true)
    })
    Comment fromDto(CommentDTO comment);
}
