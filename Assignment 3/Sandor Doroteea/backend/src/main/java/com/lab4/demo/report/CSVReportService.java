package com.lab4.demo.report;

import com.lab4.demo.track.model.Track;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Optional;

import static com.lab4.demo.report.ReportType.CSV;

@RequiredArgsConstructor
@Service
public class CSVReportService implements ReportService {
    private final UserRepository userRepository;
    @SneakyThrows
    @Override

    public String export(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {

            File file = new File("src/main/resources/MyTracks.csv");

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Id,Title,Artist,Link,Duration,Album,Lyrics");
            bufferedWriter.newLine();

            for (Track track: user.get().getPurchasedTracks()) {
                bufferedWriter.write(track.getId().toString()
                        + "," + track.getTitle()
                        + "," + track.getArtist()
                        + "," + track.getLink()
                        + "," + track.getDuration()
                        + "," + track.getAlbum()
                        + "," + track.getExplicit_lyrics());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();
        }

        File file = new File("src/main/resources/MyTracks.csv");
        Resource resource = new UrlResource(file.toPath().toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource.getFile().getAbsolutePath();
        }

        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
