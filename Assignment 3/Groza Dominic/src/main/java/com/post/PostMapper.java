package com.post;

import com.post.model.Post;
import com.post.model.dto.PostDto;
import com.user.dto.UserListDto;
import com.user.mapper.UserMapper;
import com.user.model.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;


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
