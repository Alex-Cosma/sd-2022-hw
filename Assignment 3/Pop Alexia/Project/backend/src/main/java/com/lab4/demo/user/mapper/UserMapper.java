package com.lab4.demo.user.mapper;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "role",ignore = true)
    })
    UserDTO toDto(User user);

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "quizzSessions", ignore = true),
            @Mapping(target = "reviews", ignore = true)
    })
    User fromDto(UserDTO userDTO);

}
