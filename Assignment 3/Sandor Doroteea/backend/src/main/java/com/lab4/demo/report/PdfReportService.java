package com.lab4.demo.report;

import com.lab4.demo.track.model.Track;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static com.lab4.demo.report.ReportType.PDF;

@RequiredArgsConstructor
@Service
public class PdfReportService implements ReportService {

    private final UserRepository userRepository;

    @Override
    public String export(Long id) throws IOException {

        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {

            try (PDDocument doc = new PDDocument()) {
                PDPage myPage = new PDPage();
                doc.addPage(myPage);

                try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

                    cont.beginText();

                    cont.setFont(PDType1Font.TIMES_ROMAN, 8);
                    cont.setLeading(14.5f);

                    cont.newLineAtOffset(25, 700);

                    for (Track track: user.get().getPurchasedTracks()) {
                        cont.showText("Id: " + track.getId().toString()
                                + " Title: " + track.getTitle()
                                + " Artist: " + track.getArtist()
                                + " Link: " + track.getLink()
                                + " Duration: " +track.getDuration()
                                + " Album: " + track.getAlbum()
                                + " Lyrics: " + track.getExplicit_lyrics());
                        cont.newLine();
                    }
                    cont.endText();
                }

                doc.save("src/main/resources/MyTracks.pdf");
            } catch (IOException e) {
                e.printStackTrace();
            }

            File file = new File("src/main/resources/MyTracks.pdf");
            Resource resource = new UrlResource(file.toPath().toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource.getFile().getAbsolutePath();
            }
        }
        return "I am a PDF reporter.";
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}
