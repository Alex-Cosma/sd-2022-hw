package com.assignment2.bookstoreapp.user.mapper;
import com.assignment2.bookstoreapp.book.model.Book;
import com.assignment2.bookstoreapp.book.model.dto.BookDTO;
import com.assignment2.bookstoreapp.user.dto.UserListDTO;
import com.assignment2.bookstoreapp.user.dto.UserMinimalDTO;
import com.assignment2.bookstoreapp.user.dto.UserRegisterDTO;
import com.assignment2.bookstoreapp.user.model.ERole;
import com.assignment2.bookstoreapp.user.model.Role;
import com.assignment2.bookstoreapp.user.model.User;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
    })
    UserListDTO userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }

    User fromDto(UserRegisterDTO user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
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