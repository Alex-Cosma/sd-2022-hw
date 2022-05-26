package com.example.gymapplication.training;

import com.example.gymapplication.training.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByAddress(String address);
}
