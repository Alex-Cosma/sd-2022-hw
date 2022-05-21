package com.user.mapper;

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
            @Mapping(target = "roles",ignore = true),
            @Mapping(target="id",source="user.id"),
            @Mapping(target="posts",source="user.posts"),
            @Mapping(target="friends",ignore = true),


    })
    UserListDto userListDtoFromUser(User user);

    @Mappings({
            @Mapping(target = "username", source = "username"),
            @Mapping(target = "roles",ignore = true),
            @Mapping(target="id",source="id"),
            @Mapping(target = "posts",source = "posts"),
            @Mapping(target="friends",ignore = true),


    })
    User userFromUserListDto(UserListDto userListDto);


    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDto userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }

    @AfterMapping
    default void populateFriends(User user, @MappingTarget UserListDto userListDTO) {
        userListDTO.setFriends(new HashSet<>(user.getFriends()));
    }
}