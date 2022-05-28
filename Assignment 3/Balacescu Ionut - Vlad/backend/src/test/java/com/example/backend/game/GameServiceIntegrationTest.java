package com.example.backend.game;
//integration test auto
import com.example.backend.TestCreationFactory;
import com.example.backend.game.model.Game;
import com.example.backend.game.model.dto.GameDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.backend.TestCreationFactory.newGame;
import static com.example.backend.TestCreationFactory.randomLong;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceIntegrationTest {
    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;


    @BeforeEach
    void setUp(){
        gameRepository.deleteAll();
    }

    @Test
    void getDataFromApi() {
    }

    @Test
    void findAll() {
        List<Game> games = TestCreationFactory.listOf(Game.class);
        gameRepository.saveAll(games);

        List<GameDTO> all = gameService.findAll();

        Assertions.assertEquals(games.size(),all.size());
    }

    @Test
    void findById() {
        Game newGame = newGame();
        Game aux = gameRepository.save(newGame);
        Game game = gameService.findById(aux.getId());
        Assertions.assertEquals(game.getId(),newGame.getId());
    }
}