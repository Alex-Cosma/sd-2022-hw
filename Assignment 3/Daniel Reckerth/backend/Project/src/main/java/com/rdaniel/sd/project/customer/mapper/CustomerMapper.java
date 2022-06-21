package com.rdaniel.sd.project.customer.mapper;

import com.rdaniel.sd.project.customer.dto.CustomerDto;
import com.rdaniel.sd.project.customer.model.Customer;
import com.rdaniel.sd.project.user.model.Role;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

  @Named("populateRoles")
  default Set<String> rolesToStrings(Set<Role> roles) {
    return roles.stream()
        .map(role -> role.getName().name())
        .collect(Collectors.toSet());
  }

  @Mappings({
      @Mapping(target = "roles", source = "customer.roles", qualifiedByName = "populateRoles")
  })
  CustomerDto toDto(Customer customer);

}
