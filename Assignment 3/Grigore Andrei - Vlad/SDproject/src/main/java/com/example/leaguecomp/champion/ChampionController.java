package com.example.leaguecomp.champion;

import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.summoner.dto.SummonerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.leaguecomp.UrlMappings.CHAMPS;
import static com.example.leaguecomp.UrlMappings.ENTITY;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(CHAMPS)
public class ChampionController {

    private final ChampionService championService;
    @GetMapping
    public List<ChampionDTO> findAll(){
        return championService.findAll();
    }

    @GetMapping("/{name}")
    public ChampionDTO findAllByName(@PathVariable String name){
        return championService.findByName(name);
    }

    @PostMapping("/edit"+ENTITY)
    public ChampionDTO edit(@PathVariable Long id, @RequestBody ChampionDTO champion){
        return championService.edit(id,champion);
    }

    @GetMapping( "/buildPDF/{name}")
    public ResponseEntity<InputStreamResource> export(@PathVariable String name) throws IOException {
        ChampionDTO champion = championService.findByName(name);
        ByteArrayInputStream bais = championService.generatePDF(champion);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","inline; filename = build.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }
}
