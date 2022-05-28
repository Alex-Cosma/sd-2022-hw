package com.policeSection.section;

import com.policeSection.fine.Model.Fine;
import com.policeSection.section.Model.Section;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SectionRepository extends MongoRepository<Section, String> {

    //    @Query("{number:'?0'}")
    Section findByNumber(int number);

}
