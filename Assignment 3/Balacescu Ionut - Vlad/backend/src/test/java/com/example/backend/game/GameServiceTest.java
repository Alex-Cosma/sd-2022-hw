package com.example.backend.game;
//unit test MOCK
import com.example.backend.TestCreationFactory;
import com.example.backend.game.model.Game;
import com.example.backend.game.model.dto.GameDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.example.backend.TestCreationFactory.newGame;
import static com.example.backend.TestCreationFactory.randomLong;
import static java.util.Optional.of;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService = new GameService(gameRepository, gameMapper);
    }

    @Test
    void getDataFromApi() {
    }

    @Test
    void findAll() {
        List<Game> games = TestCreationFactory.listOf(Game.class);
        when(gameRepository.findAll()).thenReturn(games);

        List<GameDTO> all = gameService.findAll();

        assertEquals(games.size(), all.size());

    }

    @Test
    void findById() {
        long gameId= randomLong();
        Game newGame = newGame();
        when(gameRepository.findById(gameId)).thenReturn(of(newGame));

        Game game = gameService.findById(gameId);

        assertEquals(newGame,game);
    }
}