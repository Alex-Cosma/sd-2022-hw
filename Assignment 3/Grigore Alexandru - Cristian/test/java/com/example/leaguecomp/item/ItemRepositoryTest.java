package com.example.leaguecomp.item;

import com.example.leaguecomp.item.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void findAll(){
        assertEquals(itemRepository.findAll().size(),254);
    }

    @Test
    void findByName(){
        Item item = itemRepository.findByName("Boots").get();
        assertEquals(item.getName(),"Boots");
    }

    @Test
    void findByNameLike(){
        List<Item> items = itemRepository.findAllByNameLike("B%");
        Item item = itemRepository.findByName("Boots").get();
        assertTrue(items.contains(item));
    }
}
