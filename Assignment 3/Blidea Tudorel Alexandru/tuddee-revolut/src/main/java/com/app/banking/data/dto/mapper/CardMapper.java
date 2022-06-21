package com.app.banking.data.dto.mapper;

import com.app.banking.data.dto.model.CardDto;
import com.app.banking.data.sql.entity.Card;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CardMapper {

    Card cardDtoToCard(CardDto cardDto);

    CardDto cardToCardDto(Card card);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCardFromCardDto(CardDto cardDto, @MappingTarget Card card);

}
