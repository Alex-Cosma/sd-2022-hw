package com.example.backend.game;


import com.example.backend.game.model.Game;
import com.example.backend.game.model.dto.GameDTO;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;



import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.Closeable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;


    public void getDataFromApi() {

        HttpResponse<String> response = Unirest.get("https://free-to-play-games-database.p.rapidapi.com/api/games")
                .header("X-RapidAPI-Host", "free-to-play-games-database.p.rapidapi.com")
                .header("X-RapidAPI-Key", "a32118bb1fmshaeaa79de1a08b62p1a2c17jsnb5b9b3ea30ea")
                .asString();
        JSONArray gamesJson = new JSONArray(response.getBody());
        for (int i = 0; i < gamesJson.length(); i++) {
            JSONObject currentGame = gamesJson.getJSONObject(i);
            String title = "";
            String thumbnail = "";
            String description = "";
            String gameURL = "";
            String genre = "";
            String platform = "";
            String publisher = "";
            String developer = "";
            String releaseDate = "";
            String freeToGameProfileURL = "";
            if(currentGame.has("title")){
                title = currentGame.getString("title");
            }
            if(currentGame.has("thumbnail")){
                thumbnail = currentGame.getString("thumbnail");
            }
            if(currentGame.has("short_description")){
                description = currentGame.getString("short_description");
            }
            if(currentGame.has("game_url")){
                gameURL = currentGame.getString("game_url");
            }
            if(currentGame.has("genre")){
                genre = currentGame.getString("genre");
            }if(currentGame.has("platform")){
                platform = currentGame.getString("platform");
            }
            if(currentGame.has("publisher")){
                publisher = currentGame.getString("publisher");
            }
            if(currentGame.has("developer")){
                developer = currentGame.getString("developer");
            }
            if(currentGame.has("release_date")){
                releaseDate = currentGame.getString("release_date");
            }
            if(currentGame.has("freetogame_profile_url")){
                freeToGameProfileURL = currentGame.getString("freetogame_profile_url");
            }

            Game newGame = Game.builder()
                    .title(title)
                    .description(description)
                    .developer(developer)
                    .freeToGameProfileURL(freeToGameProfileURL)
                    .gameURL(gameURL)
                    .genre(genre)
                    .platform(platform)
                    .publisher(publisher)
                    .releaseDate(releaseDate)
                    .thumbnail(thumbnail)
                    .build();
            gameRepository.save(newGame);
        }
    }

    public List<GameDTO> findAll() {
        return gameRepository.findAll().stream()
                .map(gameMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Game findById(Long id){
        return gameRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Game not found: "+ id));
    }
}
