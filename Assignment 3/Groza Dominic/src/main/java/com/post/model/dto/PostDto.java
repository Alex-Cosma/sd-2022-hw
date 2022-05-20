package com.post.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.user.dto.UserListDto;
import com.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private UserListDto user;
    private Long id;
    private String body;
    private Long likes;
    private Long disLikes;
    private Date created_at;
}