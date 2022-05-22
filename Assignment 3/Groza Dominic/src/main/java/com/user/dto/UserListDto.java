package com.user.dto;
import com.group.model.Group;
import com.group.model.dto.GroupDto;
import com.post.model.Post;
import com.post.model.dto.PostDto;
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
    private String password;
    private Set<UserListDto> friends;
    private Set<UserListDto> friendOf;
    private Set<GroupDto> groups;
    private Set<PostDto> posts;
}