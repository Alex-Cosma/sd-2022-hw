package com.post.model.dto;

import com.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PostDto {

    private User user;
    private Long id;
    private String body;
    private Long likes;
    private Long disLikes;
    private Date created_at;
}
