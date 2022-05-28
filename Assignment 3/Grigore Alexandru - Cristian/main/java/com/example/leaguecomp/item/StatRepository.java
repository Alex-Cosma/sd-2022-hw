package com.example.leaguecomp.item;

import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.model.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface StatRepository extends JpaRepository<Stat,Long> {
}
