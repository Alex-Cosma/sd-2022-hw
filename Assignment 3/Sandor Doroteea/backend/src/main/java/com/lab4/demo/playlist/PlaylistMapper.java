package com.lab4.demo.playlist;

import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    Playlist fromDTO(PlaylistDTO playlistDTO);

    PlaylistDTO toDTO(Playlist playlist);
}
