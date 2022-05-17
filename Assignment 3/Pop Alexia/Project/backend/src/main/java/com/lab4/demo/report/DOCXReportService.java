package com.lab4.demo.report;

import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.lab4.demo.report.ReportType.DOCX;

@Service
public class DOCXReportService implements ReportService {
    @Override
    public String export(List<QuizzSessionDTO> quizzSessions, List<QuizzDTO> quizzes) {
        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph par1 = doc.createParagraph();
        par1.setAlignment(ParagraphAlignment.CENTER);
        par1.setWordWrapped(true);
        XWPFRun r1 = par1.createRun();
        r1.setText("Quizz History Report");
        r1.setBold(true);
        r1.addCarriageReturn();

        XWPFParagraph par2 = doc.createParagraph();
        par2.setAlignment(ParagraphAlignment.LEFT);
        par2.setWordWrapped(true);
        XWPFRun r2 = par2.createRun();
        Iterator<QuizzSessionDTO> quizzSessionIterator = quizzSessions.iterator();
        Iterator<QuizzDTO> quizzIterator = quizzes.iterator();

        String buildPar ="";
        while(quizzSessionIterator.hasNext() && quizzIterator.hasNext()) {
            QuizzSessionDTO quizzSession = quizzSessionIterator.next();
            QuizzDTO quizz = quizzIterator.next();

           //buildPar = buildPar.concat("Quizz title: "+quizz.getTitle());
            r2.setText("Quizz title : " + quizz.getTitle());
            r2.addCarriageReturn();
            for(Question question : quizz.getQuestions()) {
                //buildPar = buildPar.concat("  Question : "+ question.getStatement() +"\n");
                r2.setText("  Question : " + question.getStatement());
                r2.addCarriageReturn();
                for(Answer answer : question.getAnswers()) {
                    //buildPar = buildPar.concat("   Answer : " + answer.getAnswer() +"\n");
                    r2.setText("   Answer : " + answer.getAnswer());
                    r2.addCarriageReturn();
                    //buildPar = buildPar.concat("   What was the answer: " + answer.isCorrect() +"\n");
                    r2.setText("   What was the answer : " + answer.isCorrect());
                    r2.addCarriageReturn();
                    //buildPar = buildPar.concat("   What you picked: " + quizzSession.getAnswerSequence().stream().filter(a -> a.getAnswer().equals(answer.getAnswer())).findFirst().get().isCorrect() +"\n");
                    r2.setText("   What you picked : " + quizzSession.getAnswerSequence().stream().filter(a->a.getAnswer().equals(answer.getAnswer())).findFirst().get().isCorrect());
                    r2.addCarriageReturn();
                }
            }
            r2.setText(quizzSession.getScore() + "/" + quizz.getQuestions().size());
            r2.addCarriageReturn();
            //buildPar = buildPar.concat(quizzSession.getScore() + " / " + quizz.getQuestions().size() + "\n");
        }
        //r2.setText(buildPar);
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream("src/report.docx");
            doc.write(fo);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        File file = new File("src/report.docx");
        Resource resource = null;
        try {
            resource = new UrlResource(file.toPath().toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource.getFile().getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "I am a DOCX reporter";
    }

    @Override
    public ReportType getType() {
        return DOCX;
    }
}
