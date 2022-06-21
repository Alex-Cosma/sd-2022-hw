package com.app.banking.data.dto.mapper;

import com.app.banking.data.dto.model.CardTypeDto;
import com.app.banking.data.sql.entity.CardType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CardTypeMapper {
    CardType cardTypeDtoToCardType(CardTypeDto cardTypeDto);

    CardTypeDto cardTypeToCardTypeDto(CardType cardType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCardTypeFromCardTypeDto(CardTypeDto cardTypeDto, @MappingTarget CardType cardType);
}
