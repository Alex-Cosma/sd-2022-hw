package com.example.leaguecomp.champion.dto;

import com.example.leaguecomp.item.dto.ItemListDTO;

import com.example.leaguecomp.item.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChampionDTO {
    private Long id;
    private String name;
    private String description;
    private int damage;
    private int toughness;
    private int crowdControl;
    private int mobility;
    private int utility;
    private byte[] image;
    private Set<String> runes;
    private List<String> build;
}
