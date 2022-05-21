package com.example.airbnb.amenities;

import com.example.airbnb.accommodation.AccommodationService;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.address.AddressRepository;
import com.example.airbnb.address.model.Address;
import com.example.airbnb.amenities.model.Amenity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AmenityService {
//    private final AmenityRepository amenityRepository;
//
//    public Set<Amenity> createSetOfAmenities(String amenities, Accommodation accommodation){
//        String[] amenitiesString = amenities.split(",");
//        Set<Amenity> amenitySet = new HashSet<>();
//
//        for (String amenity : amenitiesString){
//            Amenity amenityObj = new Amenity();
//            amenityObj.setAmenity(amenity);
//            amenityObj.setAccommodation(accommodation);
//            amenitySet.add(amenityObj);
//        }
//
//        return amenitySet;
//    }
//
//    public void setAccommodationToAmenities(Set<Amenity> amenities, Accommodation accommodation){
//        for (Amenity amenity : amenities){
//            amenity.setAccommodation(accommodation);
//            amenityRepository.save(amenity);
//        }
//    }
}
