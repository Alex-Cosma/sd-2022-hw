package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.*;
import com.app.banking.data.sql.entity.enums.ECardStatus;
import com.app.banking.data.sql.entity.enums.ECardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.banking.TestCreationFactory.*;
import static com.app.banking.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CardRepositoryTest {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardTypeRepository cardTypeRepository;

    @Autowired
    private CardStatusRepository cardStatusRepository;

    @BeforeEach
    public void beforeEach() {

        userRepository.deleteAll();
        cardTypeRepository.deleteAll();
        cardStatusRepository.deleteAll();
        cardRepository.deleteAll();
    }

//    @Test // TODO
    public void testAddCard() {
        User userSaved = saveUser();
        CardType cardType = saveCardType();
        CardStatus cardStatus = saveCardStatus();

        Card cardSaved = cardRepository.save(Card.builder()
                .owner(userSaved)
                .cardType(cardType)
                .currentAmount(randomFloat())
                .cvv(randomString().substring(0, 3))
                .number(randomString())
                .expirationDate(getRandomDate())
                .cardStatus(cardStatus)
                .build());

        assertNotNull(cardSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            cardRepository.save(Card.builder()
                    .build());
        });
    }

//    @Test // TODO
    public void testFindAll(){
        User userSaved = saveUser();
        CardType cardType = saveCardType();
        CardStatus cardStatus = saveCardStatus();

        int noCards = 10;
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < noCards; i++) {
            cards.add(Card.builder()
                    .owner(userSaved)
                    .cardType(cardType)
                    .currentAmount(randomFloat())
                    .cvv(randomString().substring(0, 3))
                    .number(randomString())
                    .expirationDate(getRandomDate())
                    .cardStatus(cardStatus)
                    .build());
        }
        cardRepository.saveAll(cards);
        List<Card> all = cardRepository.findAll();
        assertEquals(cards.size(), all.size());
    }

//    @Test // TODO
    public void testFindById(){
        User userSaved = saveUser();
        CardType cardType = saveCardType();
        CardStatus cardStatus = saveCardStatus();
        Card cardSaved = cardRepository.save(Card.builder()
                .owner(userSaved)
                .cardType(cardType)
                .currentAmount(randomFloat())
                .cvv(randomString().substring(0, 3))
                .number(randomString())
                .expirationDate(getRandomDate())
                .cardStatus(cardStatus)
                .build());



        Optional<Card> result = cardRepository.findById(cardSaved.getId());
        assertTrue(result.isPresent());
        result.ifPresent(c -> assertEquals(c.getId(), cardSaved.getId()));
    }

//    @Test // TODO
    public void testAllCardsByOwner(){
        User userSaved = saveUser();
        CardType cardType = saveCardType();
        CardStatus cardStatus = saveCardStatus();

        int noCards = 10;
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < noCards; i++) {
            cards.add(Card.builder()
                    .owner(userSaved)
                    .cardType(cardType)
                    .currentAmount(randomFloat())
                    .cvv(randomString().substring(0, 3))
                    .number(randomString())
                    .expirationDate(getRandomDate())
                    .cardStatus(cardStatus)
                    .build());
        }
        cardRepository.saveAll(cards);

        List<Card> all = cardRepository.findAllByOwnerId(userSaved.getId());
        assertEquals(cards.size(), all.size());

    }

//    @Test // TODO
    public void testDeleteById(){
        User userSaved = saveUser();
        CardType cardType = saveCardType();
        CardStatus cardStatus = saveCardStatus();
        Card cardSaved = cardRepository.save(Card.builder()
                .owner(userSaved)
                .cardType(cardType)
                .currentAmount(randomFloat())
                .cvv(randomString().substring(0, 3))
                .number(randomString())
                .expirationDate(getRandomDate())
                .cardStatus(cardStatus)
                .build());

        cardRepository.deleteById(cardSaved.getId());

        Optional<Card> result = cardRepository.findById(cardSaved.getId());
        assertTrue(result.isEmpty());
    }


    private User saveUser(){
        String email = "email@employee.com";
        //String password = "Abcdefg1234!";
        return userRepository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build());
    }

    private CardType saveCardType(){
        return cardTypeRepository.save(CardType.builder()
                .name("Gold")
                .description(randomString())
                .maxWithdrawal(randomInteger())
                .cashbackPercent(randomFloat())
                .build());
    }

    private CardStatus saveCardStatus(){
        return cardStatusRepository.save(CardStatus.builder()
                .name("FUNCTIONAL")
                .description(randomString())
                .build());
    }
}
