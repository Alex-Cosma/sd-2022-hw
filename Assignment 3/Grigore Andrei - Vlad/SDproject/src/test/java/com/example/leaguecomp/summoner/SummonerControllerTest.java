package com.example.leaguecomp.summoner;

import com.example.leaguecomp.BaseControllerTest;
import com.example.leaguecomp.summoner.dto.SummonerDTO;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.ArrayList;
import java.util.List;

import static com.example.leaguecomp.TestCreationFactory.randomLong;
import static com.example.leaguecomp.TestCreationFactory.randomString;
import static com.example.leaguecomp.UrlMappings.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SummonerControllerTest extends BaseControllerTest {
    @InjectMocks
    private SummonerController controller;

    @Mock
    private SummonerService service;

    @Mock
    private RegionRepository repository;

    private List<SummonerDTO> summoners;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
        controller = new SummonerController(service,repository);
        summoners = new ArrayList();
    }

    @Test
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(summoners);
        List<SummonerDTO> summs = controller.findAll();
        assertEquals(summoners.size(),summs.size());
        ResultActions response = performGet("/api/summoners");
        response.andExpect(status().isOk());
    }

    @Test
    void findByName() throws Exception {

        String name = randomString();
        SummonerDTO reqSummoner = SummonerDTO.builder()
                .id(randomLong())
                .name(name)
                .league("IRON")
                .build();
        when(service.create(reqSummoner)).thenReturn(reqSummoner);

        ResultActions result = performGetWithPathVariable(SUMMONERS + "/{name}", name);
        result.andExpect(status().isNotFound());
    }

    @Test
    void create() {
        Region region = new Region();
        region.setRegion(ERegion.EUROPE_NORTH_EAST);
        repository.save(region);
        SummonerDTO summonerDTO = SummonerDTO.builder()
                .name("Jerry")
                .league("SILVER")
                .region(region)
                .build();
        when(controller.create(summonerDTO)).thenReturn(summonerDTO);
        assertNotNull(summonerDTO);
    }


    @Test
    void edit() throws Exception {
        Region region = new Region();
        region.setRegion(ERegion.EUROPE_NORTH_EAST);
        long id = randomLong();
        SummonerDTO summonerDTO = SummonerDTO.builder()
                .id(id)
                .name(randomString())
                .league(randomString())
                .region(region)
                .build();
        when(service.edit(id,summonerDTO)).thenReturn(summonerDTO);
        ResultActions resultActions = performPostWithRequestBodyAndPathVariables(SUMMONERS +"/edit" + ENTITY,summonerDTO,id);
        resultActions.andExpect(status().isOk())
                .andExpect(jsonContentToBe(summonerDTO));
    }
    @Test
    void getMastery() throws Exception {
        Region region = new Region();
        region.setRegion(ERegion.EUROPE_NORTH_EAST);
        repository.save(region);
        SummonerDTO summonerDTO = SummonerDTO.builder()
                .name("Jerry")
                .league("SILVER")
                .region(region)
                .build();
        when(controller.create(summonerDTO)).thenReturn(summonerDTO);
        ResultActions result = performGetWithPathVariable("http://localhost:8080/api/summoners/mastery/{name}",summonerDTO.getName());
        result.andExpect(status().isOk());
    }


}