package com.app.banking.data.mongo.track;

import com.app.banking.data.mongo.entity.CardHistory;
import com.app.banking.data.mongo.entity.DepositHistory;
import com.app.banking.data.sql.entity.Deposit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

@Slf4j
@Component
public class DepositHistoryTracker {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Value("${app.mongodb.depositHistoryCollectionName}")
    private String collectionName;

    public void auditCreate(Deposit deposit) {
        DepositHistory depositHistory = getDepositFields(deposit);
        depositHistory.setPurpose("TRACK_CREATE");
        mongoTemplate.insert(depositHistory, collectionName);
    }

    public void auditUpdate(Deposit original, Deposit old) {
        DepositHistory depositHistory = new DepositHistory();
        depositHistory.setPurpose("TRACK_UPDATE");
        for (Field field : original.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (!Objects.equals(field.get(original), field.get(old))) {
                    depositHistory.getFieldsHistory().put(field.getName(), formatChanges(field.get(original), field.get(old)));
                }
            } catch (IllegalAccessException illegalAccessException) {
                log.error("Field not existing");
            }
        }
        mongoTemplate.insert(depositHistory, collectionName);
    }

    public void auditDelete(Deposit deposit) {
        DepositHistory cardHistory = getDepositFields(deposit);
        cardHistory.setPurpose("TRACK_DELETE");
        mongoTemplate.insert(cardHistory, collectionName);
    }

    private String formatChanges(Object original, Object changed) {
        return String.format("Field changed from: (%s) to (%s)", original.toString(), changed.toString());
    }

    private DepositHistory getDepositFields(Deposit deposit) {
        DepositHistory depositHistory = new DepositHistory();
        for (Field field : deposit.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                depositHistory.getFieldsHistory().put(field.getName(), Objects.toString(field.get(deposit)));
            } catch (IllegalAccessException illegalAccessException) {
                log.error("Field not existing");
            }
        }
        return depositHistory;
    }

}
