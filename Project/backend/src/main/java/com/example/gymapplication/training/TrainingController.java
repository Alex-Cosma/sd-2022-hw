package com.example.gymapplication.training;

import com.example.gymapplication.security.dto.MessageResponse;
import com.example.gymapplication.training.model.dto.TrainingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.gymapplication.UrlMapping.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(TRAININGS)
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;

    @GetMapping
    public List<TrainingDTO> allTrainings() {
        return trainingService.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(FILTER)
    public List<TrainingDTO> filterTrainings(@PathVariable String filter){
        return trainingService.filterTrainings(filter);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TrainingDTO training) {
        if(trainingService.findByTitle(training.getTitle()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Training already exist!"));
        }

        trainingService.create(training);

        return ResponseEntity.ok(new MessageResponse("Training created successfully"));
    }

    @CrossOrigin(origins = "*")
    @PatchMapping(TRAINING_ID_PATH)
    public ResponseEntity<?> edit(@PathVariable Long id ,@Valid  @RequestBody TrainingDTO training) {
        if(!trainingService.findById(id).getTitle().equals(training.getTitle())) {
            if (trainingService.findByTitle(training.getTitle()) != null) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Training already exist!"));
            }
        }

        trainingService.edit(id, training);

        return ResponseEntity.ok(new MessageResponse("Training edited successfully"));
    }

    @DeleteMapping(TRAINING_ID_PATH)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        trainingService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Training deleted successfully"));
    }
}
