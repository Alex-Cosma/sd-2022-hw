package com.example.leaguecomp.summoner.dto;

import com.example.leaguecomp.summoner.model.Region;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SummonerDTO {
    private Long id;
    private String name;
    private String league;
    private Region region;
}
