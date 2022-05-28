package com.example.backend.game;


import com.example.backend.game.model.Game;
import com.example.backend.game.model.dto.GameDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {

    GameDTO toDTO(Game game);

    Game fromDTO(GameDTO gameDTO);


}
