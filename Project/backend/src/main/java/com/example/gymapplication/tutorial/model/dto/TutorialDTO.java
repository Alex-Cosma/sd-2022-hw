package com.example.gymapplication.tutorial.model.dto;

import lombok.*;
import org.bson.types.Binary;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
@Getter
@Setter
public class TutorialDTO {
    private String id;

    private String title;

    private Binary image;
}
