package com.example.leaguecomp.summoner;

import com.example.leaguecomp.api.OriannaData;
import com.example.leaguecomp.summoner.dto.SummonerDTO;
import com.example.leaguecomp.summoner.mapper.SummonerMapper;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.summoner.model.Summoner;
import com.example.leaguecomp.user.UserRepository;
import com.example.leaguecomp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummonerService {
    private final SummonerRepository summonerRepository;
    private final RegionRepository regionRepository;
    private final SummonerMapper summonerMapper;
    private final OriannaData orianna;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public List<SummonerDTO> findAll(){
        return summonerRepository.findAll().stream()
                .map(summonerMapper:: toDto)
                .collect(Collectors.toList());
    }

    public List<SummonerDTO> findAllByName(String name){
        return summonerRepository.findAllByName(name).stream()
                .map(summonerMapper :: toDto)
                .collect(Collectors.toList());
    }
    public SummonerDTO findByName(String name){
        return summonerRepository.findByName(name)
                .map(summonerMapper :: toDto).orElseThrow(()->new EntityNotFoundException("summoner not found"));
    }

    public SummonerDTO create(SummonerDTO summoner){
        return summonerMapper.toDto(summonerRepository.save(
                summonerMapper.fromDto(summoner)
        ));
    }

    public Summoner findById(Long id){
        return summonerRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("summoner not found for id: "+id));
    }

    public SummonerDTO edit(Long id, SummonerDTO summoner){
        Summoner actSummoner  = findById(id);
        actSummoner.setName(summoner.getName());
        actSummoner.setLeague(summoner.getLeague());
        actSummoner.setRegion(summoner.getRegion());
        return summonerMapper.toDto(
                summonerRepository.save(actSummoner)
        );
    }

    public List<String> getMastery(String name){
        return orianna.getMastery(name);
    }

    public String checkInGame(String username){
        User user = userRepository.findByUsername(username).get();
        System.out.println("here");
        for(Summoner summoner : user.getFollowedSummoners()){
            if(orianna.checkInGame(summoner.getName(),summoner.getRegion())){
                return orianna.getSummonerStats(summoner.getName(),summoner.getRegion());
            }
        }
        return "nobody in game";
    }

    public void notifyFrontend(String username){
        String responseMessage = checkInGame(username);

        simpMessagingTemplate.convertAndSend("/topic/user",responseMessage);
    }
}
