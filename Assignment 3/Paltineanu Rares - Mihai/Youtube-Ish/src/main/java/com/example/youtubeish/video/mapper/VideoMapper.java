package com.example.youtubeish.video.mapper;

import com.example.youtubeish.comment.mapper.CommentMapper;
import com.example.youtubeish.video.model.Video;
import com.example.youtubeish.video.model.dto.VideoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface VideoMapper {
    @Mappings({
            @Mapping(target = "user", source = "video.user")
    })
    VideoDTO toDto(Video video);

    @Mappings({
    })
    Video toDto(VideoDTO video);
}
