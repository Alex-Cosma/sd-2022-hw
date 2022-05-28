package com.forum.thread.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    private Long id;
    private String categoryName;
    private String originalPoster;
    private String title;
    private String content;
    private Date creationDate;
}
