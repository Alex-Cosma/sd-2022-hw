package com.rdaniel.sd.a2.backend.user.mapper;

import com.rdaniel.sd.a2.backend.user.dto.UserListDto;
import com.rdaniel.sd.a2.backend.user.dto.UserMinimalDto;
import com.rdaniel.sd.a2.backend.user.model.Role;
import com.rdaniel.sd.a2.backend.user.model.User;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

  @Mappings({
      @Mapping(target = "name", source = "user.username")
  })
  UserMinimalDto userMinimalFromUser(User user);

  @Named("populateRoles")
  default Set<String> rolesToStrings(Set<Role> roles) {
    return roles.stream()
        .map(role -> role.getName().name())
        .collect(Collectors.toSet());
  }

  @Mappings({
      @Mapping(target = "name", source = "user.username"),
      @Mapping(target = "roles", source= "user.roles", qualifiedByName = "populateRoles")
  })
  UserListDto userListDtoFromUser(User user);
}
