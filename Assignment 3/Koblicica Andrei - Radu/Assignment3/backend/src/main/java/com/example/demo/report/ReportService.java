package com.example.demo.report;

import com.example.demo.movie.MovieRepository;
import com.example.demo.movie.MovieService;
import com.example.demo.movie.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final MovieService movieService;


    public ByteArrayInputStream exportReport() {
        List<MovieDTO> movies=movieService.findTopMovies();
        try{
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDFont font = PDType1Font.HELVETICA;
            PDPageContentStream content = new PDPageContentStream(document, page);
            content.setFont(font, 12);
            for(MovieDTO movie: movies){
                content.beginText();
                content.newLineAtOffset(100, 700 - 20f * movies.indexOf(movie));
                content.showText(movies.indexOf(movie)+1+". "+movie.getTitle()+" ("+movie.getYear() + ") - "+movie.getRating());
                content.endText();
            }
            content.close();
            document.save("TopMovies.pdf");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }




}
