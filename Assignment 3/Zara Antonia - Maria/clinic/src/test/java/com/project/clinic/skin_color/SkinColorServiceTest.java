package com.project.clinic.skin_color;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.treatment.TreatmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class SkinColorServiceTest {

    @InjectMocks
    private SkinColorService skinColorService;

    @Mock
    private SkinColorRepository skinColorRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        skinColorService = new SkinColorService(skinColorRepository);
    }

    @Test
    void findAll(){
        List<SkinColor> skinColors = TestCreationFactory.listOf(SkinColor.class);

        when(skinColorRepository.findAll()).thenReturn(skinColors);

        List<SkinColor> all = skinColorService.findAll();

        Assertions.assertEquals(skinColors.size(), all.size());
    }

    @Test
    void findById(){
        SkinColor skinColor = TestCreationFactory.newSkinColor();

        when(skinColorRepository.findById(skinColor.getId())).thenReturn(java.util.Optional.of(skinColor));

        SkinColor found = skinColorService.findById(skinColor.getId());

        Assertions.assertEquals(skinColor.getId(), found.getId());
    }

    @Test
    void findByName(){
        SkinColor skinColor = TestCreationFactory.newSkinColor();

        when(skinColorRepository.findByName(skinColor.getName())).thenReturn(skinColor);

        SkinColor found = skinColorService.findByName(skinColor.getName());

        Assertions.assertEquals(skinColor.getId(), found.getId());
    }
}
