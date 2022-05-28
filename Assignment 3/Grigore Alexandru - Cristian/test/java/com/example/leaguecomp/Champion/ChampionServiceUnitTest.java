package com.example.leaguecomp.Champion;

import com.example.leaguecomp.TestCreationFactory;
import com.example.leaguecomp.champion.ChampionRepository;
import com.example.leaguecomp.champion.ChampionService;
import com.example.leaguecomp.champion.ReportPdfService;
import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.mapper.ChampionMapper;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.ItemRepository;
import com.example.leaguecomp.item.model.Item;
import com.example.leaguecomp.rune.RuneRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.example.leaguecomp.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChampionServiceUnitTest {

    @InjectMocks
    private ChampionService championService;

    @Mock
    private ChampionRepository championRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private RuneRepository runeRepository;

    @Mock
    private ChampionMapper championMapper;

    @Mock
    private ReportPdfService reportPdfService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        championService = new ChampionService(championRepository, runeRepository, itemRepository, championMapper,reportPdfService);
    }

    @Test
    void findAll(){
        List<Champion> championList = TestCreationFactory.listOf(Champion.class);
        //List<ChampionDTO> championDTOS = TestCreationFactory.listOf(ChampionDTO.class);
        when(championRepository.findAll()).thenReturn(championList);
        for(Champion champion : championList){
            when(championRepository.findById(champion.getId())).thenReturn(Optional.of(champion));
        }
        List<ChampionDTO> championDTOS = championService.findAll();
        assertEquals(championList.size(),championDTOS.size());
    }

    @Test
    void edit() throws Exception{
        long id = randomLong();
        Champion champion = new Champion();
        when(championRepository.findById(id)).thenReturn(Optional.of(champion));
        String name = randomString();
        ChampionDTO championDTO = ChampionDTO.builder()
                .utility(1)
                .toughness(1)
                .mobility(1)
                .image(randomBytes())
                .description(randomString())
                .damage(1)
                .id(id)
                .name(name)
                .build();
        when(championService.edit(id,championDTO)).thenReturn(championDTO);
        championService.edit(id,championDTO);
        assertEquals(champion.getName(),name);
    }

    @Test
    void findByName(){
        Champion champion = newChampion();
        ChampionDTO champion1 = newChampionDTO();
        String name = randomString();
        when(championRepository.findByName(name)).thenReturn(Optional.ofNullable(champion));
        doNothing().when(championMapper).populateRunes(champion,champion1);
        when(championMapper.toDto(champion)).thenReturn(champion1);
        ChampionDTO champion2 = championService.findByName(name);
        assertEquals(champion2.getName(),champion1.getName());
    }

    @Test
    void findById(){
        long id = randomLong();
        Champion champion = newChampion();
        when(championRepository.findById(id)).thenReturn(Optional.ofNullable(champion));

        Champion champion1 = championService.findById(id);
        assertEquals(champion1.getName(),champion.getName());
    }



}
