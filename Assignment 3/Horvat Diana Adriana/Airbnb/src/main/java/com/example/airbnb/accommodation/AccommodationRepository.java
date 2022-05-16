package com.example.airbnb.accommodation;

import com.example.airbnb.accommodation.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

}
