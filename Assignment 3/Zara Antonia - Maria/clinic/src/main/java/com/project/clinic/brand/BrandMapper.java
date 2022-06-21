package com.project.clinic.brand;

import com.project.clinic.brand.model.Brand;
import com.project.clinic.brand.model.dto.BrandDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mappings({
            @Mapping(target = "products", ignore = true),
    })
    BrandDTO toDto(Brand brand);

    @Mappings({
            @Mapping(target = "products", ignore = true),
    })
    Brand fromDto(BrandDTO brand);
}
