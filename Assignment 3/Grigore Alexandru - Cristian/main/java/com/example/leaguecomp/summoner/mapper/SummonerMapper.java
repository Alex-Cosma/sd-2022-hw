package com.example.leaguecomp.summoner.mapper;

import com.example.leaguecomp.summoner.dto.SummonerDTO;
import com.example.leaguecomp.summoner.model.Summoner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SummonerMapper {
    SummonerDTO toDto(Summoner Summoner);
    Summoner fromDto(SummonerDTO summonerDTO);
}
