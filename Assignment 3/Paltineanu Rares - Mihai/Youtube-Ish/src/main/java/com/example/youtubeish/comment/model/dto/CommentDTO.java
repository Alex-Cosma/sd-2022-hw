package com.example.youtubeish.comment.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String author;
    private String content;
}
