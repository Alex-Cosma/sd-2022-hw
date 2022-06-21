package com.app.banking.data.dto.mapper;

import com.app.banking.data.dto.model.DepositTypeDto;
import com.app.banking.data.sql.entity.DepositType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DepositTypeMapper {
    DepositType depositTypeDtoToDepositType(DepositTypeDto depositTypeDto);

    DepositTypeDto depositTypeToDepositTypeDto(DepositType depositType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDepositTypeFromDepositTypeDto(DepositTypeDto depositTypeDto, @MappingTarget DepositType depositType);
}
