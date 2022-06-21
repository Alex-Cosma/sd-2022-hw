package com.example.projectsd.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.example.projectsd.UrlMapping.DOWNLOAD;
import static com.example.projectsd.UrlMapping.RESERVE;

@RestController
@RequestMapping(RESERVE)
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public void create(@RequestParam Long courtId, @RequestParam String username){
        reservationService.create(username, courtId);
    }

    @GetMapping(DOWNLOAD)
    public void downloadCSV() throws IOException {
        reservationService.download();
    }

}
