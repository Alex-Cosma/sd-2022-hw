package com.policeSection.section.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Document("Section")
public class Section {
    @Id
    private String id;
    private int number;
    private String email;

}
