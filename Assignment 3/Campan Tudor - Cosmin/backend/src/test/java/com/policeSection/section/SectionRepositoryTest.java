package com.policeSection.section;

import com.policeSection.section.Model.Section;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SectionRepositoryTest {

    @Autowired
    private SectionRepository repository;
    @Test
    public void testFindByNumber() {
        Section itemSaved = repository.save(Section.builder().number(4)
                .email("tudorcampan@gmail.com")
                .build());
        repository.save(itemSaved);
        assertEquals(repository.findByNumber(4).getEmail(),"tudorcampan@gmail.com");
        repository.delete(itemSaved);
    }

}
