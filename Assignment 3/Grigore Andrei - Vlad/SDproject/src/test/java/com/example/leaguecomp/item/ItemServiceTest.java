package com.example.leaguecomp.item;

import com.example.leaguecomp.item.dto.ItemListDTO;
import com.example.leaguecomp.item.dto.ItemMinimalDTO;
import com.example.leaguecomp.item.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Test
    void findAllList(){
        List<ItemListDTO> itemListDTOS = itemService.allItemsForList();
        assertEquals(itemRepository.findAll().size(),itemListDTOS.size());
    }

    @Test
    void delete(){
        Random rand = new Random();
        Long number = 252 % rand.nextLong();
        Item item = itemService.getById(number);
        int current = itemRepository.findAll().size();
        itemService.delete(item.getId());
        assertEquals(current - 1,itemRepository.findAll().size());
    }

}
