package com.example.airbnb.amenities.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
