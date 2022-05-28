package com.policeSection.fine;

import com.policeSection.fine.Model.Fine;
import com.policeSection.fine.Model.dto.FineDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FineServiceIntegrationTest {

    @Autowired
    private FineService fineService;
    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private FineMapper fineMapper;

    @BeforeEach
    void setUp() {
        fineRepository.deleteAll();
    }

    @Test
    void findAll() {

            Fine fine1=Fine.builder()
                    .id(1L)
                    .type("random")
                    .description("random")
                    .price(200)
                    .build();
            Fine fine2=Fine.builder()
                    .id(2L)
                    .type("random")
                    .description("random")
                    .price(200)
                    .build();
            List<Fine> list =new ArrayList<>();
            list.add(fine1);
            list.add(fine2);
            fineRepository.save(fine1);
            fineRepository.save(fine2);
            List<FineDTO> allFines= fineService.findAll();
            assertEquals(list.size(),allFines.size());

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

        FineDTO createdFine= fineService.create(fine);
        Assertions.assertEquals(createdFine,fine);
    }
    @Test
    void edit()
    {

        FineDTO fine=FineDTO.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();

        fine=fineService.create(fine);
        fine.setType("random test");
        fineService.edit(fine.getId(),fine);
        Assertions.assertNotNull(fineRepository.findFirstByTypeEquals("random test"));
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
        fineRepository.save(fine);

        Fine fineFound=fineService.findByType("random");
        assertEquals(fineFound.getDescription(),fine.getDescription());
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
        fine=fineRepository.save(fine);
        fineService.delete(fine.getId());
        Assertions.assertFalse(fineRepository.existsById(fine.getId()));
    }
}
