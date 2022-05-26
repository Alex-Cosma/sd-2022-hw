package com.example.leaguecomp.champion;


import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.model.Item;
import com.example.leaguecomp.rune.model.Rune;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ReportPdfService {
    private final ChampionRepository championRepository;
    public ByteArrayInputStream export(Champion champion) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StringBuilder sb = new StringBuilder();
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc,page);
        contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
        contentStream.beginText();
        contentStream.newLineAtOffset(20,720);
        sb.append(champion.getName()).append(", ");
        contentStream.showText("ITEMS:");
        List<Item> items = champion.getBuild();
        for(Item item : items){
           contentStream.showText(item.getName());
           contentStream.newLine();
        }
        Set<Rune> runes = champion.getRunes();
        contentStream.showText("RUNES:");
        for(Rune rune : runes){
            contentStream.showText(rune.getName());
            contentStream.newLine();
        }
        contentStream.endText();
        contentStream.close();
        doc.save(baos);
        doc.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
