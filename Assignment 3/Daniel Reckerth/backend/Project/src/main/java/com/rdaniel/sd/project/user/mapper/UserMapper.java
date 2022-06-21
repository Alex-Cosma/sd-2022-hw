package com.rdaniel.sd.project.user.mapper;

import com.rdaniel.sd.project.user.dto.UserDto;
import com.rdaniel.sd.project.user.model.Role;
import com.rdaniel.sd.project.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Named("populateRoles")
  default Set<String> rolesToStrings(Set<Role> roles) {
    return roles.stream()
        .map(role -> role.getName().name())
        .collect(Collectors.toSet());
  }

  @Mappings({
      @Mapping(target = "roles", source= "roles", qualifiedByName = "populateRoles")
  })
  UserDto userToUserDto(User user);
}
