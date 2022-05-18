package com.example.airbnb.amenities;

import com.example.airbnb.amenities.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
