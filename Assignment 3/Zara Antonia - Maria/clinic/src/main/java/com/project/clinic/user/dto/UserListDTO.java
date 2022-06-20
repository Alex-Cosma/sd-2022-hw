package com.project.clinic.user.dto;
import com.project.clinic.treatment.model.Treatment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder=true)
@AllArgsConstructor
public class UserListDTO extends UserMinimalDTO {
    private Set<String> roles;
    private String skinColor;
    private Set<Treatment> treatments;
}