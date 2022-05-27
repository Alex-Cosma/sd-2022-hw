package com.travel.city;

import com.travel.city.model.City;
import com.travel.city.model.City.CityBuilder;
import com.travel.city.model.dto.CityDTO;
import com.travel.city.model.dto.CityDTO.CityDTOBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-27T03:34:02+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.jar, environment: Java 15 (Oracle Corporation)"
)
@Component
public class CityMapperImpl implements CityMapper {

    @Override
    public CityDTO toDto(City city) {
        if ( city == null ) {
            return null;
        }

        CityDTOBuilder cityDTO = CityDTO.builder();

        cityDTO.id( city.getId() );
        cityDTO.name( city.getName() );

        return cityDTO.build();
    }

    @Override
    public City fromDto(CityDTO cityDTO) {
        if ( cityDTO == null ) {
            return null;
        }

        CityBuilder city = City.builder();

        city.id( cityDTO.getId() );
        city.name( cityDTO.getName() );

        return city.build();
    }
}
