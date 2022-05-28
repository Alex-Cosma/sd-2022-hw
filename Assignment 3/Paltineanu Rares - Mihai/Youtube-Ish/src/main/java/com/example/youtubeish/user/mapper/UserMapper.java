package com.example.youtubeish.user.mapper;

import com.example.youtubeish.comment.dto.CommentDTO;
import com.example.youtubeish.comment.model.Comment;
import com.example.youtubeish.playlist.dto.PlaylistDTO;
import com.example.youtubeish.playlist.model.Playlist;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.dto.VideoDTO;
import com.example.youtubeish.video.model.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(target = "username", source = "username")
    })
    UserDTO toDto(User user);

    @Mappings({
            @Mapping(target = "username", source = "username")
    })
    User fromDto(UserDTO user);

    @Mappings({
            @Mapping(target = "user", source = "video.user")
    })
    VideoDTO toDto(Video video);

    @Mappings({
    })
    Video fromDto(VideoDTO video);

    @Mappings({
            @Mapping(target = "author", source = "comment.user.username")
    })
    CommentDTO toDto(Comment comment);

    @Mappings({
            @Mapping(target = "video", ignore = true)
    })
    Comment fromDto(CommentDTO comment);

    PlaylistDTO toDto(Playlist playlist);
}
