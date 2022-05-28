package com.example.leaguecomp.rune;

import com.example.leaguecomp.rune.dto.RuneDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RuneServiceTest {

    @Autowired
    private RuneService runeService;

    @Test
    void findAll(){
        assertEquals(runeService.findAll().size(),63);
    }

    @Test
    void findByNameLike(){
        List<RuneDTO> runeDTOS = runeService.findAllByNameLike("%El%");
        RuneDTO runeDTO = runeService.findAll().stream().filter(runeDTO1 -> runeDTO1.getName().equals("Electrocute")).findFirst().get();
        assertTrue(runeDTOS.contains(runeDTO));
    }

}
