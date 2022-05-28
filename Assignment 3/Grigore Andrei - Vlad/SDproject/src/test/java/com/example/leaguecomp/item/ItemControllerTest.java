package com.example.leaguecomp.item;

import com.example.leaguecomp.BaseControllerTest;
import com.example.leaguecomp.TestCreationFactory;
import com.example.leaguecomp.item.dto.ItemListDTO;
import com.example.leaguecomp.item.dto.ItemMinimalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.leaguecomp.TestCreationFactory.randomLong;
import static com.example.leaguecomp.UrlMappings.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

import java.util.List;

@SpringBootTest
public class ItemControllerTest extends BaseControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @BeforeEach
    public void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        itemController = new ItemController(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void allItems() throws Exception{
        List<ItemListDTO> itemListDTOS = TestCreationFactory.listOf(ItemListDTO.class);
        when(itemService.allItemsForList()).thenReturn(itemListDTOS);
        ResultActions resultActions = mockMvc.perform(get(ITEMS));
        resultActions.andExpect(status().isOk());
    }


    @Test
    void delete() throws Exception{
        long id = randomLong();
        doNothing().when(itemService).delete(id);

        ResultActions resultActions = performDeleteWIthPathVariables(ITEMS + ENTITY,id);
        verify(itemService,times(1)).delete(id);

        resultActions.andExpect(status().isOk());
    }

}
