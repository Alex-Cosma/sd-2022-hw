package com.lab4.demo.report;

import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {
    @Override
    public String export(List<QuizzSessionDTO> quizzSessions, List<QuizzDTO> quizzes) {
        try (PDDocument report = new PDDocument()) {
            PDPage page = new PDPage();
            report.addPage(page);

            try (PDPageContentStream cont = new PDPageContentStream(report, page)) {

                cont.beginText();
                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
                cont.setLeading(14.5f);
                cont.newLineAtOffset(25, 700);

                cont.showText("Quizz History Report");
                cont.newLine();

               Iterator<QuizzSessionDTO> quizzSessionIterator = quizzSessions.iterator();
                Iterator<QuizzDTO> quizzIterator = quizzes.iterator();

                while(quizzSessionIterator.hasNext() && quizzIterator.hasNext()) {
                    QuizzSessionDTO quizzSession = quizzSessionIterator.next();
                    QuizzDTO quizz = quizzIterator.next();

                    cont.showText("Quizz title: "+quizz.getTitle());
                    cont.newLine();

                    for(Question question : quizz.getQuestions()) {
                        cont.showText("  Question : "+ question.getStatement());
                        cont.newLine();
                        for(Answer answer : question.getAnswers()) {
                            cont.showText("   Answer : " + answer.getAnswer());
                            cont.newLine();
                            cont.showText("   What was the answer: " + answer.isCorrect());
                            cont.newLine();
                            cont.showText("   What you picked: " + quizzSession.getAnswerSequence().stream().filter(a -> a.getAnswer().equals(answer.getAnswer())).findFirst().get().isCorrect());
                            cont.newLine();
                        }
                    }
                    cont.showText(quizzSession.getScore() + " / " + quizz.getQuestions().size());
                    cont.newLine();
                }
                cont.endText();
                cont.close();
                report.save("src/report.pdf");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }

        File file = new File("src/report.pdf");
        Resource resource = null;
        try {
            resource = new UrlResource(file.toPath().toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource.getFile().getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "I am a PDF reporter";
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}
