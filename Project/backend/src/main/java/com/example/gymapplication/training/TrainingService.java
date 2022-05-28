package com.example.gymapplication.training;

import com.example.gymapplication.training.model.Location;
import com.example.gymapplication.training.model.Training;
import com.example.gymapplication.training.model.dto.TrainingDTO;
import com.example.gymapplication.user.UserRepository;
import com.example.gymapplication.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private final LocationRepository locationRepository;
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final TrainingMapper trainingMapper;

    public TrainingDTO findById(Long id) {
        return trainingMapper.toDto(trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training not found: " + id)));
    }

    public TrainingDTO findByTitle(String title) {
        return trainingMapper.toDto(trainingRepository.findByTitle(title));
    }

    public List<TrainingDTO> findAll() {
        return trainingRepository.findAll().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TrainingDTO> filterTrainings(String filter){
        PageRequest pageRequest = PageRequest.of(0, 10);
        return trainingRepository.findAllByTitleLikeOrTypeLikeOrDateLike("%"+filter+"%",
                        "%"+filter+"%","%"+filter+"%",pageRequest).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    public TrainingDTO create(TrainingDTO training) {
        Training trainingToCreate = trainingMapper.fromDto(training);

        Location location = locationRepository.findByAddress(training.getLocation())
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        User trainer = userRepository.findByUsername(training.getUser())
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        trainingToCreate.setLocation(location);
        trainingToCreate.setUser(trainer);

        return trainingMapper.toDto(trainingRepository.save(trainingToCreate));
    }

    public void delete(Long id) {
        trainingRepository.deleteById(id);
    }

    public TrainingDTO edit(Long id, TrainingDTO training) {
        Training trainingToEdit = trainingRepository.findById(id).get();
        trainingToEdit.setTitle(training.getTitle());
        trainingToEdit.setType(training.getType());
        trainingToEdit.setDate(training.getDate());

        Location location = locationRepository.findByAddress(training.getLocation())
                        .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        User trainer = userRepository.findByUsername(training.getUser())
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        trainingToEdit.setLocation(location);
        trainingToEdit.setUser(trainer);

        return trainingMapper.toDto(trainingRepository.save(trainingToEdit));
    }
}
