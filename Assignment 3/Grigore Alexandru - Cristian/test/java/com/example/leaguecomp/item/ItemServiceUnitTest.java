package com.example.leaguecomp.item;

import com.example.leaguecomp.TestCreationFactory;
import com.example.leaguecomp.item.Mapper.ItemMapper;
import com.example.leaguecomp.item.dto.ItemListDTO;
import com.example.leaguecomp.item.dto.ItemMinimalDTO;
import com.example.leaguecomp.item.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.example.leaguecomp.TestCreationFactory.randomLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ItemServiceUnitTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        itemService = new ItemService(itemRepository,itemMapper);
    }

    @Test
    void findAllList(){
        List<Item> items = TestCreationFactory.listOf(Item.class);
        when(itemRepository.findAll()).thenReturn(items);

        List<ItemListDTO> itemListDTOS = itemService.allItemsForList();
        Assertions.assertEquals(items.size(),itemListDTOS.size());
    }


    @Test
    void delete(){
        long itemId = randomLong();
        doNothing().when(itemRepository).deleteById(itemId);
        itemRepository.deleteById(itemId);
        verify(itemRepository,times(1)).deleteById(itemId);
    }

}
