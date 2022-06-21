package com.app.banking.learning;

import com.app.banking.TestHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.app.banking.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class RestTemplateLearningTest {

    private static final RestTemplate restTemplate = getBasicRestTemplateForTest();
    private static final ParameterizedTypeReference<Map<String, String>> MAP_REFERENCE = new ParameterizedTypeReference<Map<String, String>>() {
    };
    private static final Integer STRING_SIZE = 30;
    private static final HttpHeaders headers = new HttpHeaders();

    static {
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
    }

    private String baseUrlLearnRequests = "http://localhost:5523/learnrequests";

    @RepeatedTest(10)
    public void testGetRequestDeserializeObject() throws IllegalAccessException, CloneNotSupportedException {
        String toAdd = getRandomString(STRING_SIZE);
        RequestTestClass obj = getRandomRequestTestObject();
        ResponseEntity<RequestTestClass> result = restTemplate.getForEntity(getGETUrl(toAdd, obj), RequestTestClass.class);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(obj.computeWithAddedFields(toAdd), result.getBody());
    }

    @RepeatedTest(10)
    public void testGetRequestDeserializeObject_getMap() throws IllegalAccessException, CloneNotSupportedException {
        String toAdd = getRandomString(STRING_SIZE);
        RequestTestClass obj = getRandomRequestTestObject();
        ResponseEntity<Map<String, String>> result = restTemplate.exchange(getGETUrl(toAdd, obj), HttpMethod.GET, null, MAP_REFERENCE);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        Map<String, String> expectedResult = obj.computeWithAddedFields(toAdd).computePlainObjectMap();
        assertFalse(expectedResult.isEmpty());
        assertEquals(expectedResult, result.getBody());
    }

    @RepeatedTest(10)
    public void testGetRequestDeserializeObject_exchange() throws IllegalAccessException, CloneNotSupportedException {
        String toAdd = getRandomString(STRING_SIZE);
        RequestTestClass obj = getRandomRequestTestObject();
        ResponseEntity<RequestTestClass> result = restTemplate.exchange(getGETUrl(toAdd, obj), HttpMethod.GET, null, RequestTestClass.class);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(obj.computeWithAddedFields(toAdd), result.getBody());
    }

    @RepeatedTest(10)
    public void testPostRequestDeserializeObject() throws CloneNotSupportedException, IllegalAccessException {
        String toAdd = getRandomString(STRING_SIZE);
        RequestTestClass obj = getRandomRequestTestObject();
        HttpEntity<RequestTestClass> request = new HttpEntity<>(obj, headers);
        ResponseEntity<RequestTestClass> result = restTemplate.postForEntity(getPOSTUrl(toAdd), request, RequestTestClass.class);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(obj.computeWithAddedFields(toAdd), result.getBody());
    }

    @RepeatedTest(10)
    public void testPostRequestDeserializeObject_exchange() throws CloneNotSupportedException, IllegalAccessException {
        String toAdd = getRandomString(STRING_SIZE);
        RequestTestClass obj = getRandomRequestTestObject();
        HttpEntity<RequestTestClass> request = new HttpEntity<>(obj, headers);
        ResponseEntity<RequestTestClass> result = restTemplate.exchange(getPOSTUrl(toAdd), HttpMethod.POST, request, RequestTestClass.class);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(obj.computeWithAddedFields(toAdd), result.getBody());
    }

    @RepeatedTest(10)
    public void testPutRequestDeserializeObject() throws CloneNotSupportedException, IllegalAccessException {
        RequestTestClass obj = getRandomRequestTestObject();
        String fieldName = obj.sampleFieldName(), newFieldValue = getRandomString(STRING_SIZE);
        HttpEntity<RequestTestClass> httpEntity = new HttpEntity<>(obj, headers);
        ResponseEntity<RequestTestClass> result = restTemplate.exchange(getPUTUrl(fieldName, newFieldValue), HttpMethod.PUT, httpEntity, RequestTestClass.class);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(obj.computeWithChangedField(fieldName, newFieldValue), result.getBody());
    }

    @RepeatedTest(10)
    public void testDeleteDeserializeObject() throws CloneNotSupportedException, IllegalAccessException {
        RequestTestClass obj = getRandomRequestTestObject();
        String fieldName = obj.sampleFieldName();
        HttpEntity<RequestTestClass> httpEntity = new HttpEntity<>(obj, headers);
        ResponseEntity<RequestTestClass> result = restTemplate.exchange(getDELETEUrl(fieldName), HttpMethod.DELETE, httpEntity, RequestTestClass.class);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(obj.computeWithChangedField(fieldName, null), result.getBody());
    }

    private String getGETUrl(String toAdd, RequestTestClass obj) throws IllegalAccessException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(baseUrlLearnRequests))
                .pathSegment(toAdd);
        for (Map.Entry<String, String> entry : obj.computePlainObjectMap().entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        return builder.encode()
                .build()
                .toString();
    }

    private String getPOSTUrl(String toAdd) {
        return UriComponentsBuilder.fromUri(URI.create(baseUrlLearnRequests))
                .pathSegment(toAdd)
                .encode()
                .build()
                .toString();
    }

    private String getPUTUrl(String fieldName, String newValue) {
        return UriComponentsBuilder.fromUri(URI.create(baseUrlLearnRequests))
                .pathSegment(fieldName)
                .pathSegment(newValue)
                .encode()
                .build()
                .toString();
    }

    private String getDELETEUrl(String fieldName) {
        return UriComponentsBuilder.fromUri(URI.create(baseUrlLearnRequests))
                .pathSegment(fieldName)
                .encode()
                .build()
                .toString();
    }

    private RequestTestClass getRandomRequestTestObject() {
        return RequestTestClass.of(getRandomString(STRING_SIZE), getRandomString(STRING_SIZE),
                getRandomString(STRING_SIZE), getRandomString(STRING_SIZE));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RequestTestClass implements Cloneable {

        private String theBestFieldOrMaybeNot;
        private String anotherField;
        private String helloField;
        private String favoriteSong;

        public static RequestTestClass of(String aField, String anotherField, String helloField, String favoriteSong) {
            return new RequestTestClass(aField, anotherField, helloField, favoriteSong);
        }

        public Map<String, String> computePlainObjectMap() throws IllegalAccessException {
            Map<String, String> result = new HashMap<>();
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                result.put(field.getName(), field.get(this).toString());
            }
            return result;
        }

        public RequestTestClass computeWithAddedFields(String value) throws CloneNotSupportedException, IllegalAccessException {
            RequestTestClass result = clone();
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(result, field.get(this) + value);
            }
            return result;
        }

        public RequestTestClass computeWithChangedField(String fieldName, String newValue) throws CloneNotSupportedException, IllegalAccessException {
            RequestTestClass result = clone();
            Stream.of(this.getClass().getDeclaredFields())
                    .filter(field -> field.getName().equals(fieldName))
                    .findAny()
                    .ifPresent(field -> {
                        field.setAccessible(true);
                        try {
                            field.set(result, newValue);
                        } catch (IllegalAccessException e) {
                        }
                    });
            return result;
        }

        public String sampleFieldName() {
            Field[] fields = this.getClass().getDeclaredFields();
            int chosenFieldIndex = TestHelper.getRandomIntegerBetween(0, fields.length - 1);
            return fields[chosenFieldIndex].getName();
        }

        @Override
        protected RequestTestClass clone() throws CloneNotSupportedException {
            RequestTestClass result = new RequestTestClass();
            result.theBestFieldOrMaybeNot = theBestFieldOrMaybeNot;
            result.anotherField = anotherField;
            result.favoriteSong = favoriteSong;
            result.helloField = helloField;
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RequestTestClass that = (RequestTestClass) o;

            if (!Objects.equals(theBestFieldOrMaybeNot, that.theBestFieldOrMaybeNot)) return false;
            if (!Objects.equals(anotherField, that.anotherField)) return false;
            if (!Objects.equals(helloField, that.helloField)) return false;
            return Objects.equals(favoriteSong, that.favoriteSong);
        }

        @Override
        public int hashCode() {
            return Objects.hash(theBestFieldOrMaybeNot, anotherField, helloField, favoriteSong);
        }

    }


}
