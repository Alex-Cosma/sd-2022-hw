package com.example.leaguecomp.Champion;

import com.example.leaguecomp.champion.ChampionRepository;
import com.example.leaguecomp.champion.ChampionService;
import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.mapper.ChampionMapper;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.ItemRepository;
import com.example.leaguecomp.item.Mapper.ItemMapper;
import com.example.leaguecomp.rune.RuneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChampionServiceTest {

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private ChampionService championService;

    @Autowired
    private RuneRepository runeRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ChampionMapper championMapper;

    @Test
    void findAll(){
        List<ChampionDTO> championList = championService.findAll();
        System.out.println(championList.get(0).getName());
        assertEquals(159,championList.size());
    }


    @Test
    void edit(){
        ChampionDTO champion = championService.findByName("Aatrox");
        championMapper.populateBuild(championRepository.findByName("Aatrox").get(),champion);
        int size1 = champion.getBuild().size();
        championService.edit(1L,champion);
        Champion champion1 = championService.findById(1L);
        int size2 = champion1.getBuild().size();
        assertEquals(size1+1,7);
    }

    @Test
    void findById(){
        Champion champion = championService.findById(3L);
        ChampionDTO championDTO = championService.findByName(champion.getName());

        assertEquals(champion.getId(),championDTO.getId());
    }


}
