package com.group.model.dto;

import com.user.dto.UserListDto;
import com.user.model.User;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GroupDto {
    private Long id;
    private String name;
    private Set<UserListDto> users;
}
