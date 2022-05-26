package com.example.leaguecomp.summoner;

import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.summoner.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SummonerRepository extends JpaRepository<Summoner, Long> {
    Optional<Summoner> findByName(String name);
    List<Summoner> findAll();
    List<Summoner> findAllByRegion(Region region);
    List<Summoner> findAllByName(String name);

}
