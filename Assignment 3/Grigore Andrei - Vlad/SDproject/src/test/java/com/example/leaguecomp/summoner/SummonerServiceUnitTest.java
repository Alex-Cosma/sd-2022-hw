package com.example.leaguecomp.summoner;

import com.example.leaguecomp.api.OriannaData;
import com.example.leaguecomp.summoner.dto.SummonerDTO;
import com.example.leaguecomp.summoner.mapper.SummonerMapper;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.summoner.model.Summoner;
import com.example.leaguecomp.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.leaguecomp.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class SummonerServiceUnitTest {

    @InjectMocks
    private SummonerService service;

    @Mock
    private SummonerRepository repository;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private SummonerMapper summonerMapper;

    @Mock
    private OriannaData orianna;

    @Mock
    private SimpMessagingTemplate simpMsg;

    @Mock
    private UserRepository userRepository;
    private List<Summoner> summoners;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        service = new SummonerService(repository,regionRepository,summonerMapper,orianna,userRepository,simpMsg);
        summoners = new ArrayList<>();
    }

    @Test
    void findAll() {
        List<Summoner> summoners = new ArrayList<>();
        when(repository.findAll()).thenReturn(summoners);

        List<SummonerDTO> summonerDTOS = service.findAll();
        assertEquals(summoners.size(),summonerDTOS.size());

    }

    @Test
    void findAllByName(){
        when(repository.findAllByName("Zed")).thenReturn(summoners);
        List<SummonerDTO> summonerDTOS = service.findAllByName("Zed");
        assertEquals(summoners.size(),summonerDTOS.size());
    }


    @Test
    void create() {
        Region region = new Region();
        region.setRegion(ERegion.EUROPE_NORTH_EAST);
        when(regionRepository.save(region)).thenReturn(region);
        regionRepository.save(region);
        String name = randomString();
        SummonerDTO summonerDTO = SummonerDTO.builder()
                .name(name)
                .league("SILVER")
                .region(region)
                .build();

        when(service.create(summonerDTO)).thenReturn(summonerDTO);
        summonerDTO = service.create(summonerDTO);
        assertEquals(summonerDTO.getName(),name);
    }



    @Test
    void edit(){
        long id = randomLong();
        Summoner summoner = new Summoner();
        when(repository.findById(id)).thenReturn(Optional.of(summoner));
        String name = randomString();
        SummonerDTO summonerDTO = SummonerDTO.builder()
                .name(name)
                .id(id)
                .league(randomString())
                .region(randomRegion())
                .build();
        when(service.edit(id,summonerDTO)).thenReturn(summonerDTO);
        service.edit(id,summonerDTO);
        assertEquals(summoner.getName(),name);
    }

    @Test
    void findByName(){
        String name = randomString();
        Summoner summoner = newSummoner();
        SummonerDTO summoner1 = newSummonerDTO();
        when(repository.findByName(name)).thenReturn(Optional.ofNullable(summoner));
        when(summonerMapper.toDto(summoner)).thenReturn(summoner1);
        SummonerDTO summonerDTO = service.findByName(name);
        assertEquals(summonerDTO.getName(),summoner1.getName());
    }

    @Test
    void getMastery(){
        String name = randomString();
        List<String> mastery = new ArrayList<>();
        mastery = service.getMastery(name);
        when(service.getMastery(name)).thenReturn(mastery);
        assertNotNull(mastery);
    }


}