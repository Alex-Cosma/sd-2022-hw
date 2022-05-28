package com.project.song;

import com.project.song.model.Song;
import com.project.song.model.dto.SongDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongMapper {
    @Mappings({
          //  @Mapping(target = "album", source = "album.title"),
            @Mapping(target = "artist", source = "artist")
    })
    SongDTO toDto(Song song);

    @Mappings({
           //@Mapping(target = "album.title", source = "album"),
           @Mapping(target = "artist", source = "artist")
    })
    Song fromDto(SongDTO song);

}
