package com.app.banking.data.dto.mapper;

import com.app.banking.data.dto.model.Offer;
import com.app.banking.data.sql.entity.Offers;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OffersMapper {

    Offer entityToDto(Offers entity);

    Offers dtoToEntity(Offer dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOffer(Offer dto, @MappingTarget Offers entity);

}
