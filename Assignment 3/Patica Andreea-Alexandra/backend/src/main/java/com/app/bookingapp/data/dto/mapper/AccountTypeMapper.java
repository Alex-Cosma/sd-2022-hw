package com.app.bookingapp.data.dto.mapper;

import com.app.bookingapp.data.dto.model.AccountTypeDto;
import com.app.bookingapp.data.sql.entity.AccountType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AccountTypeMapper {
    AccountType accountTypeDtoToAccountType(AccountTypeDto accountTypeDto);

    AccountTypeDto accountTypeToAccountTypeDto(AccountType accountType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAccountTypeFromAccountTypeDto(AccountTypeDto accountTypeDto, @MappingTarget AccountType accountType);
}
