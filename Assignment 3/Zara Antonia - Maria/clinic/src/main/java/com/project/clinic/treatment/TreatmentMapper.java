package com.project.clinic.treatment;

import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.treatment.model.dto.TreatmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import javax.persistence.ManyToOne;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {

    @Mappings({
            @Mapping(target = "category", ignore = true)
    })
    Treatment fromDto(TreatmentDTO treatment);

    @Mappings({
            @Mapping(target = "category", ignore = true)
    })
    TreatmentDTO toDto(Treatment treatment);
}
