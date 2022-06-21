package com.app.banking.controller;

import com.app.banking.BaseControllerTest;
import com.app.banking.data.sql.entity.Card;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.repo.CardRepository;
import com.app.banking.data.sql.repo.UserRepository;
import com.app.banking.service.TransferDetails;
import com.app.banking.util.CardUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@DataJpaTest
@Sql(scripts = "/create-data.sql")
@Sql(scripts = "/cleanup-data.sql", executionPhase = AFTER_TEST_METHOD)
public class PayControllerTest {
    @Autowired
    protected CardRepository cardRepository;
    @Autowired
    protected UserRepository userRepository;
    private User user1, user2;
    private CardUtil cardUtil;

    @BeforeEach
    public void initUsers() {
        user1 = userRepository.findByUsername("tudoRElU").get();
        user2 = userRepository.findByUsername("tudorel_pentru_test").get();
    }

    @Test
    public void testTransfer() {
        Card card1 = Card.builder().currentAmount(200.0f).cvv("342").build();
        Card card2 = Card.builder().currentAmount(100.0f).cvv("123").build();
        cardRepository.save(card1);
        cardRepository.save(card2);
        RequestEntity<TransferDetails> details = RequestEntity.post("/transfer").body(new TransferDetails());
        try {
//            this.performPostWithRequestBody("/transfer", details);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
