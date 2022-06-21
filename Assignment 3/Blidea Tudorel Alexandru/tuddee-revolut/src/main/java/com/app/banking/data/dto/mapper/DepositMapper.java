package com.app.banking.data.dto.mapper;

import com.app.banking.data.dto.model.DepositDto;
import com.app.banking.data.sql.entity.Deposit;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DepositMapper {
    Deposit depositDtoToDeposit(DepositDto depositDto);

    DepositDto depositToDepositDto(Deposit deposit);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDepositFromDepositDto(DepositDto depositDto, @MappingTarget Deposit deposit);
}
