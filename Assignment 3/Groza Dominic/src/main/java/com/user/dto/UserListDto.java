package com.user.dto;
import com.group.model.Group;
import com.post.model.Post;
import com.user.model.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserListDto extends UserMinimalDto {
    private String email;
    private Set<String> roles;
    private String password;
    private Set<User> friends;
    private Set<Group> groups;
    private Set<Post> posts;
}