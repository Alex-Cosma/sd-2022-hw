package com.post;

import com.post.model.Post;
import com.post.model.dto.PostDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto toDto (Post post);

    Post fromDto (PostDto postDto);
}
