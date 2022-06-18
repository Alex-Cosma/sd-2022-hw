package com.app.bookingapp.data.sql.repo;

import com.app.bookingapp.data.sql.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findAllByOwnerId(Long id);

    List<Property> findAllByOwnerUsername(String username);

    Optional<Property> findPropertyByName(String name);
}
