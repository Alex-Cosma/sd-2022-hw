package com.rdaniel.sd.a2.backend.user.mapper;

import com.rdaniel.sd.a2.backend.security.dto.JwtResponse;
import com.rdaniel.sd.a2.backend.user.dto.RegularUserDto;
import com.rdaniel.sd.a2.backend.user.dto.UserListDto;
import com.rdaniel.sd.a2.backend.user.dto.UserMinimalDto;
import com.rdaniel.sd.a2.backend.user.model.Role;
import com.rdaniel.sd.a2.backend.user.model.RoleType;
import com.rdaniel.sd.a2.backend.user.model.User;
import org.mapstruct.*;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mappings({
      @Mapping(target = "name", source = "user.username")
  })
  UserMinimalDto userMinimalFromUser(User user);

  @Mappings({
      @Mapping(target = "name", source = "user.username"),
      @Mapping(target = "roles", ignore = true)
  })
  UserListDto userListDtoFromUser(User user);

  @AfterMapping
  default void populateRoles(User user, @MappingTarget UserListDto userListDTO) {
    userListDTO.setRoles(user.getRoles().stream()
        .map(role -> role.getName().name())
        .collect(Collectors.toSet()));
  }
}
