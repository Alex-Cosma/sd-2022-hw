package com.lab4.demo.track;


import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.dto.TrackDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackDTO toDto(Track track);

    Track fromDto(TrackDTO item);
}
