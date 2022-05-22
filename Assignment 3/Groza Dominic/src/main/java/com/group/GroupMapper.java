package com.group;

import com.group.model.Group;
import com.group.model.dto.GroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mappings({
            @Mapping(target="users",ignore = true)
    })
    GroupDto toDto (Group group);

    @Mappings({
            @Mapping(target="users",ignore = true)
    })
    Group fromDto (GroupDto groupDto);
}
