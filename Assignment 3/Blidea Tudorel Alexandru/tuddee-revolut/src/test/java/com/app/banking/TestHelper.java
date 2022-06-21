package com.app.banking;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;

public class TestHelper {

    private static final Random RANDOM_GENERATOR = new Random();

    private TestHelper() {

    }

    public static Long getRandomLong() {
        return RANDOM_GENERATOR.nextLong();
    }

    public static Double getRandomDouble(double li, double ls) {
        return RANDOM_GENERATOR.nextDouble() * (ls - li) + li;
    }

    public static Integer getRandomInteger() {
        return RANDOM_GENERATOR.nextInt();
    }

    public static Integer getRandomIntegerBetween(Integer li, Integer ls) {
        if (li > ls) {
            int aux = li;
            li = ls;
            ls = aux;
        }
        // We need abs because generates negative numbers
        return Math.abs(RANDOM_GENERATOR.nextInt()) % (ls - li + 1) + li;
    }

    public static String getRandomString(int numberOfCharacters) {
        if (numberOfCharacters < 32) {
            return getUuidString().substring(0, numberOfCharacters);
        } else {
            int repetitions = numberOfCharacters / 32 + 1;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < repetitions; i++) {
                result.append(getUuidString());
            }
            return result.substring(0, numberOfCharacters);
        }
    }

    public static RestTemplate getBasicRestTemplateForTest() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(10000))
                .setReadTimeout(Duration.ofMillis(10000))
                .build();
    }

    public static String getUuidString() {
        return UUID.randomUUID().toString();
    }

}
