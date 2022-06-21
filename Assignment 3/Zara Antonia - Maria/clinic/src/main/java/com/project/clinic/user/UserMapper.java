package com.project.clinic.user;

import com.project.clinic.user.dto.UserListDTO;
import com.project.clinic.user.dto.UserMinimalDTO;
import com.project.clinic.user.dto.UserRegisterDTO;
import com.project.clinic.user.model.ERole;
import com.project.clinic.user.model.Role;
import com.project.clinic.user.model.User;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "points", ignore = true)
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "points", ignore = true),
            @Mapping(target = "skinColor", ignore = true),
            @Mapping(target = "treatments", ignore = true)

    })
    UserListDTO userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }

    @Mappings({
            @Mapping(target = "username", source = "user.name"),
            @Mapping(target = "treatments", ignore = true),
            @Mapping(target = "points", ignore = true),
            @Mapping(target = "skinColor", ignore = true)
    })
    User fromDto(UserRegisterDTO user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "points", ignore = true),
            @Mapping(target = "skinColor", ignore = true),
            @Mapping(target = "treatments", ignore = true)
    })
    UserRegisterDTO userRegisterDtoFromUser(User user);

    default <R,S> Set<R> map(Set<S> set){
        HashSet<R> newSet = new HashSet<R>();
        for(S s : set){
            if(s instanceof String)
                newSet.add((R) Role.builder()
                        .name(ERole.valueOf(s.toString()))
                        .build());
            if(s instanceof Role)
                newSet.add((R) ((Role) s)
                        .getName()
                        .toString());
        }
        return newSet;
    }

}
