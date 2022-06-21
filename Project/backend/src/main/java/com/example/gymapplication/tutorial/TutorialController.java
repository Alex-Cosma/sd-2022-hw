package com.example.gymapplication.tutorial;

import com.example.gymapplication.tutorial.model.Tutorial;
import com.example.gymapplication.security.dto.MessageResponse;
import com.example.gymapplication.tutorial.model.dto.TutorialDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.gymapplication.UrlMapping.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(TUTORIALS)
@RequiredArgsConstructor
public class TutorialController {
    private final TutorialService tutorialService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = TUTORIAL_ADD, method = RequestMethod.POST)
    public ResponseEntity<?> addTutorial(@RequestPart("title") String title, @RequestPart("file") MultipartFile image) throws IOException {
        tutorialService.addTutorial(title, image);

        return ResponseEntity.ok(new MessageResponse("Tutorial uploaded successfully"));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = ALL_TUTORIALS, method = RequestMethod.GET)
    public List<TutorialDTO> getAllTutorials() {
        return tutorialService.getAllTutorials();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = TUTORIAL_DOWNLOAD, method = RequestMethod.GET)
    public ResponseEntity<byte[]> getTutorialForDownload(@PathVariable String title) {
        byte[] imageBytes = tutorialService.getTutorialForDownload(title);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentDispositionFormData("attachment", "tutorial.jpeg");
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);
    }
}
