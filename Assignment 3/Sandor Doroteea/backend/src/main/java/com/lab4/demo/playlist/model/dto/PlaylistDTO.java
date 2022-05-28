package com.lab4.demo.playlist.model.dto;

import com.lab4.demo.track.model.dto.TrackDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDTO {

    private Long id;

    private String title;

    private List<TrackDTO> tracks;

    private Integer duration;

    public void setDuration(){
        this.duration = 0;
        for(TrackDTO track: this.tracks) {
            this.duration += track.getDuration();
        }
    }
}
