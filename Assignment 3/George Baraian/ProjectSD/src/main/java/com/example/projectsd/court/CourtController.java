package com.example.projectsd.court;


import com.example.projectsd.court.model.dto.CourtDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.projectsd.UrlMapping.*;

@RestController
@RequestMapping(COURTS)
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @GetMapping
    public List<CourtDTO> allCourts(){
        return courtService.findAll();
    }

    @PostMapping
    public CourtDTO create(@RequestBody CourtDTO court){
        return courtService.create(court);
    }

    @PatchMapping
    public CourtDTO edit(@RequestBody CourtDTO court){
        return courtService.edit(court);
    }

    @PatchMapping(ENTITY)
    public CourtDTO changeName(@PathVariable Long id, @RequestBody String newName){
        return courtService.changeName(id, newName);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        courtService.delete(id);
    }

}
