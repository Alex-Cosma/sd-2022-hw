package com.policeSection.section;

import com.policeSection.fine.FineService;
import com.policeSection.section.Model.Section;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class SectionServiceTest {

    @InjectMocks
    private SectionService sectionService;
    @Mock
    private SectionRepository sectionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sectionService = new SectionService(sectionRepository);
    }
    @Test
    void create()
    {
        Section itemSaved=Section.builder().number(5)
                .email("tudorcampan@gmail.com")
                .build();
        when(sectionRepository.save(itemSaved)).thenReturn(itemSaved);
        Section createdSection=sectionService.create(itemSaved);
        Assertions.assertEquals(itemSaved,createdSection);

    }
    @Test
    void findByNumber()
    {
        Section sectionSaved=Section.builder().number(5)
                .email("tudorcampan@gmail.com")
                .build();
        when(sectionRepository.findByNumber(5)).thenReturn(sectionSaved);
        Section sectionFound=sectionService.findByNumber(5);
        Assertions.assertEquals(sectionFound,sectionSaved);


    }
}
