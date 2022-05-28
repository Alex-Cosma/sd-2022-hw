package com.policeSection.fine;

import com.policeSection.fine.Model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FineRepository extends JpaRepository<Fine, Long> {
    Fine findFirstByTypeEquals(String type);

}
