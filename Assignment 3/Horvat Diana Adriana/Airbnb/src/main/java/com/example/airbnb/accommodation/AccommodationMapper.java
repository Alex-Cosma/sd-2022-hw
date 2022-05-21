package com.example.airbnb.accommodation;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.address.model.Address;
import com.example.airbnb.amenities.model.Amenity;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import com.example.airbnb.image.model.ImageURL;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {

    @Mappings({
            @Mapping(target = "address_city", ignore = true),
            @Mapping(target = "address_street", ignore = true),
            @Mapping(target = "imageURL", ignore = true),
            @Mapping(target = "amenities", ignore = true),
            @Mapping(target = "user_id", ignore = true)
    })
    AccommodationDTO toDto(Accommodation accommodation);

    @Mappings({
            @Mapping(target = "address", ignore = true),
            @Mapping(target = "imageURL", ignore = true),
            @Mapping(target = "amenities", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    Accommodation fromDto(AccommodationDTO accommodationDTO);

    @AfterMapping
    default void populateAccommodationAddress(Accommodation accommodation, @MappingTarget AccommodationDTO accommodationDTO){
        Address address = new Address();
        address.setStreet(accommodationDTO.getAddress_street());
        address.setCity(accommodationDTO.getAddress_city());

        accommodation.setAddress(address);
    }

    @AfterMapping
    default void populateAccommodationAmenities(Accommodation accommodation, @MappingTarget AccommodationDTO accommodationDTO){
        String[] amenitiesString = accommodationDTO.getAmenities().split(",");
        Set<Amenity> amenitySet = new HashSet<>();

        for (String amenity : amenitiesString){
            Amenity amenityObj = new Amenity();
            amenityObj.setAmenity(amenity);
            amenityObj.setAccommodation(accommodation);
            amenitySet.add(amenityObj);
        }

        accommodation.setAmenities(amenitySet);
    }

    @AfterMapping
    default void populateAccommodationImageURL(Accommodation accommodation, @MappingTarget AccommodationDTO accommodationDTO){
        ImageURL imageURL = new ImageURL();
        imageURL.setPicture_url(accommodationDTO.getImageURL());

        accommodation.setImageURL(imageURL);
    }

    @AfterMapping
    default void populateAccommodationDTOAddress(AccommodationDTO accommodationDTO, @MappingTarget Accommodation accommodation){
        Address address = accommodation.getAddress();
        accommodationDTO.setAddress_city(address.getCity());
        accommodationDTO.setAddress_street(address.getStreet());
    }

    @AfterMapping
    default void populateAccommodationDTOAmenities(AccommodationDTO accommodationDTO, @MappingTarget Accommodation accommodation){
        Set<Amenity> amenities = accommodation.getAmenities();
        StringBuilder amenitiesStr = new StringBuilder();

        for(Amenity amenity : amenities){
            amenitiesStr.append(amenity.getAmenity()).append(", ");
        }

        accommodationDTO.setAmenities(amenitiesStr.toString());
    }

    @AfterMapping
    default void populateAccommodationDTOImageURL(AccommodationDTO accommodationDTO, @MappingTarget Accommodation accommodation){
        accommodationDTO.setImageURL(accommodation.getImageURL().getPicture_url());
    }

}
