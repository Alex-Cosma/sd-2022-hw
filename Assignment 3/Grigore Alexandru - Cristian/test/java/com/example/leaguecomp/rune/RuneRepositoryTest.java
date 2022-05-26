package com.example.leaguecomp.rune;

import com.example.leaguecomp.rune.model.Rune;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RuneRepositoryTest {

    @Autowired
    private RuneRepository runeRepository;

    @Test
    void findAll(){
        assertEquals(runeRepository.findAll().size(),63);
    }

    @Test
    void findNameLike(){
        List<Rune> runes = runeRepository.findAllByNameLike("E%");
        Rune rune = runeRepository.findByName("Electrocute").get();
        assertTrue(runes.contains(rune));
    }
}
