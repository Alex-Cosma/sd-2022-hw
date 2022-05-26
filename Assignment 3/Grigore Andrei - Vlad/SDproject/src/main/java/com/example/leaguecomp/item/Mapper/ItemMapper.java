package com.example.leaguecomp.item.Mapper;

import com.example.leaguecomp.item.dto.ItemListDTO;
import com.example.leaguecomp.item.dto.ItemMinimalDTO;
import com.example.leaguecomp.item.model.Item;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mappings({
            @Mapping(target = "name", source = "item.name")
    })
    ItemMinimalDTO itemMinimalFromItem(Item item);

    @Mappings({
            @Mapping(target = "name", source = "item.name"),
            @Mapping(target = "stats", ignore = true)
    })
    ItemListDTO itemListFromItem(Item item);

    @AfterMapping
    default void populateStats(Item item, @MappingTarget ItemListDTO itemListDTO){
        itemListDTO.setStats(item.getStats().stream().map(stat -> stat.getStat() + " " + stat.getValue()).collect(Collectors.toSet()));
    }
}
