package com.travel.user.mapper;


import com.travel.user.dto.UserDTO;
import com.travel.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserDTO toDto(User user);

    @Mappings({
            @Mapping(target = "username", source = "userDto.username"),
            @Mapping(target = "roles", ignore = true)
    })
    User fromDto(UserDTO userDto);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }
}

