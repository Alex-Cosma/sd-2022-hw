package com.example.backend;

import java.time.LocalDateTime;
import java.util.Random;

public class EntitiesMockerService {

    private static Random random = new Random();

    public static String randomString() {

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        return random
                .ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        random.nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return random.nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return random.nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return random.nextInt(upperBound);
    }

    public static Double randomDouble() {
        return random.nextDouble();
    }

    public static LocalDateTime randomDate() {
        return random
                .nextLong() % 2 == 0 ? LocalDateTime.now() : LocalDateTime.now()
                .minusDays(random.nextInt(365));
    }
}
