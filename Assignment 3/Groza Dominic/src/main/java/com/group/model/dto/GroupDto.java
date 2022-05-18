package com.group.model.dto;

import com.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private Set<User> users;
}
