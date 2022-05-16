package com.example.airbnb.accommodation.model.dto;

import com.example.airbnb.amenities.model.Amenity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Decimal128;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDTO {
    private String id;
    private String name;
    private String description;
    private String house_rules;
    private String property_type;
    private String room_type;
    private String accommodates;
    private String bathrooms;
    private String bedrooms;
    private String beds;
    private Set<Amenity> amenities;
//    private Decimal128 price;
//    private Object images;
//    private Object address;
//    private List<Object> reviews;
}
