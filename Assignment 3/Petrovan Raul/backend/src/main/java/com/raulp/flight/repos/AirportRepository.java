package com.raulp.flight.repos;

import com.raulp.flight.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findTopByNameLikeOrIcaoLike(String name, String code);
}
