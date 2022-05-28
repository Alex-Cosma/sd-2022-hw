package com.example.backend.game;

import com.example.backend.game.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.example.backend.TestCreationFactory.newGame;
import static com.example.backend.TestCreationFactory.randomLong;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    public void beforeEach(){
        gameRepository.deleteAll();
    }

    @Test
    public void testSave(){
        Game gameSaved = gameRepository.save(Game.builder()
                .title("a")
                .genre("a")
                .description("a")
                .gameURL("a")
                .freeToGameProfileURL("a")
                .developer("a")
                .platform("a")
                .thumbnail("a")
                .publisher("a")
                .releaseDate("a").build());
        assertNotNull(gameSaved);
        assertEquals(1,gameRepository.findAll().size());
    }

}