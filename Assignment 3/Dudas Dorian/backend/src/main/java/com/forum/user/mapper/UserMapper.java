package com.forum.user.mapper;

import com.forum.user.dto.UserListDTO;
import com.forum.user.dto.UserMinimalDTO;
import com.forum.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }

    @Mappings({
      @Mapping(target = "username", source = "userListDTO.name"),
      @Mapping(target = "roles", ignore = true)
    })
    User userFromUserListDTO(UserListDTO userListDTO);
}
