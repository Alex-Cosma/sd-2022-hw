package com.example.leaguecomp.user.mapper;

import com.example.leaguecomp.user.dto.UserListDTO;
import com.example.leaguecomp.user.dto.UserMinimalDTO;
import com.example.leaguecomp.user.model.User;
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
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "summoners", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toSet()));
    }
    @AfterMapping
    default void populateSummoners(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setSummoners(user.getFollowedSummoners());
    }
}
