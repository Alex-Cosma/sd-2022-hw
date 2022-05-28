package com.policeSection.section;

import com.policeSection.fine.FineRepository;
import com.policeSection.fine.FineService;
import com.policeSection.section.Model.Section;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class SectionServiceIntegrationTest {

    @Autowired
    private SectionService sectionService;
    @Autowired
    private SectionRepository sectionRepository;

    @BeforeEach
    void setUp() {
        sectionRepository.deleteAll();
    }

    @Test
    void create()
    {
        Section itemSaved=Section.builder().number(5)
                .email("tudorcampan@gmail.com")
                .build();
        Section createdSection=sectionService.create(itemSaved);
        Assertions.assertEquals(itemSaved,createdSection);

    }
    @Test
    void findByNumber()
    {
        Section sectionSaved=Section.builder().number(5)
                .email("tudorcampan@gmail.com")
                .build();
        sectionService.create(sectionSaved);
        Section sectionFound=sectionService.findByNumber(5);
        Assertions.assertEquals(sectionFound,sectionSaved);


    }

}
