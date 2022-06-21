package com.app.banking.scheduler;

import com.app.banking.data.dto.model.FromTransferDetails;
import com.app.banking.data.dto.model.InterbankingTransferDetails;
import com.app.banking.data.mongo.track.TransferHistoryTrack;
import com.app.banking.data.sql.entity.Card;
import com.app.banking.data.sql.entity.InterbankingDetail;
import com.app.banking.data.sql.entity.Transfer;
import com.app.banking.data.sql.entity.enums.TransferStatus;
import com.app.banking.data.sql.repo.CardRepository;
import com.app.banking.data.sql.repo.InterbankingDetailsRepository;
import com.app.banking.data.sql.repo.TransferRepository;
import com.app.banking.util.ServiceUtil;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransferScheduler {

    private final static InterbankingDetail nowhereInterbankingDetail = InterbankingDetail.builder()
            .bankName("NOWHERE")
            .ibanPrefix(Strings.repeat("-", 34))
            .feesToTransfer(0.0f)
            .endpointToCall("http://127.0.0.5")
            .build();

    private final TransferRepository transferRepository;
    private final CardRepository cardRepository;
    private final InterbankingDetailsRepository interbankingDetailsRepository;
    private final RestTemplate restTemplate;
    private final ServiceUtil serviceUtil;
    private final TransferHistoryTrack transferHistoryTrack;

    @Scheduled(cron = "00 00 11 * * MON-SAT") // Every Day from Monday to Saturday at 11:00
    public void makeInterbankingTransfers() {
        List<Transfer> transfers = transferRepository.findAllByTransferStatus(TransferStatus.WAITING);
        transfers.forEach(transfer -> {
            executeTransfer(transfer);
            if (transfer.getRemainingAttempts() < 1) {
                closeTransfer(transfer);
            }
            transferRepository.save(transfer);
        });
    }

    private void executeTransfer(Transfer transfer) {
        transfer.setRemainingAttempts((short) (transfer.getRemainingAttempts() - 1));
        Card fromCard = cardRepository.findByIban(transfer.getFrom()).get();
        InterbankingDetail interbankingDetail = getBankByIban(transfer.getTo());
        InterbankingTransferDetails transferDetails = InterbankingTransferDetails.builder()
                .bankName(interbankingDetail.getBankName())
                .iban(transfer.getTo())
                .amount(transfer.getAmount())
                .fromDetails(
                        FromTransferDetails.builder()
                                .firstName(fromCard.getOwner().getFirstName())
                                .lastName(fromCard.getOwner().getLastName())
                                .email(fromCard.getOwner().getEmail())
                                .iban(fromCard.getIban())
                                .build()
                ).build();
        transferHistoryTrack.externTransferAudit(transferDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("identifyToken", interbankingDetail.getIdentifyToken());
        headers.set("bankName", serviceUtil.getBankName());
        HttpEntity<InterbankingTransferDetails> httpEntity = new HttpEntity<>(transferDetails, headers);
        restTemplate.put(interbankingDetail.getEndpointToCall(), httpEntity);
    }

    private InterbankingDetail getBankByIban(String iban) {
        return interbankingDetailsRepository.findAll().stream()
                .filter(detail -> iban.startsWith(detail.getIbanPrefix()))
                .findAny().orElse(nowhereInterbankingDetail);
    }

    private void closeTransfer(Transfer transfer) {
        Card fromCard = cardRepository.findByIban(transfer.getFrom()).get();
        fromCard.setCurrentAmount((float) (fromCard.getCurrentAmount() + transfer.getAmount()));
        cardRepository.save(fromCard);
        transfer.setTransferStatus(TransferStatus.FAILED);
    }

}
