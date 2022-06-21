package com.app.banking.service;


import com.app.banking.data.dto.model.PaymentDetails;
import com.app.banking.data.mongo.track.TransferHistoryTrack;
import com.app.banking.data.sql.entity.Business;
import com.app.banking.data.sql.entity.Card;
import com.app.banking.data.sql.entity.Transfer;
import com.app.banking.data.sql.entity.enums.TransferStatus;
import com.app.banking.data.sql.repo.BusinessRepository;
import com.app.banking.data.sql.repo.CardRepository;
import com.app.banking.data.sql.repo.TransferRepository;
import com.app.banking.exception.ErrorFactory;
import com.app.banking.util.CardUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PayService {

    private final CardRepository cardRepository;
    private final BusinessRepository businessRepository;
    private final CardUtil cardUtil;
    private final TransferRepository transferRepository;
    private final TransferHistoryTrack transferHistoryTrack;

    @Transactional
    public void payToBusiness(String username, PaymentDetails paymentDetails) {
        Card card = cardRepository.findByNumberAndCvv(paymentDetails.getCardNumber(), paymentDetails.getCvv())
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, "Not existing card!"));
        if (!card.getOwner().getUsername().equalsIgnoreCase(username)) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, "Wrong card owner!");
        }
        // You should have enough funds
        if (card.getCurrentAmount() < paymentDetails.getAmount()) {
            throw ErrorFactory.getError(HttpStatus.PAYMENT_REQUIRED, "Insufficient Funds!");
        }
        // and do not exceed the maximum withdrawal (even with the cashback)
        Float toPayAmount = paymentDetails.getAmount() * (1 - card.getCardType().getCashbackPercent());
        if (card.getCardType().getMaxWithdrawal() < toPayAmount) {
            throw ErrorFactory.getError(HttpStatus.I_AM_A_TEAPOT, "You have excedded your maximum withdrawal!");
        }
        Business business = businessRepository.findByCif(paymentDetails.getBusinessCIF())
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, "Requested business not found!"));
        business.setBalance(business.getBalance() + toPayAmount);
        card.setCurrentAmount(card.getCurrentAmount() - toPayAmount);
    }

    @Transactional
    public void makeTransfer(String username, TransferDetails transferDetails) {
        validateTransfer(username, transferDetails);
        Card ownerCard = cardRepository.findByNumber(transferDetails.getCardNumber()).get();
        Transfer.TransferBuilder transfer = Transfer.builder()
                .amount(transferDetails.getAmount())
                .from(ownerCard.getIban())
                .startedOn(LocalDateTime.now())
                .remainingAttempts(Short.valueOf("16"))
                .to(transferDetails.getIbanToTransfer());
        ownerCard.setCurrentAmount((float) (ownerCard.getCurrentAmount() - transferDetails.getAmount()));
        if (cardUtil.isIbanFromCurrentBank(transferDetails.getIbanToTransfer())) {
            // Instant transfer
            if (cardRepository.findByIban(transferDetails.getIbanToTransfer()).isEmpty()) {
                throw ErrorFactory.getError(HttpStatus.NOT_FOUND, "Iban to transfer seems to be from our bank but there is no account with this iban!");
            }
            Card destinationCard = cardRepository.findByIban(transferDetails.getIbanToTransfer()).get();
            destinationCard.setCurrentAmount((float) (destinationCard.getCurrentAmount() + transferDetails.getAmount()));
//            transferRepository.save(transfer.transferStatus(TransferStatus.DONE).doneOn(LocalDateTime.now()).build());
            transferHistoryTrack.internTransferAudit(transferDetails);
        } else {
            // it should wait...
            transferRepository.save(transfer.transferStatus(TransferStatus.WAITING).build());
        }
    }

    // More verbose for displaying errors in front-end and also for debugging
    private void validateTransfer(String username, TransferDetails transferDetails) {
        Card card = cardRepository.findByNumber(transferDetails.getCardNumber())
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, "Not found any card with given number"));
        if (!card.getOwner().getUsername().equalsIgnoreCase(username)) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, "Wrong card owner!");
        }
        if (card.getCurrentAmount() < transferDetails.getAmount()) {
            throw ErrorFactory.getError(HttpStatus.PAYMENT_REQUIRED, "Insufficient Funds!");
        }
    }

}
