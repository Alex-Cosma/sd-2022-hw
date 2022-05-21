package com.raulp.user.mapper;

import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.dto.user.UserListDTO;
import com.raulp.user.dto.user.UserMinimalDTO;
import com.raulp.user.model.Student;
import com.raulp.user.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mappings({
            @Mapping(target = "name", source = "student.username")
    })
    StudentMinimalDTO studentMinimalFromStudent(Student student);

//    @Mappings({
//            @Mapping(target = "name", source = "user.username"),
//            @Mapping(target = "roles", ignore = true)
//    })
//    UserListDTO userListDtoFromUser(User user);
//
//    @AfterMapping
//    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
//        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(
//                Collectors.toSet()));
//    }
}
