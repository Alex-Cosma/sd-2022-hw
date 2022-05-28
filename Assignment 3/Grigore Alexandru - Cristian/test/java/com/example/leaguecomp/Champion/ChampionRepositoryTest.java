package com.example.leaguecomp.Champion;

import com.example.leaguecomp.champion.ChampionRepository;
import com.example.leaguecomp.champion.model.Champion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.leaguecomp.TestCreationFactory.randomBytes;
import static com.example.leaguecomp.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChampionRepositoryTest {

    @Autowired
    private ChampionRepository championRepository;

    @Test
    void findAll(){
        List<Champion> championList = championRepository.findAll();
        assertEquals(championList.size(),159);
    }

    @Test
    void findByName(){
        Champion champion = Champion.builder()
                .name("Chewbacca")
                .image(randomBytes())
                .description(randomString())
                .build();
        championRepository.save(champion);
        Champion champion1 = championRepository.findByName("Chewbacca").get();
        assertEquals(champion.getName(),champion1.getName());
    }

}
