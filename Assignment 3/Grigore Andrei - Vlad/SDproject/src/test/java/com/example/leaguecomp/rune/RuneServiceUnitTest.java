package com.example.leaguecomp.rune;


import com.example.leaguecomp.TestCreationFactory;
import com.example.leaguecomp.rune.dto.RuneDTO;
import com.example.leaguecomp.rune.mapper.RuneMapper;
import com.example.leaguecomp.rune.model.Rune;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;

public class RuneServiceUnitTest {

    @InjectMocks
    private RuneService runeService;

    @Mock
    private RuneRepository runeRepository;

    @Mock
    private RuneMapper runeMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        runeService = new RuneService(runeRepository,runeMapper);
    }

    @Test
    void findAll(){
        List<Rune> runeList = TestCreationFactory.listOf(Rune.class);
        when(runeRepository.findAll()).thenReturn(runeList);

        List<RuneDTO> runeDTOS = runeService.findAll();
        Assertions.assertEquals(runeList.size(),runeDTOS.size());
    }

    @Test
    void findAllByNameLike(){
        List<Rune> runeList = TestCreationFactory.listOf(Rune.class);
        when(runeRepository.findAllByNameLike("E%")).thenReturn(runeList);

        List<RuneDTO> runeDTOS = runeService.findAllByNameLike("E%");
        Assertions.assertEquals(runeList.size(),runeDTOS.size());

    }
}
