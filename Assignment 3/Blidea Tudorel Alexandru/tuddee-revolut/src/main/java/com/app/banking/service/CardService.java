package com.app.banking.service;

import com.app.banking.data.dto.mapper.CardMapper;
import com.app.banking.data.dto.model.CardDto;
import com.app.banking.data.mongo.track.CardHistoryTracker;
import com.app.banking.data.sql.entity.Card;
import com.app.banking.data.sql.entity.CardStatus;
import com.app.banking.data.sql.entity.CardType;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.entity.enums.ECardStatus;
import com.app.banking.data.sql.repo.CardRepository;
import com.app.banking.data.sql.repo.CardStatusRepository;
import com.app.banking.data.sql.repo.CardTypeRepository;
import com.app.banking.data.sql.repo.UserRepository;
import com.app.banking.exception.ErrorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;
    private final CardTypeRepository cardTypeRepository;
    private final CardStatusRepository cardStatusRepository;
    private final CardHistoryTracker cardHistoryTracker;

    public CardDto findById(Integer id) {       //TODO test
        return cardRepository.findById(id)
                .map(cardMapper::cardToCardDto)
                .orElseThrow(() -> new RuntimeException(format("Card with id %s not found", id)));
    }

    public List<CardDto> findAll() {
        return cardRepository.findAll().stream()
                .map(cardMapper::cardToCardDto)
                .collect(Collectors.toList());
    }

    public List<CardDto> allCardsByOwner(String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, "User with given credentials not found!"));
        return cardRepository.findAllByOwnerId(owner.getId()).stream()
                .map(cardMapper::cardToCardDto)
                .collect(Collectors.toList());
    }

    public CardDto addCard(String username, CardDto cardDto) {
        Optional<User> userOp = userRepository.findByUsername(username);
        Optional<CardType> cardTypeOp = cardTypeRepository.findByNameLike(cardDto.getCardType().getName());
        Optional<CardStatus> cardStatusOp = cardStatusRepository.findByNameLike(ECardStatus.NOT_ACTIVATED);

        Card card = cardMapper.cardDtoToCard(cardDto);

        userOp.ifPresent(card::setOwner);
        cardTypeOp.ifPresent(card::setCardType);
        cardStatusOp.ifPresent(card::setCardStatus);

        CardDto cardDtoAdded;

        try {
            Card addedCard = cardRepository.save(card);
            cardDtoAdded = cardMapper.cardToCardDto(addedCard);
            cardHistoryTracker.auditCreate(card);
        } catch (Exception e) {
            cardDtoAdded = null;
        }

        return cardDtoAdded;
    }

    public CardDto update(Integer id, CardDto cardDto) {
        Card original = cardRepository.findById(id)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, "Not Existing card"));
        Card updatedCard = cardMapper.cardDtoToCard(cardDto);
        updatedCard.setId(id);
        updatedCard = cardRepository.save(updatedCard);
        cardHistoryTracker.auditUpdate(original, updatedCard);
        return cardMapper.cardToCardDto(updatedCard);
    }

    public void delete(Integer id) {
        Card cardToDelete = cardRepository.findById(id).orElse(new Card());
        cardRepository.deleteById(id);
        cardHistoryTracker.auditDelete(cardToDelete);
    }

}
