package com.example.leaguecomp.item.dto;

import com.example.leaguecomp.item.model.Stat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
public class ItemListDTO extends ItemMinimalDTO{
    private Set<String> stats;
}
