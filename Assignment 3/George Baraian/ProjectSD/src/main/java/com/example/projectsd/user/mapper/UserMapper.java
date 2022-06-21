package com.example.projectsd.user.mapper;

import com.example.projectsd.user.dto.UserDTO;
import com.example.projectsd.user.dto.UserListDTO;
import com.example.projectsd.user.dto.UserMinimalDTO;
import com.example.projectsd.user.model.User;
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

    UserDTO toDto(User user);

    User fromDto(UserDTO user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }

}
