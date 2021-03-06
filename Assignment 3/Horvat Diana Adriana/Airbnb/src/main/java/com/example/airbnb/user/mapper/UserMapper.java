package com.example.airbnb.user.mapper;

import com.example.airbnb.user.dto.UserListDTO;
import com.example.airbnb.user.dto.UserMinimalDTO;
import com.example.airbnb.user.model.User;
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

    @AfterMapping
    default void populateAccommodations(User user, @MappingTarget UserListDTO userListDTO){
        userListDTO.setAccommodations(user.getAccommodations());
    }

    @Mappings({
            @Mapping(target = "username", source = "name"),
            @Mapping(target = "roles", ignore = true)
    })
    User fromDto(UserListDTO userListDTO);
}
