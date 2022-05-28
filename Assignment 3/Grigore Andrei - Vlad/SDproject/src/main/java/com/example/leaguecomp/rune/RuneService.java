package com.example.leaguecomp.rune;

import com.example.leaguecomp.rune.dto.RuneDTO;
import com.example.leaguecomp.rune.mapper.RuneMapper;
import com.example.leaguecomp.rune.model.Rune;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RuneService {

    private final RuneRepository runeRepository;
    private final RuneMapper runeMapper;

    public List<RuneDTO> findAll(){
        return runeRepository.findAll()
                .stream().map(runeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<RuneDTO> findAllByNameLike(String name){
        return runeRepository.findAllByNameLike(name)
                .stream().map(runeMapper::toDto)
                .collect(Collectors.toList());
    }

}
