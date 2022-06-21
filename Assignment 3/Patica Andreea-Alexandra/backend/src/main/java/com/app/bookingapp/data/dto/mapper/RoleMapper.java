package com.app.bookingapp.data.dto.mapper;

import com.app.bookingapp.data.dto.model.RoleDto;
import com.app.bookingapp.data.sql.entity.Role;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoleMapper {
    Role roleDtoToRole(RoleDto roleDto);

    RoleDto roleToRoleDto(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoleFromRoleDto(RoleDto roleDto, @MappingTarget Role role);
}
