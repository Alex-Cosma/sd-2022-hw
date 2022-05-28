package com.example.leaguecomp.rune;

import com.example.leaguecomp.rune.model.Rune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RuneRepository extends JpaRepository<Rune, Long> {

    List<Rune> findAll();

    List<Rune> findAllByNameLike(String name);

    Optional<Rune> findByName(String name);
}
