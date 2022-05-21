package com.raulp.user.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StudentMinimalDTO {
    private Long id;
    private String name;
    private String firstName;
    private String lastName;
}
