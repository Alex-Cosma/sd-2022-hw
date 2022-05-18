package com.example.youtubeish.playlist.mapper;

import com.example.youtubeish.playlist.model.Playlist;
import com.example.youtubeish.playlist.dto.PlaylistDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    PlaylistDTO toDto(Playlist playlist);

}
