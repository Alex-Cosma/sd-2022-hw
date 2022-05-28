package com.policeSection.driver;

import com.policeSection.driver.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Driver findFirstByNameEquals(String name);
}
