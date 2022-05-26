package com.example.leaguecomp.Champion;

import com.example.leaguecomp.BaseControllerTest;
import com.example.leaguecomp.TestCreationFactory;
import com.example.leaguecomp.champion.ChampionController;
import com.example.leaguecomp.champion.ChampionService;
import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.dto.ItemListDTO;
import com.example.leaguecomp.rune.dto.RuneDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.leaguecomp.TestCreationFactory.*;
import static com.example.leaguecomp.UrlMappings.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChampionControllerTest extends BaseControllerTest {

    @InjectMocks
    private ChampionController championController;

    @Mock
    private ChampionService championService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        championController = new ChampionController(championService);
        mockMvc = MockMvcBuilders.standaloneSetup(championController).
                setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void testController() throws Exception{

        ResultActions response = mockMvc.perform(get(CHAMPS));
        response.andExpect(status().isOk());
        ResultActions invalidRequest = mockMvc.perform(get("/invalidSomething"));
        invalidRequest.andExpect(status().isNotFound());

    }

    @Test
    void allChamps() throws Exception{

        List<ChampionDTO> championDTOList = TestCreationFactory.listOf(Champion.class);
        when(championService.findAll()).thenReturn(championDTOList);
        ResultActions resultActions = mockMvc.perform(get(CHAMPS));
        resultActions.andExpect(status().isOk());

    }
    @Test
    void findAllByName() throws  Exception{
        ChampionDTO championDTO = ChampionDTO.builder()
                .id(randomLong())
                .name(randomString())
                .crowdControl(1)
                .damage(1)
                .description(randomString())
                .image(randomBytes())
                .mobility(1)
                .toughness(1)
                .utility(1)
                .build();
        when(championService.findByName("Aatrox")).thenReturn(championDTO);

        ResultActions resultActions = performGetWithPathVariable("http://localhost:8080/api/champs/{name}","Aatrox");
        resultActions.andExpect(status().isOk());

    }

    @Test
    void edit() throws Exception{
        long id = randomLong();
        Champion champion = new Champion();
        when(championService.findById(id)).thenReturn(champion);
        String name = randomString();
        ChampionDTO championDTO = ChampionDTO.builder()
                .utility(1)
                .toughness(1)
                .mobility(1)
                .image(randomBytes())
                .description(randomString())
                .damage(1)
                .id(id)
                .name(name)
                .build();
        when(championService.edit(id,championDTO)).thenReturn(championDTO);
        assertEquals(championDTO.getName(),name);
    }


}
