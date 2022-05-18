package com.example.airbnb.accommodation.model.dto;

import com.example.airbnb.amenities.model.Amenity;
import com.example.airbnb.user.dto.UserListDTO;
import com.example.airbnb.user.model.User;
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
    private Long id;
    private String name;
    private String description;
    private String house_rules;
    private String property_type;
    private String room_type;
    private String accommodates;
    private String bathrooms;
    private String bedrooms;
    private String beds;
    private String amenities;
    private Double price;
    private String imageURL;
    private String address_city;
    private String address_street;
    private Long user_id;
}
