package com.app.banking.service;

import com.app.banking.data.dto.model.UserReceivedMoneyStatistics;
import com.app.banking.data.dto.model.UserTransferStatistics;
import com.app.banking.data.sql.entity.Card;
import com.app.banking.data.sql.entity.Transfer;
import com.app.banking.data.sql.entity.enums.TransferStatus;
import com.app.banking.data.sql.repo.CardRepository;
import com.app.banking.data.sql.repo.TransferRepository;
import com.app.banking.exception.ErrorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStatisticsService {

    private final CardRepository cardRepository;
    private final TransferRepository transferRepository;

    public UserTransferStatistics getMonthlyTransferStatistic(String username, String cardNumber) {
        Card card = getValidatedCard(username, cardNumber);
        List<Transfer> lastMonthTransfers = transferRepository.findAllByStartedOnBeforeAndFrom(LocalDateTime.now().minusMonths(1), card.getIban());
        Long failedTransfers = lastMonthTransfers.stream()
                .filter(transfer -> transfer.getTransferStatus().equals(TransferStatus.FAILED))
                .count();
        Long doneTransfers = lastMonthTransfers.stream()
                .filter(transfer -> transfer.getTransferStatus().equals(TransferStatus.DONE))
                .count();
        Long waitingTransfers = lastMonthTransfers.stream()
                .filter(transfer -> transfer.getTransferStatus().equals(TransferStatus.WAITING))
                .count();;
        Double totalSentMoney = lastMonthTransfers.stream()
                .map(Transfer::getAmount)
                .reduce(0.0, Double::sum);
        Double moneySuccessfullySent = lastMonthTransfers.stream()
                .filter(transfer -> transfer.getTransferStatus().equals(TransferStatus.DONE))
                .map(Transfer::getAmount)
                .reduce(0.0, Double::sum);
        return UserTransferStatistics.builder()
                .failedTransfers(failedTransfers)
                .doneTransfers(doneTransfers)
                .waitingTransfers(waitingTransfers)
                .totalSentMoney(totalSentMoney)
                .moneySuccessfullySent(moneySuccessfullySent)
                .build();
    }

    public UserReceivedMoneyStatistics getMonthlyReceivedStatistic(String username, String cardNumber) {
        Card card = getValidatedCard(username, cardNumber);
        List<Transfer> lastMonthTransfers = transferRepository.findAllByStartedOnBeforeAndTo(LocalDateTime.now().minusMonths(1), card.getIban());
        Double totalReceivedMoney = lastMonthTransfers.stream()
                .map(Transfer::getAmount)
                .reduce(0.0, Double::sum);
        Integer totalTransfersReceived = lastMonthTransfers.size();
        Long totalDoneTransfersReceived = lastMonthTransfers.stream()
                .filter(transfer -> transfer.getTransferStatus().equals(TransferStatus.DONE))
                .count();
        Double totalSuccessfulReceivedMoney = lastMonthTransfers.stream()
                .filter(transfer -> transfer.getTransferStatus().equals(TransferStatus.DONE))
                .map(Transfer::getAmount)
                .reduce(0.0, Double::sum);
        return UserReceivedMoneyStatistics.builder()
                .totalReceivedMoney(totalReceivedMoney)
                .totalTransfersReceived(totalTransfersReceived)
                .totalDoneTransfersReceived(totalDoneTransfersReceived)
                .totalSuccessfulReceivedMoney(totalSuccessfulReceivedMoney)
                .build();
    }

    private Card getValidatedCard(String username, String cardNumber) {
        Card card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, "No card with given number"));
        if (!card.getOwner().getUsername().equalsIgnoreCase(username)) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, "This card does not belong to current user!");
        }
        return card;
    }

}
