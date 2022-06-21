package com.app.banking.service;

import com.app.banking.data.sql.entity.Card;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.repo.CardRepository;
import com.app.banking.data.sql.repo.UserRepository;
import com.app.banking.exception.ErrorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportDataService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public String getOwnCardsReport(String username) {
        User owner = userRepository.findByUsername(username).
                orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND));

        return cardRepository.findAllByOwnerId(owner.getId()).stream()
                .map(Card::getCsvLine)
                .collect(Collectors.joining("\n"));
    }

}
