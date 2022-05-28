package com.example.leaguecomp.item;

import com.example.leaguecomp.item.dto.ItemListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.leaguecomp.UrlMappings.ENTITY;
import static com.example.leaguecomp.UrlMappings.ITEMS;

@RestController
@RequiredArgsConstructor
@RequestMapping(ITEMS)
@CrossOrigin
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemListDTO> allItems() {
        return itemService.allItemsForList();
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

}
