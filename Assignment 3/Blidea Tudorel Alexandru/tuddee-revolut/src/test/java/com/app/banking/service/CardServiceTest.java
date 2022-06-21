package com.app.banking.service;

import com.app.banking.data.dto.mapper.CardMapper;
import com.app.banking.data.dto.mapper.CardMapperImpl;
import com.app.banking.data.dto.model.CardDto;
import com.app.banking.data.mongo.track.CardHistoryTracker;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.banking.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    private CardMapper cardMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CardTypeRepository cardTypeRepository;

    @Mock
    private CardStatusRepository cardStatusRepository;

    @Mock
    private CardHistoryTracker cardHistoryTracker;


    @BeforeEach
    void setUp() {
        cardMapper = new CardMapperImpl();
        MockitoAnnotations.openMocks(this);
        cardService = new CardService(cardRepository, cardMapper, userRepository, cardTypeRepository, cardStatusRepository, cardHistoryTracker);
    }

    @Test
    void testFindAll() {
        List<Card> cards = new ArrayList<>();
        int noCards = 10;
        for (int i = 0; i < noCards; i++) {
            cards.add(Card.builder()
                    .owner(new User())
                    .cardType(new CardType())
                    .currentAmount(randomFloat())
                    .cvv(randomString().substring(0, 3))
                    .number(randomString())
                    .expirationDate(getRandomDate())
                    .cardStatus(new CardStatus())
                    .build());
        }

        when(cardRepository.findAll()).thenReturn(cards);

        List<CardDto> all = cardService.findAll();

        Assertions.assertEquals(noCards, all.size());
    }

    @Test
    void testAllCardsByOwner() {
        Integer id = randomInteger();
        int noCards = 10;
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < noCards; i++) {
            cards.add(Card.builder()
                    .owner(User.builder().id(id).build())
                    .cardType(new CardType())
                    .currentAmount(randomFloat())
                    .cvv(randomString().substring(0, 3))
                    .number(randomString())
                    .expirationDate(getRandomDate())
                    .cardStatus(new CardStatus())
                    .build());
        }

        when(cardRepository.findAllByOwnerId(id)).thenReturn(cards);

        List<CardDto> foundCards = cardService.allCardsByOwner(randomString());
        Assertions.assertEquals(noCards, foundCards.size());
    }


//    @Test // TODO
    void testAddCard() {
        User user = buildUser();
        CardType cardType = buildCardType();
        CardStatus cardStatus = buildCardStatus();


        Card card = Card.builder()
                .owner(user)
                .cardType(cardType)
                .currentAmount(randomFloat())
                .cvv(randomString().substring(0, 3))
                .number(randomString())
                .expirationDate(getRandomDate())
                .cardStatus(cardStatus)
                .build();
        CardDto cardDto = cardMapper.cardToCardDto(card);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(cardTypeRepository.findByNameLike(any())).thenReturn(Optional.of(cardType));
        when(cardStatusRepository.findByNameLike(any())).thenReturn(Optional.of(cardStatus));
        when(cardRepository.save(any())).thenReturn(card);

        CardDto savedCardDto = cardService.addCard(user.getUsername(), cardDto);
        assertEquals(cardDto, savedCardDto);
    }

//    @Test // TODO
    void testUpdateCard() {
        Card card = Card.builder()
                .owner(buildUser())
                .cardType(buildCardType())
                .currentAmount(randomFloat())
                .cvv(randomString().substring(0, 3))
                .number(randomString())
                .expirationDate(getRandomDate())
                .cardStatus(buildCardStatus())
                .build();
        CardDto cardDto = cardMapper.cardToCardDto(card);

        when(cardRepository.save(any())).thenReturn(card);

        CardDto savedCardDto = cardService.update(randomInteger(), cardDto);
        assertEquals(cardDto, savedCardDto);
    }

    @Test
    void delete() {
        Integer id = randomInteger();
        Card card = Card.builder()
                .id(id)
                .owner(buildUser())
                .cardType(buildCardType())
                .currentAmount(randomFloat())
                .cvv(randomString().substring(0, 3))
                .number(randomString())
                .expirationDate(getRandomDate())
                .cardStatus(buildCardStatus())
                .build();

        cardRepository.save(card);

        doNothing().when(cardRepository).deleteById(id);

        cardService.delete(id);

        verify(cardRepository, times(1)).deleteById(id);
    }


    private User buildUser() {
        String email = "email@employee.com";
        //String password = "Abcdefg1234!";
        return User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build();
    }

    private CardType buildCardType() {
        return CardType.builder()
                .name("GOLD")
                .description(randomString())
                .maxWithdrawal(randomInteger())
                .cashbackPercent(randomFloat())
                .build();
    }

    private CardStatus buildCardStatus() {
        return CardStatus.builder()
                .name("FUNCTIONAL")
                .description(randomString())
                .build();
    }

}
