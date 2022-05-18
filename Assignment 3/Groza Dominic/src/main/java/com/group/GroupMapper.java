package com.group;

import com.group.model.Group;
import com.group.model.dto.GroupDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto toDto (Group group);

    Group fromDto (GroupDto groupDto);
}
