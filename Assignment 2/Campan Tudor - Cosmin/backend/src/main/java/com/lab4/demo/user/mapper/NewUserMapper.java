package com.lab4.demo.user.mapper;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface NewUserMapper {
    UserDTO toDto(User user);
    User fromDto(UserDTO user);
}
