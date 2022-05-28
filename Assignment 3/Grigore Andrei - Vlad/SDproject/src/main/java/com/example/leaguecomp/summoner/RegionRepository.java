package com.example.leaguecomp.summoner;

import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region,Integer> {

    Optional<Region> findByRegion(ERegion region);
}
