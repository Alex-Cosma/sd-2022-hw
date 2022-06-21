package com.app.banking.data.dto.mapper;

import com.app.banking.data.dto.model.BusinessDto;
import com.app.banking.data.sql.entity.Business;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BusinessMapper {
    Business businessDtoToBusiness(BusinessDto businessDto);

    BusinessDto businessToBusinessDto(Business business);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBusinessFromBusinessDto(BusinessDto businessDto, @MappingTarget Business business);
}
