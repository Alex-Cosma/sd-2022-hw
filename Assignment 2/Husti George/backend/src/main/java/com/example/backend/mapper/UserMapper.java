package com.example.backend.mapper;

import com.example.backend.dto.user.UserCreationDto;
import com.example.backend.dto.user.UserGetDto;
import com.example.backend.dto.user.UserUpdateDto;
import com.example.backend.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userCreationDtoToUser(UserCreationDto userCreationDto);

    UserGetDto userToUserGetDto(User user);

    User userUpdateDtoToUser(UserUpdateDto userUpdateDto);
}
