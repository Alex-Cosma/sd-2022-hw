package com.example.youtubeish.user.mapper;

import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.dto.UserListDTO;
import com.example.youtubeish.user.dto.UserMinimalDTO;
import com.example.youtubeish.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "username", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }

    @Mappings({
        @Mapping(target = "username", source = "username")
    })
    UserDTO toDto(User user);

    @Mappings({
            @Mapping(target = "username", source = "username")
    })
    User fromDto(UserDTO user);
}
