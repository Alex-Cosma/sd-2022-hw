package com.example.leaguecomp.champion;

import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.model.Champion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChampionRepository extends JpaRepository<Champion,Long> {

    Optional<Champion> findByName(String name);

    List<Champion> findAll();

    /*@Override
    Page<Champion> findAll(Pageable pageable);*/
}
