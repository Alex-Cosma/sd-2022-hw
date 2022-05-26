package com.example.leaguecomp.rune;

import com.example.leaguecomp.rune.dto.RuneDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.leaguecomp.UrlMappings.RUNES;

@RestController
@RequiredArgsConstructor
@RequestMapping(RUNES)
@CrossOrigin
public class RuneController {
    private final RuneService runeService;

    @GetMapping
    public List<RuneDTO> allRunes(){return runeService.findAll();}
}
