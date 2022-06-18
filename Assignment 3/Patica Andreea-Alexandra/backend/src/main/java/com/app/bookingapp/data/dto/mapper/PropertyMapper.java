package com.app.bookingapp.data.dto.mapper;

import com.app.bookingapp.data.dto.model.PropertyDto;
import com.app.bookingapp.data.sql.entity.Property;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PropertyMapper {
    Property propertyDtoToProperty(PropertyDto propertyDto);

    PropertyDto propertyToPropertyDto(Property property);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePropertyFromPropertyDto(PropertyDto propertyDto, @MappingTarget Property property);
}
