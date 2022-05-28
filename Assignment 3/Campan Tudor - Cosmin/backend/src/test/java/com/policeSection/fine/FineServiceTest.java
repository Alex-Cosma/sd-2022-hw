package com.policeSection.fine;

import com.policeSection.driver.DriverMapper;
import com.policeSection.driver.DriverRepository;
import com.policeSection.driver.DriverService;
import com.policeSection.driver.model.Driver;
import com.policeSection.driver.model.dto.DriverDTO;
import com.policeSection.fine.Model.Fine;
import com.policeSection.fine.Model.dto.FineDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FineServiceTest {
    @InjectMocks
    private FineService fineService;
    @Mock
    private FineRepository fineRepository;
    @Mock
    private FineMapper fineMapper;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fineService = new FineService(fineRepository,fineMapper);
    }

    @Test
    void create()
    {
        FineDTO fine=FineDTO.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();
        when(fineMapper.toDto(fineRepository.save(fineMapper.fromDto(fine)))).thenReturn(fine);
        FineDTO createdFine= fineService.create(fine);
        Assertions.assertEquals(createdFine,fine);
    }
    @Test
    void edit()
    {
        Fine fine=Fine.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();
        FineDTO fineDTO=FineDTO.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();
        when(fineRepository.findById(1L)).thenReturn(Optional.ofNullable(fine)
        );
        when(fineMapper.toDto(fineRepository.save(fineMapper.fromDto(fineDTO)))).thenReturn(fineDTO);
        FineDTO createdFine=fineService.create(fineDTO);
        createdFine.setType("random test");
        FineDTO editedFine= fineService.edit(createdFine.getId(),createdFine);
        Assertions.assertEquals("random test",editedFine.getType());
    }
    @Test
    void findAll()
    {
        Fine fine1=Fine.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();
        Fine fine2=Fine.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();
        List<Fine> list =new ArrayList<>();
        list.add(fine1);
        list.add(fine2);
        when(fineRepository.findAll()).thenReturn(list);
        List<FineDTO> allFines= fineService.findAll();
        assertEquals(list.size(),allFines.size());
    }
    @Test
    void findByType()
    {
        Fine fine=Fine.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();
       // when(fineMapper.toDto(fineRepository.save(fineMapper.fromDto(fine)))).thenReturn(fine);
        when(fineRepository.findFirstByTypeEquals("random")).thenReturn(fine);

        Fine fineFound=fineService.findByType("random");
        assertEquals(fineFound,fine);
    }
    @Test
    void delete()
    {
        Fine fine=Fine.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();
        when(fineRepository.save(fine)).thenReturn(fine);
        when(fineRepository.findById(1L)).thenReturn(Optional.of(fine));
        when(fineRepository.existsById(1L)).thenReturn(false);
        fineService.delete(1L);
        Assertions.assertFalse(fineRepository.existsById(1L));
    }

}
