package com.example.gymapplication.tutorial.model;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "tutorials")
public class Tutorial {
    @Id
    private String id;

    private String title;

    private Binary image;

    public Tutorial(String title) {
        this.title = title;
    }
}
