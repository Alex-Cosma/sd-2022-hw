package com.app.banking.data.dto.mapper;

import com.app.banking.data.dto.model.CardStatusDto;
import com.app.banking.data.sql.entity.CardStatus;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CardStatusMapper {
    CardStatus cardStatusDtoToCardStatus(CardStatusDto cardStatusDto);

    CardStatusDto cardStatusToCardStatusDto(CardStatus cardStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCardStatusFromCardStatusDto(CardStatusDto cardStatusDto, @MappingTarget CardStatus cardStatus);
}
