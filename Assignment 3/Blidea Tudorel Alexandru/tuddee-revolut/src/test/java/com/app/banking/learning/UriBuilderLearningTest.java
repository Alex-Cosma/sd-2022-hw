package com.app.banking.learning;

import com.app.banking.TestHelper;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UriBuilderLearningTest {

    private static final String WHITE_SPACE_ENCODING = "%20";
    private static final String ORIGINAL_PORT = String.valueOf(TestHelper.getRandomIntegerBetween(7, 60_000));
    private static final String NEXT_PORT = String.valueOf(TestHelper.getRandomIntegerBetween(7, 60_000));
    private static final String ORIGIN1 = String.format("http://localhost:%s", ORIGINAL_PORT);
    private static final String ORIGIN2 = "https://google.com";
    private static final String PATH1 = "path1";
    private static final String PATH2 = "path2";
    private static final Map<String, String> QUERY_PARAMS = Map.of("qp1", "val1", "qp2", "val2", "qp3", "val3", "qp4", "val with whitespaces");

    @RepeatedTest(10)
    public void simpleTestUriBuilder() {
        StringBuilder expected = new StringBuilder(String.format("%s/%s/%s?", ORIGIN1, PATH1, PATH2));
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(ORIGIN1)
                .pathSegment(PATH1)
                .pathSegment(PATH2);
        for (String key : QUERY_PARAMS.keySet()) {
            uriBuilder.queryParam(key, QUERY_PARAMS.get(key));
            expected.append(String.format("%s=%s&", key, encodedWhiteSpaces(QUERY_PARAMS.get(key))));
        }
        if (expected.toString().endsWith("&")) {
            expected.reverse().deleteCharAt(0).reverse();
        }
        String result = uriBuilder.encode()
                .build()
                .toString();
        assertEquals(expected.toString(), result);
    }

    @RepeatedTest(10)
    public void testWithChangedPortNumber() {
        StringBuilder expected = new StringBuilder(String.format("%s:%s/%s/%s?", ORIGIN2, NEXT_PORT, PATH2, PATH1));
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(ORIGIN2)
                .pathSegment(PATH2)
                .pathSegment(PATH1)
                .port(NEXT_PORT);
        for (String key : QUERY_PARAMS.keySet()) {
            uriBuilder.queryParam(key, QUERY_PARAMS.get(key));
            expected.append(String.format("%s=%s&", key, encodedWhiteSpaces(QUERY_PARAMS.get(key))));
        }
        if (expected.toString().endsWith("&")) {
            expected.reverse().deleteCharAt(0).reverse();
        }
        String result = uriBuilder.encode()
                .build()
                .toString();
        assertEquals(expected.toString(), result);
    }

    @RepeatedTest(10)
    public void testPortChanging() {
        StringBuilder expected = new StringBuilder(String.format("%s/%s/%s?", ORIGIN1.replace(ORIGINAL_PORT, NEXT_PORT), PATH2, PATH1));
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(ORIGIN1)
                .pathSegment(PATH2)
                .pathSegment(PATH1)
                .port(NEXT_PORT);
        for (String key : QUERY_PARAMS.keySet()) {
            uriBuilder.queryParam(key, QUERY_PARAMS.get(key));
            expected.append(String.format("%s=%s&", key, encodedWhiteSpaces(QUERY_PARAMS.get(key))));
        }
        if (expected.toString().endsWith("&")) {
            expected.reverse().deleteCharAt(0).reverse();
        }
        String result = uriBuilder.encode()
                .build()
                .toString();
        assertEquals(expected.toString(), result);
    }

    private String encodedWhiteSpaces(String original) {
        return original.replace(" ", WHITE_SPACE_ENCODING);
    }


}
