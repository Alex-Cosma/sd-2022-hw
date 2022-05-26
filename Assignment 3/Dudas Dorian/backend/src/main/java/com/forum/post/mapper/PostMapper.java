package com.forum.post.mapper;

import com.forum.post.model.dto.PostDTO;
import com.forum.post.model.Post;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mappings({
            @Mapping(target = "topicTitle", ignore = true),
            @Mapping(target = "posterUsername", ignore = true)
    })
    PostDTO toDto(Post post);

    @AfterMapping
    default void setTopicAndUser(Post post, @MappingTarget PostDTO postDTO) {
        postDTO.setTopicTitle(post.getTopic().getTitle());
        postDTO.setPosterUsername(post.getUser().getUsername());
    }

    @Mappings({
            @Mapping(target = "topic", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    Post fromDto(PostDTO postDTO);
}
