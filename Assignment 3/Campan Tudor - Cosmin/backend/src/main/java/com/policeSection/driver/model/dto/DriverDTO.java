package com.policeSection.driver.model.dto;

import com.policeSection.fine.Model.Fine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {

    private Long id;

    private String name;

    private String email;

    private List<Fine> fines = new ArrayList<>();

    private int sectionNumber;
}
