package com.travel.hotel;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mappings({
            @Mapping(target = "cityName", ignore = true)
    })
    HotelDTO toDto(Hotel hotel);

    @Mappings({
            @Mapping(target = "city", ignore = true)
    })
    Hotel fromDto(HotelDTO hotelDto);

    @AfterMapping
    default void populateCities(Hotel hotel, @MappingTarget HotelDTO hotelDTO){
        hotelDTO.setCityName(hotel.getCity().getName());
    }

}
