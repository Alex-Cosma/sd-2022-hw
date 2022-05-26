package com.example.leaguecomp.summoner.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Summoner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 50)
    private String league;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "summoner_region",
    joinColumns = @JoinColumn(name = "summoner_id"))
    private Region region;
}
