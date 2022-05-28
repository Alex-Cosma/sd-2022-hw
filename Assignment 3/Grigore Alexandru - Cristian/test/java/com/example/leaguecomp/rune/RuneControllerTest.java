package com.example.leaguecomp.rune;

import com.example.leaguecomp.BaseControllerTest;
import com.example.leaguecomp.TestCreationFactory;
import com.example.leaguecomp.rune.dto.RuneDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.leaguecomp.UrlMappings.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RuneControllerTest extends BaseControllerTest {

    @InjectMocks
    private RuneController runeController;

    @Mock
    private RuneService runeService;

    @BeforeEach
    public void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        runeController = new RuneController(runeService);
        mockMvc = MockMvcBuilders.standaloneSetup(runeController).build();
    }

    @Test
    void allRunes() throws Exception{
        List<RuneDTO> runeDTOS = TestCreationFactory.listOf(RuneDTO.class);
        when(runeService.findAll()).thenReturn(runeDTOS);
        ResultActions resultActions = mockMvc.perform(get(RUNES));
        resultActions.andExpect(status().isOk());
    }


}
