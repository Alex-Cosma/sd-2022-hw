package com.app.banking.data.dto.mapper;

import com.app.banking.data.dto.model.InterbankingDetailDto;
import com.app.banking.data.sql.entity.InterbankingDetail;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface InterbankingDetailMapper {
    InterbankingDetail interbankingDetailDtoToInterbankingDetail(InterbankingDetailDto interbankingDetailDto);

    InterbankingDetailDto interbankingDetailToInterbankingDetailDto(InterbankingDetail interbankingDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInterbankingDetailFromInterbankingDetailDto(InterbankingDetailDto interbankingDetailDto, @MappingTarget InterbankingDetail interbankingDetail);
}
