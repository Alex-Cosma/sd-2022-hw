package com.forum.post.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String topicTitle;
    private String posterUsername;
    private String content;
    private Date creationDate;
}
