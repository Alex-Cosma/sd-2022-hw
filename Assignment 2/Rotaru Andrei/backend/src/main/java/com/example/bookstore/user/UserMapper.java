package com.example.bookstore.user;

import com.example.bookstore.user.model.User;
import com.example.bookstore.user.model.dto.UserDTO;
import com.example.bookstore.user.model.dto.UserListDTO;
import com.example.bookstore.user.model.dto.UserMinimalDTO;
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

    @Mappings({

            @Mapping(target = "role", ignore = true)
    })
    UserDTO toDto(User user);

    @Mappings({
            @Mapping(target = "roles", ignore = true)
    })
    User fromDto(UserDTO userDTO);


    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }
}
