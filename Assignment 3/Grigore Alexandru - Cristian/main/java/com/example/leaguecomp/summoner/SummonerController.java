package com.example.leaguecomp.summoner;

import com.example.leaguecomp.summoner.dto.SummonerDTO;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.leaguecomp.UrlMappings.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(SUMMONERS)
@CrossOrigin
public class SummonerController {
    private final SummonerService summonerService;
    private final RegionRepository regionRepository;

    @GetMapping
    public List<SummonerDTO> findAll(){
        return summonerService.findAll();
    }

    @GetMapping(FILTER_NAME)
    public SummonerDTO findByName(@PathVariable String name){
        return summonerService.findByName(name);
    }

    @PostMapping("/create")
    public SummonerDTO create(SummonerDTO summoner){
        return summonerService.create(summoner);
    }

    @PostMapping("/edit"+ENTITY)
    public SummonerDTO edit(@PathVariable Long id, @RequestBody SummonerDTO summoner){
        return summonerService.edit(id,summoner);
    }

    @GetMapping("/mastery/{name}")
    public List<String> summonerMastery(@PathVariable String name){
        return summonerService.getMastery(name);
    }

}
