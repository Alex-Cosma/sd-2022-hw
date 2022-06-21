package com.app.banking.data.dto.mapper;

import com.app.banking.data.dto.model.ExpensDto;
import com.app.banking.data.sql.entity.Expens;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ExpensMapper {
    Expens expensDtoToExpens(ExpensDto expensDto);

    ExpensDto expensToExpensDto(Expens expens);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExpensFromExpensDto(ExpensDto expensDto, @MappingTarget Expens expens);
}
