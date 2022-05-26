package com.policeSection.Reports.Model;

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
@Document("Reports")
public class Report {
    @Id
    private String id;
    private String description;
    private int sectionNumber;

}


