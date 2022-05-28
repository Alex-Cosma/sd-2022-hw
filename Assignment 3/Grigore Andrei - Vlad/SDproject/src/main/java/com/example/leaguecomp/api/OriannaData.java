package com.example.leaguecomp.api;

import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.model.Stat;
import com.example.leaguecomp.summoner.RegionRepository;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.rune.model.Rune;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.core.league.League;
import com.merakianalytics.orianna.types.core.league.LeagueEntry;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.staticdata.*;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import com.merakianalytics.orianna.types.core.summoner.Summoners;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OriannaData {

    private final String key = "RGAPI-222341f1-a747-444e-a574-ff4948538006";
    private final RegionRepository regionRepository;

    public void myData(){
        Orianna.setRiotAPIKey(key);
        Orianna.setDefaultPlatform(Platform.EUROPE_NORTH_EAST);
        Summoner fatalElement = Summoner.named("Arobobo").get();
        ChampionMasteries masteries = fatalElement.getChampionMasteries();
        List<ChampionMastery> goodWith = masteries.filter((ChampionMastery mastery) -> mastery.getLevel() >= 6);

        List<String> names = goodWith.stream().map((ChampionMastery mastery) -> mastery.getChampion().getName()).collect(Collectors.toList());
        System.out.println("[" + String.join(", ", names) + "]");
    }

    public List<Champion> getChampions(){
        Orianna.setRiotAPIKey(key);
        Orianna.setDefaultPlatform(Platform.EUROPE_NORTH_EAST);
        Champions champions = Champions.get();
        List<Champion> championList = new ArrayList<>();
        for(int i = 0; i < champions.size();i++){
            Champion champion = Champion.builder()
                    .name(champions.get(i).getName())
                    .description(champions.get(i).getLore())
                    .damage(champions.get(i).getPhysicalRating()%5 + 1)
                    .crowdControl(champions.get(i).getMagicRating()%5 + 1)
                    .toughness(champions.get(i).getDefenseRating()%5 + 1)
                    .mobility((int)champions.get(i).getStats().getMovespeed()%5 + 1)
                    .utility(champions.get(i).getDifficultyRating()%5 + 1)
                    .image(champions.get(i).getImage().toBytes())
                    .build();
            championList.add(champion);
        }
       return championList;
    }

    public List<String> getMastery(String name){
        Orianna.setRiotAPIKey(key);
        Orianna.setDefaultPlatform(Platform.EUROPE_NORTH_EAST);
        Summoner fatalElement = Summoner.named(name).get();
        ChampionMasteries masteries = fatalElement.getChampionMasteries();
        List<ChampionMastery> goodWith = masteries.filter((ChampionMastery mastery) -> mastery.getLevel() >= 6);
        List<String> names = goodWith.stream().map((ChampionMastery mastery) -> mastery.getChampion().getName()).collect(Collectors.toList());

        return names;
    }

    public List<Stat> getStats(){
        Orianna.setRiotAPIKey(key);
        Orianna.setDefaultPlatform(Platform.EUROPE_NORTH_EAST);
        Items items = Items.get();
        List<Stat> stats = new ArrayList<>();
        for(Item item : items) {
            ItemStats itemStats = item.getStats();
            if (itemStats.getAbilityPower() != 0) {
                Stat stat = Stat.builder()
                        .stat("abilityPower")
                        .value(itemStats.getAbilityPower())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getArmor() != 0) {
                Stat stat = Stat.builder()
                        .stat("armor")
                        .value(itemStats.getArmor())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getMovespeed() != 0) {
                Stat stat = Stat.builder()
                        .stat("movespeed")
                        .value(itemStats.getMovespeed())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getAttackDamage() != 0) {
                Stat stat = Stat.builder()
                        .stat("attackDamage")
                        .value(itemStats.getAttackDamage())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getBlock() != 0) {
                Stat stat = Stat.builder()
                        .stat("block")
                        .value(itemStats.getBlock())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getAttackSpeed() != 0) {
                Stat stat = Stat.builder()
                        .stat("attackSpeed")
                        .value(itemStats.getAttackSpeed())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getCriticalStrikeChance() != 0) {
                Stat stat = Stat.builder()
                        .stat("crit")
                        .value(itemStats.getCriticalStrikeChance())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getCriticalStrikeDamage() != 0) {
                Stat stat = Stat.builder()
                        .stat("critdmg")
                        .value(itemStats.getCriticalStrikeDamage())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getEnergy() != 0) {
                Stat stat = Stat.builder()
                        .stat("energy")
                        .value(itemStats.getEnergy())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getEnergyRegen() != 0) {
                Stat stat = Stat.builder()
                        .stat("energyRegen")
                        .value(itemStats.getEnergyRegen())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getExperience() != 0) {
                Stat stat = Stat.builder()
                        .stat("experience")
                        .value(itemStats.getExperience())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getHealth() != 0) {
                Stat stat = Stat.builder()
                        .stat("health")
                        .value(itemStats.getHealth())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getHealthRegen() != 0) {
                Stat stat = Stat.builder()
                        .stat("healthRegen")
                        .value(itemStats.getHealthRegen())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getMagicResist() != 0) {
                Stat stat = Stat.builder()
                        .stat("magicResist")
                        .value(itemStats.getMagicResist())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getMana() != 0) {
                Stat stat = Stat.builder()
                        .stat("mana")
                        .value(itemStats.getMana())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getManaRegen() != 0) {
                Stat stat = Stat.builder()
                        .stat("manaRegen")
                        .value(itemStats.getManaRegen())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentAbilityPower() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentAbilityPower")
                        .value(itemStats.getPercentAbilityPower())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentArmor() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentArmor")
                        .value(itemStats.getPercentArmor())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentAttackDamage() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentAttackDamage")
                        .value(itemStats.getPercentAttackDamage())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentAttackSpeed() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentAttackSpeed")
                        .value(itemStats.getPercentAttackSpeed())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentBlock() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentBlock")
                        .value(itemStats.getPercentBlock())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentCriticalStrikeChance() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentCritChance")
                        .value(itemStats.getPercentCriticalStrikeChance())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentCriticalStrikeChange() != 0) {
                Stat stat = Stat.builder()
                        .stat("pCritChange")
                        .value(itemStats.getPercentCriticalStrikeChange())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentDodge() != 0) {
                Stat stat = Stat.builder()
                        .stat("perDodge")
                        .value(itemStats.getPercentDodge())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentExperience() != 0) {
                Stat stat = Stat.builder()
                        .stat("perXP")
                        .value(itemStats.getPercentExperience())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentHealth() != 0) {
                Stat stat = Stat.builder()
                        .stat("perHP")
                        .value(itemStats.getPercentHealth())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentHealthRegen() != 0) {
                Stat stat = Stat.builder()
                        .stat("perHPReg")
                        .value(itemStats.getPercentHealthRegen())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentLifesteal() != 0) {
                Stat stat = Stat.builder()
                        .stat("%LifeSteal")
                        .value(itemStats.getPercentLifesteal())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentMagicResist() != 0) {
                Stat stat = Stat.builder()
                        .stat("%MR")
                        .value(itemStats.getManaRegen())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getAttackDamage() != 0) {
                Stat stat = Stat.builder()
                        .stat("attackDamage")
                        .value(itemStats.getAttackDamage())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentMana() != 0) {
                Stat stat = Stat.builder()
                        .stat("%Mana")
                        .value(itemStats.getPercentMana())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentManaRegen() != 0) {
                Stat stat = Stat.builder()
                        .stat("%ManaReg")
                        .value(itemStats.getPercentManaRegen())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentMovespeed() != 0) {
                Stat stat = Stat.builder()
                        .stat("%Speed")
                        .value(itemStats.getPercentMovespeed())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
            if (itemStats.getPercentSpellVamp() != 0) {
                Stat stat = Stat.builder()
                        .stat("%LF")
                        .value(itemStats.getPercentSpellVamp())
                        .build();
                if(!stats.contains(stat)){
                    stats.add(stat);
                }
            }
        }
        return stats;
    }

    public List<com.example.leaguecomp.item.model.Item> getItems(){
        Orianna.setRiotAPIKey(key);
        Orianna.setDefaultPlatform(Platform.EUROPE_NORTH_EAST);
        List<com.example.leaguecomp.item.model.Item> items1 = new ArrayList<>();
        Items items = Items.get();
        for(Item item : items) {
            Set<Stat> stats = new HashSet<>();
            ItemStats itemStats = item.getStats();
            if (itemStats.getAbilityPower() != 0) {
                Stat stat = Stat.builder()
                        .stat("abilityPower")
                        .value(itemStats.getAbilityPower())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getArmor() != 0) {
                Stat stat = Stat.builder()
                        .stat("armor")
                        .value(itemStats.getArmor())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getMovespeed() != 0) {
                Stat stat = Stat.builder()
                        .stat("movespeed")
                        .value(itemStats.getMovespeed())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getAttackDamage() != 0) {
                Stat stat = Stat.builder()
                        .stat("attackDamage")
                        .value(itemStats.getAttackDamage())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getBlock() != 0) {
                Stat stat = Stat.builder()
                        .stat("block")
                        .value(itemStats.getBlock())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getAttackSpeed() != 0) {
                Stat stat = Stat.builder()
                        .stat("attackSpeed")
                        .value(itemStats.getAttackSpeed())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getCriticalStrikeChance() != 0) {
                Stat stat = Stat.builder()
                        .stat("crit")
                        .value(itemStats.getCriticalStrikeChance())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getCriticalStrikeDamage() != 0) {
                Stat stat = Stat.builder()
                        .stat("critdmg")
                        .value(itemStats.getCriticalStrikeDamage())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getEnergy() != 0) {
                Stat stat = Stat.builder()
                        .stat("energy")
                        .value(itemStats.getEnergy())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getEnergyRegen() != 0) {
                Stat stat = Stat.builder()
                        .stat("energyRegen")
                        .value(itemStats.getEnergyRegen())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getExperience() != 0) {
                Stat stat = Stat.builder()
                        .stat("experience")
                        .value(itemStats.getExperience())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getHealth() != 0) {
                Stat stat = Stat.builder()
                        .stat("health")
                        .value(itemStats.getHealth())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getHealthRegen() != 0) {
                Stat stat = Stat.builder()
                        .stat("healthRegen")
                        .value(itemStats.getHealthRegen())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getMagicResist() != 0) {
                Stat stat = Stat.builder()
                        .stat("magicResist")
                        .value(itemStats.getMagicResist())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getMana() != 0) {
                Stat stat = Stat.builder()
                        .stat("mana")
                        .value(itemStats.getMana())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getManaRegen() != 0) {
                Stat stat = Stat.builder()
                        .stat("manaRegen")
                        .value(itemStats.getManaRegen())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentAbilityPower() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentAbilityPower")
                        .value(itemStats.getPercentAbilityPower())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentArmor() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentArmor")
                        .value(itemStats.getPercentArmor())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentAttackDamage() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentAttackDamage")
                        .value(itemStats.getPercentAttackDamage())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentAttackSpeed() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentAttackSpeed")
                        .value(itemStats.getPercentAttackSpeed())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentBlock() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentBlock")
                        .value(itemStats.getPercentBlock())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentCriticalStrikeChance() != 0) {
                Stat stat = Stat.builder()
                        .stat("percentCritChance")
                        .value(itemStats.getPercentCriticalStrikeChance())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentCriticalStrikeChange() != 0) {
                Stat stat = Stat.builder()
                        .stat("pCritChange")
                        .value(itemStats.getPercentCriticalStrikeChange())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentDodge() != 0) {
                Stat stat = Stat.builder()
                        .stat("perDodge")
                        .value(itemStats.getPercentDodge())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentExperience() != 0) {
                Stat stat = Stat.builder()
                        .stat("perXP")
                        .value(itemStats.getPercentExperience())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentHealth() != 0) {
                Stat stat = Stat.builder()
                        .stat("perHP")
                        .value(itemStats.getPercentHealth())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentHealthRegen() != 0) {
                Stat stat = Stat.builder()
                        .stat("perHPReg")
                        .value(itemStats.getPercentHealthRegen())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentLifesteal() != 0) {
                Stat stat = Stat.builder()
                        .stat("%LifeSteal")
                        .value(itemStats.getPercentLifesteal())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentMagicResist() != 0) {
                Stat stat = Stat.builder()
                        .stat("%MR")
                        .value(itemStats.getManaRegen())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getAttackDamage() != 0) {
                Stat stat = Stat.builder()
                        .stat("attackDamage")
                        .value(itemStats.getAttackDamage())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentMana() != 0) {
                Stat stat = Stat.builder()
                        .stat("%Mana")
                        .value(itemStats.getPercentMana())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentManaRegen() != 0) {
                Stat stat = Stat.builder()
                        .stat("%ManaReg")
                        .value(itemStats.getPercentManaRegen())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentMovespeed() != 0) {
                Stat stat = Stat.builder()
                        .stat("%Speed")
                        .value(itemStats.getPercentMovespeed())
                        .build();
                stats.add(stat);
            }
            if (itemStats.getPercentSpellVamp() != 0) {
                Stat stat = Stat.builder()
                        .stat("%LF")
                        .value(itemStats.getPercentSpellVamp())
                        .build();
                stats.add(stat);
            }
            com.example.leaguecomp.item.model.Item item1 = com.example.leaguecomp.item.model.Item
                    .builder()
                    .name(item.getName())
                    .stats(stats)
                    .image(item.getImage().getFull())
                    .build();
            items1.add(item1);
        }
        return items1;
    }

    public List<Rune> getRunes(){
        Orianna.setRiotAPIKey(key);
        Orianna.setDefaultPlatform(Platform.EUROPE_NORTH_EAST);
        List<Rune> runes = new ArrayList<>();
        ReforgedRunes reforgedRunes = ReforgedRunes.withRegion(Region.EUROPE_NORTH_EAST).get();
        System.out.println(reforgedRunes.get(0).getName());
        runes = reforgedRunes.stream().map(reforgedRune -> Rune.builder()
                .name(reforgedRune.getName())
                .description(reforgedRune.getShortDescription())
                .build()).collect(Collectors.toList());

        return runes;
    }

    public List<com.example.leaguecomp.summoner.model.Summoner> getSummoners(){
        Orianna.setRiotAPIKey(key);
        Orianna.setDefaultPlatform(Platform.EUROPE_NORTH_EAST);
        League league = League.challengerInQueue(Queue.RANKED_SOLO).withRegion(Region.EUROPE_NORTH_EAST).get();
        List<com.example.leaguecomp.summoner.model.Summoner> summoners = new ArrayList<>();
        for (int i=0;i<50;i++) {
            LeagueEntry leagueEntry = league.get(i);
            Summoner summoner = leagueEntry.getSummoner();
            com.example.leaguecomp.summoner.model.Region region = regionRepository.findByRegion(ERegion.valueOf(summoner.getRegion().name())).orElseThrow(() -> new RuntimeException("region not found"));
            com.example.leaguecomp.summoner.model.Summoner summ = com.example.leaguecomp.summoner.model.Summoner.builder()
                    .name(summoner.getName())
                    .region(region)
                    .league(summoner.getLeague(Queue.RANKED_SOLO).getTier().name())
                    .build();
            summoners.add(summ);

        }
        return summoners;
    }

    public String getSummonerStats(String name, com.example.leaguecomp.summoner.model.Region region){
        Orianna.setRiotAPIKey(key);
        Orianna.setDefaultPlatform(Platform.EUROPE_NORTH_EAST);
        Region region1 = Region.valueOf(region.getRegion().name());
        Summoner summoner = Summoners.named(name).withRegion(region1).get().get(0);
        CurrentMatch match = CurrentMatch.forSummoner(summoner).get();
        String champ = match.getParticipants().find(summoner).getChampion().getName();
        String path1 = match.getParticipants().find(summoner).getRunes().getPrimaryPath().name();
        String path2 = match.getParticipants().find(summoner).getRunes().getSecondaryPath().name();
        StringBuilder stringBuilder = new StringBuilder().append(champ).append('\n').append(path1).append('\n').append(path2);
        return stringBuilder.toString();
    }

    public Boolean checkInGame(String name, com.example.leaguecomp.summoner.model.Region region){
        Orianna.setRiotAPIKey(key);
        Orianna.setDefaultPlatform(Platform.EUROPE_NORTH_EAST);
        Region region1 = Region.valueOf(region.getRegion().name());
        Summoner summoner =  Summoners.named(name).withRegion(region1).get().get(0);
        return summoner.isInGame();
    }


}
