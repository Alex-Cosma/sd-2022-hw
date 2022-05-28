package com.policeSection.fine;

import com.policeSection.fine.FineService;
import com.policeSection.fine.Model.dto.FineDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.policeSection.UrlMapping.*;


@RestController
@RequestMapping(FINES)
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;

    @GetMapping
    public List<FineDTO> allItems() {
        return fineService.findAll();
    }


    @PostMapping(CREATE_FINE)
    public FineDTO create(@RequestBody FineDTO item) {
        return fineService.create(item);
    }

    @DeleteMapping(FINES_ID_PART)
    public void delete(@PathVariable Long id) {
        fineService.delete(id);
    }

    @PutMapping(FINES_ID_PART)
    public FineDTO edit(@PathVariable Long id, @RequestBody FineDTO item) {
        return fineService.edit(id, item);
    }

}
