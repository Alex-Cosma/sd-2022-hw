package com.user.mapper;

import com.post.PostMapper;
import com.user.dto.UserListDto;
import com.user.dto.UserMinimalDto;
import com.user.model.User;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target="id",source="user.id"),
            @Mapping(target="posts",ignore = true),
            @Mapping(target="friends",ignore = true),
            @Mapping(target="groups",ignore = true),



    })
    UserListDto userListDtoFromUser(User user);

    @Mappings({
            @Mapping(target = "username", source = "username"),
            @Mapping(target="id",source="id"),
            @Mapping(target = "posts",ignore = true),
            @Mapping(target="friends",ignore = true),
            @Mapping(target="groups",ignore = true),


    })
    User userFromUserListDto(UserListDto userListDto);




    @AfterMapping
    default void populateFriends(User user, @MappingTarget UserListDto userListDTO) {
       userListDTO.setFriends(user.getFriends().stream().map(this::userListDtoFromUser).collect(Collectors.toSet()));
    }

}