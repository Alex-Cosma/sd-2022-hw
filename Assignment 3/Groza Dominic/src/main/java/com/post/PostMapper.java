package com.post;

import com.post.model.Post;
import com.post.model.dto.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mappings({
            @Mapping(target = "user.roles",ignore = true),
    })
    PostDto toDto (Post post);

    @Mappings({
            @Mapping(target = "user.roles",ignore = true)
    })
    Post fromDto (PostDto postDto);
}
