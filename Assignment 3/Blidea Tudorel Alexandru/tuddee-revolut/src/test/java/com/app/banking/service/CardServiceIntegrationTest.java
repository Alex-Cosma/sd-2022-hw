package com.app.banking.service;


import com.app.banking.data.dto.mapper.CardMapper;
import com.app.banking.data.dto.mapper.CardMapperImpl;
import com.app.banking.data.dto.model.CardDto;
import com.app.banking.data.sql.entity.Card;
import com.app.banking.data.sql.entity.CardStatus;
import com.app.banking.data.sql.entity.CardType;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.entity.enums.ECardStatus;
import com.app.banking.data.sql.entity.enums.ECardType;
import com.app.banking.data.sql.repo.CardRepository;
import com.app.banking.data.sql.repo.CardStatusRepository;
import com.app.banking.data.sql.repo.CardTypeRepository;
import com.app.banking.data.sql.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.app.banking.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CardServiceIntegrationTest {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardTypeRepository cardTypeRepository;

    @Autowired
    private CardStatusRepository cardStatusRepository;

    private CardMapper cardMapper;

    @BeforeEach
    void setUp() {
        cardMapper = new CardMapperImpl();
        userRepository.deleteAll();

        cardTypeRepository.deleteAll();
        cardStatusRepository.deleteAll();
        cardRepository.deleteAll();
    }

//    @Test // TODO
    void testFindAll() {
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

        List<CardDto> all = cardService.findAll();

        assertEquals(noCards, all.size());
    }

//    @Test // TODO
    void testAllCardsByOwner(){
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

        List<CardDto> foundItems = cardService.allCardsByOwner(userSaved.getUsername());
        assertEquals(noCards, foundItems.size());
    }

//    @Test // TODO
    void testAddCard(){             //TODO
        User userSaved = saveUser();
        CardType cardType = saveCardType();
        CardStatus cardStatus = saveCardStatus();
        Card card = Card.builder()
                .owner(userSaved)
                .cardType(cardType)
                .currentAmount(randomFloat())
                .cvv(randomString().substring(0,3))
                .number(randomString())
                .expirationDate(getRandomDate())
                .cardStatus(cardStatus)
                .build();

        CardDto cardDto = cardMapper.cardToCardDto(card);

        CardDto savedCardDto = cardService.addCard(userSaved.getUsername(), cardDto);

        assertEquals(savedCardDto, cardDto);
    }



    private User saveUser(){

        //String password = "Abcdefg1234!";
        return userRepository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(randomString() + "@email.com")
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build());
    }

    private CardType saveCardType(){
        return cardTypeRepository.save(CardType.builder()
                .name("GOLD")
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
