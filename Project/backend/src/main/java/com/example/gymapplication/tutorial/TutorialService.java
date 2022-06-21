package com.example.gymapplication.tutorial;

import com.example.gymapplication.tutorial.model.Tutorial;
import com.example.gymapplication.tutorial.model.dto.TutorialDTO;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TutorialService {
    private final TutorialRepository tutorialRepository;
    private final TutorialMapper tutorialMapper;

    public void addTutorial(String title, MultipartFile file) throws IOException {
        Tutorial tutorial = new Tutorial(title);
        tutorial.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        tutorialRepository.insert(tutorial);
    }

    public List<TutorialDTO> getAllTutorials() {
        return tutorialRepository.findAll().stream()
                .map(tutorialMapper::toDto)
                .collect(toList());
    }

    public byte[] getTutorialForDownload(String title) {
        Tutorial tutorial = tutorialRepository.findByTitle(title);

        return tutorial.getImage().getData();
    }

}
