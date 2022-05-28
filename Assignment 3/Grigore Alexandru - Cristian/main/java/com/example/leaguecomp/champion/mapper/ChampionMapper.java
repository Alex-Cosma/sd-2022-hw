package com.example.leaguecomp.champion.mapper;

import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.model.Champion;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ChampionMapper {

    @Mappings({
            @Mapping(target = "name", source = "champion.name"),
            @Mapping(target = "runes", ignore = true),
            @Mapping(target = "build", ignore = true)
    })
    ChampionDTO toDto(Champion champion);

    //Champion fromDto(ChampionDTO championDTO);

    @AfterMapping
    default void populateRunes(Champion champion, @MappingTarget ChampionDTO championDTO){
        championDTO.setRunes(champion.getRunes().stream().map(rune -> rune.getName()).collect(Collectors.toSet()));
    }

    @AfterMapping
    default void populateBuild(Champion champion, @MappingTarget ChampionDTO championDTO){
        championDTO.setBuild(champion.getBuild().stream().map(item -> item.getName()).collect(Collectors.toList()));
    }


}
