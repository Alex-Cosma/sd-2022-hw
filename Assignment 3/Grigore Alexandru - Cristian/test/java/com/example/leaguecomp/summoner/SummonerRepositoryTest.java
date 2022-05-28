package com.example.leaguecomp.summoner;

import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.summoner.model.Summoner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SummonerRepositoryTest {

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private RegionRepository regionRepository;
    @Test
    void findByName() {
        Region region = regionRepository.findByRegion(ERegion.BRAZIL).orElseThrow(()-> new NoSuchElementException("region not found"));
        Summoner summoner = summonerRepository.save(
                Summoner.builder()
                        .name("HoboTarry")
                        .league("Iron")
                        .region(region)
                        .build()
        );

        assertNotNull(summoner);
        Summoner findSummoner = summonerRepository.findByName(summoner.getName()).orElseThrow(()-> new NoSuchElementException("summoner not found"));
        assertNotNull(findSummoner);
    }

    @Test
    void findAll() {
        List<Summoner> allSummoners = summonerRepository.findAll();
        assertNotNull(allSummoners);
    }

    @Test
    void findAllByRegion() {
        Region region = regionRepository.findByRegion(ERegion.EUROPE_NORTH_EAST).orElseThrow(()-> new NoSuchElementException("region not found"));
        List<Summoner> summonerWithRegion = summonerRepository.findAllByRegion(region);
        assertNotNull(summonerWithRegion);

    }

    @Test
    void findAllByName() {
        List<Summoner> summonersByName = summonerRepository.findAllByName("Zed");
        assertNotNull(summonersByName);
    }
}