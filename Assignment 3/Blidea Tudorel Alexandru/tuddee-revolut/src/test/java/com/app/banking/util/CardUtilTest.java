package com.app.banking.util;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardUtilTest {

    @Value("${app.card.ibanPrefix}")
    private String ibanPrefix;
    @Autowired
    private CardUtil cardUtil;

    @Test
    void getIbanPrefix() {
        assertEquals(ibanPrefix, cardUtil.getIbanPrefix());
    }

    @RepeatedTest(10)
    void getRandomIban() {
        List<String> currencies = List.of("EUR", "BTC", "RON", "USD", "XAU");
        for (String currency: currencies) {
            String generatedIban = cardUtil.getRandomIban(currency);
            assertTrue(generatedIban.startsWith(cardUtil.getIbanPrefix()));
            assertEquals(cardUtil.getIbanLength(), generatedIban.length());
        }
    }

    @RepeatedTest(10)
    void getRandomCardNumber() {
        String generatedCardNumber = cardUtil.getRandomCardNumber();
        assertNotNull(generatedCardNumber);
        assertTrue(generatedCardNumber.length() > 0);
    }

    @Test
    void getRandomIbann() {
        for (int i = 0; i < 20; i++) {
            for (String currency: List.of("EUR", "BTC", "RON", "USD", "XAU")) {
                System.out.println(cardUtil.getRandomIban("EUR"));
                System.out.println(cardUtil.getRandomCardNumber());
                System.out.print("\n");
            }
        }
    }

}