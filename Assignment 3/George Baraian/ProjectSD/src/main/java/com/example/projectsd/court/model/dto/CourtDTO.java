package com.example.projectsd.court.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourtDTO {
    private Long id;
    private String name;
    private String availability;
    private String sportType;
}
