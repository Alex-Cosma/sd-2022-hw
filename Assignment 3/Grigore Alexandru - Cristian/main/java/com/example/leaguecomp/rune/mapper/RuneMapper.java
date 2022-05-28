package com.example.leaguecomp.rune.mapper;

import com.example.leaguecomp.rune.dto.RuneDTO;
import com.example.leaguecomp.rune.model.Rune;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RuneMapper {
    RuneDTO toDto(Rune rune);

    Rune fromDto(RuneDTO runeDTO);
}
