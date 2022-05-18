package com.user.dto;
import com.group.model.Group;
import com.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@ToString
public class UserListDto extends UserMinimalDto {
    private String email;
    private Set<String> roles;
    private String password;
    private Set<User> friends;
    private Set<Group> groups;
}