package com.example.leaguecomp.summoner;

import com.example.leaguecomp.summoner.dto.SummonerDTO;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.summoner.model.Summoner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SummonerServiceTest {

    @Autowired
    private SummonerService service;
    @Autowired
    private RegionRepository regionRepository;

    @Test
    void findAll() {
        List<SummonerDTO> summoners = service.findAll();
        assertNotNull(summoners);
    }

    @Test
    void findAllByName() {
        List<SummonerDTO> summoners = service.findAllByName("Zed");
        assertNotNull(summoners);
    }

    @Test
    void findByName(){
        SummonerDTO summoner = service.findByName("Zed");
        assertNotNull(summoner);
    }

    @Test
    void create() {
        Region region = regionRepository.findByRegion(ERegion.EUROPE_NORTH_EAST).orElseThrow(()-> new NoSuchElementException("region not found"));
        SummonerDTO summoner = SummonerDTO.builder()
                .name("Tarry")
                .region(region)
                .league("IRON")
                .build();
        assertNotNull(service.create(summoner));

    }

    @Test
    void findById() {
        SummonerDTO summoner = service.findByName("Tarry");
        Summoner summ = service.findById(summoner.getId());
        assertNotNull(summ);
    }

    @Test
    void edit() {
        SummonerDTO summoner = service.findByName("Tarry");
        SummonerDTO newTarry = SummonerDTO.builder()
                .name("Jerry")
                .league("BRONZE")
                .build();
        assertNotNull(service.edit(summoner.getId(),newTarry));
    }


    @Test
    void getMastery(){
        SummonerDTO summoner = service.findByName("Zed");
        List<String> mastery = service.getMastery(summoner.getName());
        assertNotNull(mastery);
    }

}