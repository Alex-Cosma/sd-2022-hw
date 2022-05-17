package com.lab4.demo;

import com.lab4.demo.answer.AnswerRepository;
import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.question.QuestionRepository;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.quizz.QuizzRepository;
import com.lab4.demo.quizzSession.QuizzSessionRepository;
import com.lab4.demo.review.ReviewRepository;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final QuizzRepository quizzRepository;

    private final QuizzSessionRepository quizzSessionRepository;

    private final ReviewRepository reviewRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();
            quizzRepository.deleteAll();
            quizzSessionRepository.deleteAll();
            answerRepository.deleteAll();
            questionRepository.deleteAll();
            reviewRepository.deleteAll();
            roleRepository.deleteAll();

            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex1@email.com")
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of("CLIENT"))
                    .build());

            try {
                URL url = new URL("https://opentdb.com/api.php?amount=50&category=18&type=multiple");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == 200) {
                    Scanner scan = new Scanner(url.openStream());
                    Object object = new JSONParser(scan.nextLine()).parse();
                    HashMap<String, JSONArray> passedValues = (HashMap<String, JSONArray>) object;
                    Set<String> keys = passedValues.keySet();
                    keys.remove("response_code");
                    for (String key : keys) {
                        ArrayList<JSONObject> nested1= passedValues.get(key);
                        for(int i=0;i<nested1.size();i++){
                            String category="";
                            String question = "";
                            String correctAnswer ="";
                            ArrayList<String> incorrectAnswers = null;
                            HashMap<String, String> passedValues2 = (HashMap<String, String>) nested1.get(i);
                            for (Map.Entry<String, ?> mapTemp : passedValues2.entrySet()) {

                                if (mapTemp.getKey().equalsIgnoreCase("category")) {
                                    category = String.valueOf(mapTemp.getValue());
                                    System.out.println(category);
                                }
                                if(mapTemp.getKey().equalsIgnoreCase("question")) {
                                    question = String.valueOf(mapTemp.getValue());
                                    System.out.println(question);
                                }
                                if(mapTemp.getKey().equalsIgnoreCase("correct_answer")) {
                                    correctAnswer = (String) mapTemp.getValue();
                                    System.out.println(correctAnswer);
                                }
                                if(mapTemp.getKey().equalsIgnoreCase("incorrect_answers")) {
                                    incorrectAnswers = (ArrayList<String>) mapTemp.getValue();
                                    System.out.println(incorrectAnswers);
                                }
                            }
                            Question q= questionRepository.save(Question.builder().statement(question).category(category).answers(null).build());
                            Set<Answer> answers = new HashSet<>();
                            Answer answer = Answer.builder().answer(correctAnswer).correct(true).question(q).build();
                            answers.add(answer);
                            for(int j=0;j<incorrectAnswers.size();j++){
                                Answer a = Answer.builder().answer(incorrectAnswers.get(j)).correct(false).question(q).build();
                                answers.add(a);
                                answerRepository.save(a);
                            }
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
