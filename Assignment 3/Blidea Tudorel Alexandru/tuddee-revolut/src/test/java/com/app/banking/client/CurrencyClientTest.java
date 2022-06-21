package com.app.banking.client;

import com.app.banking.TestHelper;
import com.app.banking.config.client.CurrencyClientConfiguration;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyClientTest {

    private static final CurrencyClientConfiguration testClientConfiguration = new CurrencyClientConfiguration();
    private static final CurrencyClient currencyClient = new CurrencyClient(TestHelper.getBasicRestTemplateForTest(), testClientConfiguration);

    static {
        testClientConfiguration.setBaseUrl("http://localhost:3070/rates");
        testClientConfiguration.setLatestRatesEndpoint("latest");
        testClientConfiguration.setAvailableCurrenciesEndpoint("availableCurrencies");
    }

    @RepeatedTest(10)
    void getCurrencies() {
        Map<String, Double> currencies = currencyClient.getCurrencies();
        assertNotNull(currencies);
    }

    @RepeatedTest(10)
    void getAvailableCurrencies() {
        List<String> availableCurrencies = currencyClient.getAvailableCurrencies();
        assertNotNull(availableCurrencies);
    }

    @RepeatedTest(10)
    void getCurrency() {
        Map<String, Double> currencies = currencyClient.getCurrencies();
        assertNotNull(currencies);
        List<String> availableCurrencies = currencyClient.getAvailableCurrencies();
        assertNotNull(availableCurrencies);
        if (!ObjectUtils.isEmpty(currencies)) {
            if (!ObjectUtils.isEmpty(availableCurrencies)) {
                assertEquals(availableCurrencies.size(), currencies.size());
                for (String currency : availableCurrencies) {
                    assertNotNull(currencies.get(currency));
                }
            } else {
                /* Thanos meme */
                fail("Unpossible");
            }
        }
    }

}