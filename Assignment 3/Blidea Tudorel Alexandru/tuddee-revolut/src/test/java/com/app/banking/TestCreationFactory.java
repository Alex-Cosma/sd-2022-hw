package com.app.banking;

import com.app.banking.data.sql.entity.enums.EDepositType;
import com.app.banking.data.sql.entity.enums.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static com.app.banking.TestHelper.getRandomIntegerBetween;

public class TestCreationFactory {

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 12;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static LocalDate getRandomDate() {
        LocalDate now = LocalDate.now();
        return now.plusYears(getRandomIntegerBetween(1, 5));
    }

    public static LocalDateTime getRandomDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.plusSeconds(getRandomIntegerBetween(0, 60))
                .plusMinutes(getRandomIntegerBetween(0, 60))
                .plusHours(getRandomIntegerBetween(0, 24))
                .plusDays(getRandomIntegerBetween(0, 365))
                .plusYears(getRandomIntegerBetween(-1, 1));
    }

    public static Float randomFloat(){
        return new Random().nextFloat();
    }

    public static Integer randomInteger(){
        return new Random().nextInt();
    }

    public static UserRole getRandomRole() {
        List<UserRole> allRoles = UserRole.getListOfAllRoles();
        return allRoles.get((int) Math.floor(Math.random() * allRoles.size()));
    }

    public static String getRandomDepositType() {
        List<EDepositType> allDeposits = EDepositType.getDepositTypes();
        return allDeposits.get((int) Math.floor(Math.random() * allDeposits.size()));
    }

}
