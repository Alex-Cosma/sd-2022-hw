package com.app.banking.data.dto.mapper;

import com.app.banking.data.dto.model.ExpensesOnMonthDto;
import com.app.banking.data.sql.entity.ExpensesOnMonth;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ExpensesOnMonthMapper {
    ExpensesOnMonth expensesOnMonthDtoToExpensesOnMonth(ExpensesOnMonthDto expensesOnMonthDto);

    ExpensesOnMonthDto expensesOnMonthToExpensesOnMonthDto(ExpensesOnMonth expensesOnMonth);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExpensesOnMonthFromExpensesOnMonthDto(ExpensesOnMonthDto expensesOnMonthDto, @MappingTarget ExpensesOnMonth expensesOnMonth);
}
