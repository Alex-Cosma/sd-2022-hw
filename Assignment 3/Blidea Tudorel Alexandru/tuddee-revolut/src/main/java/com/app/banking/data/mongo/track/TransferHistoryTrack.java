package com.app.banking.data.mongo.track;

import com.app.banking.data.dto.model.InterbankingTransferDetails;
import com.app.banking.data.mongo.entity.TransferHistory;
import com.app.banking.service.TransferDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransferHistoryTrack {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Value("${app.mongodb.transferHistoryCollectionName}")
    private String collectionName;

    public void externTransferAudit(InterbankingTransferDetails transferDetails) {
        mongoTemplate.insert(
                TransferHistory.builder()
                    .purpose("EXTERN_TRANSFER")
                    .transferDetails(transferDetails)
                    .build(),
                collectionName);
    }

    public void internTransferAudit(TransferDetails transferDetails) {
        mongoTemplate.insert(
                TransferHistory.builder()
                        .purpose("INTERN_TRANSFER")
                        .internTransferDetails(transferDetails)
                        .build(),
                collectionName);
    }

}
