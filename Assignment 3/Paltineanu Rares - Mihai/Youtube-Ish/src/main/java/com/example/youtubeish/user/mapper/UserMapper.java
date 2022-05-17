package com.example.youtubeish.user.mapper;

import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.model.User;
import org.mapstruct.*;


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
}
