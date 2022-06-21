package com.app.banking.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CardUtil {

    private final static Integer ibanLength = 34;
    private final static Integer cardNumberLength = 16;
    private final static Random randomGenerator = new Random();

    @Value("${app.card.ibanPrefix}")
    private String ibanPrefix;

    private CardUtil() {

    }

    public String getIbanPrefix() {
        return ibanPrefix;
    }

    public Integer getIbanLength() {
        return ibanLength;
    }

    public String getRandomIban(String currency) {
        String result = ibanPrefix + currency + "CRT";
        while (result.length() < ibanLength) {
            result += (int) Math.floor(Math.random() * 10);
        }
        return result.toUpperCase();
    }

    public String getRandomCardNumber() {
        String result = "";
        for (int i = 0; i < 16; i++) {
            result += Math.abs(randomGenerator.nextInt()) % 10;
        }
        return result;
    }

    public boolean isIbanFromCurrentBank(String iban) {
        return iban.startsWith(ibanPrefix);
    }

}
