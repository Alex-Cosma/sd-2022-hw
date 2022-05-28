package com.policeSection.user.mapper;

import com.policeSection.user.dto.UserDTO;
import com.policeSection.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface NewUserMapper {
    UserDTO toDto(User user);
    User fromDto(UserDTO user);
}
