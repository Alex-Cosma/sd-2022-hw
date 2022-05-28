package com.example.leaguecomp.champion;

import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.mapper.ChampionMapper;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.ItemRepository;
import com.example.leaguecomp.item.dto.ItemListDTO;
import com.example.leaguecomp.item.model.Item;
import com.example.leaguecomp.rune.RuneRepository;
import com.example.leaguecomp.rune.model.Rune;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChampionService {

    private final ChampionRepository championRepository;
    private final RuneRepository runeRepository;
    private final ItemRepository itemRepository;
    private final ChampionMapper championMapper;
    private final ReportPdfService reportPdfService;

    public ChampionDTO findByName(String name){
        Champion champion =  championRepository.findByName(name).orElseThrow(()-> new RuntimeException("Champ not existing    "));
        //System.out.println(champion.getBuild().toString());
        ChampionDTO champDto = championMapper.toDto(champion);
        championMapper.populateBuild(champion,champDto);
        championMapper.populateRunes(champion,champDto);
        return champDto;
    }

    public Champion findById(Long id){
        return championRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("champion not found for id: "+id));
    }

    public List<ChampionDTO> findAll(){
        List<ChampionDTO> championDTOS = new ArrayList<>();
        for(Champion champion: championRepository.findAll()){
            ChampionDTO championDTO = championMapper.toDto(champion);
            championMapper.populateRunes(findById(champion.getId()), championDTO);
            championMapper.populateBuild(findById(champion.getId()), championDTO);
            championDTOS.add(championDTO);

        }

        return championDTOS;
    }

    public ByteArrayInputStream generatePDF(ChampionDTO champion) throws IOException {
        Champion championAct = championRepository.findById(champion.getId()).get();
        return reportPdfService.export(championAct);
    }
    public ChampionDTO edit(Long id, ChampionDTO champion){
        System.out.println(id);
        Champion championAct = championRepository.findById(id).get();
        Set<String> runesAct = champion.getRunes();
        List<String> itemsAct = champion.getBuild();
        Set<Rune> runes = new HashSet<>();
        List<Item> items = new ArrayList<>();
        if(runesAct != null) {
            runesAct.forEach(r -> {
                Rune rune = runeRepository.findByName(r)
                        .orElseThrow(() -> new RuntimeException("no rune"));
                runes.add(rune);
            });
        }
        if(itemsAct != null) {
            itemsAct.forEach(item -> {
                Item item1 = itemRepository.findByName(item)
                        .orElseThrow(() -> new RuntimeException("no item"));
                items.add(item1);
            });
        }

        championAct.setBuild(items);
        championAct.setRunes(runes);
        championAct.setCrowdControl(champion.getCrowdControl());
        championAct.setDamage(champion.getDamage());
        championAct.setDescription(champion.getDescription());
        championAct.setImage(champion.getImage());
        championAct.setMobility(champion.getMobility());
        championAct.setToughness(champion.getToughness());
        championAct.setUtility(champion.getUtility());
        championAct.setName(champion.getName());
        return championMapper.toDto(championRepository.save(championAct));
    }
}
