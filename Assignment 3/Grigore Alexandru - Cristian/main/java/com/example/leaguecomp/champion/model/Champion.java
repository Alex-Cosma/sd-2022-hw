package com.example.leaguecomp.champion.model;

import com.example.leaguecomp.item.model.Item;
import com.example.leaguecomp.rune.model.Rune;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Champion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    @Builder.Default
    @Max(value = 3)
    @Min(value = 1)
    private int damage = 1;

    @Column(nullable = false)
    @Builder.Default
    @Max(value = 3)
    @Min(value = 1)
    private int toughness = 1;

    @Column(nullable = false)
    @Builder.Default
    @Max(value = 3)
    @Min(value = 1)
    private int crowdControl = 1;

    @Column(nullable = false)
    @Builder.Default
    @Max(value = 3)
    @Min(value = 1)
    private int mobility = 1;

    @Column(nullable = false)
    @Builder.Default
    @Max(value = 3)
    @Min(value = 1)
    private int utility = 1;

    @Lob
    @Column(nullable = false)
    private byte[] image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "champ_runes",
            joinColumns = @JoinColumn(name = "champion_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Rune> runes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "champ_items",
            joinColumns = @JoinColumn(name = "champ_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> build = new ArrayList<>();
}
