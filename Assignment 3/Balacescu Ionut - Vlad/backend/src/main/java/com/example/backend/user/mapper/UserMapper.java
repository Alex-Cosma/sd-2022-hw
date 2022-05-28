package com.example.backend.user.mapper;


import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.model.User;
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
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    User fromDto(UserDTO user);


    @AfterMapping
    default void populateRolesDTO(User user, @MappingTarget UserDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }
}
