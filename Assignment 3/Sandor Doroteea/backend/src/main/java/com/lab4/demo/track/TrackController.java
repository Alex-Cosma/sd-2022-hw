package com.lab4.demo.track;

import com.lab4.demo.track.model.dto.TrackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@Validated
@RestController
@RequestMapping(TRACKS)
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @GetMapping
    public List<TrackDTO> getTracks(){
        return trackService.findAll();
    }

    @PostMapping
    public TrackDTO createTrack(@RequestBody TrackDTO track) {
        return trackService.create(track);
    }

    @DeleteMapping(TRACK_ID_PART)
    public void deleteTrack(@PathVariable Long id) {
        trackService.delete(id);
    }

    @PutMapping(TRACK_ID_PART)
    public TrackDTO editTrack(@PathVariable Long id, @RequestBody TrackDTO track) {
        return trackService.edit(id, track);
    }





}
