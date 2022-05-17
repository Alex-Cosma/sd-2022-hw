package com.lab4.demo;

import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.question.model.dto.QuestionDTO;
import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import com.lab4.demo.quizzSession.model.QuizzSession;
import com.lab4.demo.review.model.Review;
import com.lab4.demo.review.model.dto.ReviewDTO;
import com.lab4.demo.user.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if(cls.equals(User.class)){
            supplier = TestCreationFactory::newUser;
        }else if (cls.equals(Question.class)) {
            supplier = TestCreationFactory::newQuestion;
        } else if (cls.equals(QuestionDTO.class)) {
            supplier = TestCreationFactory::newQuestionDTO;
        }else if(cls.equals(Quizz.class)){
            supplier = TestCreationFactory::newQuizz;
        }else if(cls.equals(QuizzDTO.class)) {
            supplier = TestCreationFactory::newQuizzDTO;
        }else if(cls.equals(Review.class)) {
            supplier = TestCreationFactory::newReview;
        }else if(cls.equals(QuizzSession.class)) {
            supplier = TestCreationFactory::newQuizzSession;
        }else if(cls.equals(ReviewDTO.class)) {
            supplier = TestCreationFactory::newReviewDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                        (T) finalSupplier.get()
                ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static User newUser() {
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }

    private static Question newQuestion() {
        return Question.builder()
                .id(randomLong())
                .statement(randomString())
                .category(randomString())
                .answers(Set.of(Answer.builder().answer(randomString()).correct(false).build()))
                .build();
    }

    private static QuestionDTO newQuestionDTO() {
        return QuestionDTO.builder()
                .id(randomLong())
                .statement(randomString())
                .category(randomString())
                .answers(Set.of(Answer.builder().answer(randomString()).correct(false).build()))
                .build();
    }

    private static Quizz newQuizz(){
        return Quizz.builder()
                .id(randomLong())
                .title(randomString())
                .description(randomString())
                .questions(null)
                .points(1)
                .build();
    }

    private static QuizzDTO newQuizzDTO(){
        return QuizzDTO.builder()
                .id(randomLong())
                .title(randomString())
                .description(randomString())
                .questions(null)
                .points(1)
                .build();
    }

    private static Review newReview(){
        return Review.builder()
                .id(randomLong())
                .text(randomString())
                .rating(randomString())
                .user(null)
                .build();
    }

    private static ReviewDTO newReviewDTO(){
        return ReviewDTO.builder()
                .id(randomLong())
                .text(randomString())
                .rating(randomString())
                .reviewer(null)
                .build();
    }

    private static QuizzSession newQuizzSession() {
        return QuizzSession.builder().id(randomLong()).score(randomBoundedInt(10)).build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 65; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String encodePassword(String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}