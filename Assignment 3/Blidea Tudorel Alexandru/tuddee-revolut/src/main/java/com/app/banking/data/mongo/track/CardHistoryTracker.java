package com.app.banking.data.mongo.track;

import com.app.banking.data.mongo.entity.CardHistory;
import com.app.banking.data.mongo.entity.UserHistory;
import com.app.banking.data.sql.entity.Card;
import com.app.banking.data.sql.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

@Slf4j
@Component
public class CardHistoryTracker {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Value("${app.mongodb.cardHistoryCollectionName}")
    private String collectionName;

    public void auditCreate(Card card) {
        CardHistory cardHistory = getCardFields(card);
        cardHistory.setPurpose("TRACK_CREATE");
        mongoTemplate.insert(cardHistory, collectionName);
    }

    public void auditUpdate(Card original, Card old) {
        CardHistory cardHistory = new CardHistory();
        cardHistory.setPurpose("TRACK_UPDATE");
        for (Field field : original.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (!Objects.equals(field.get(original), field.get(old))) {
                    cardHistory.getFieldsHistory().put(field.getName(), formatChanges(field.get(original), field.get(old)));
                }
            } catch (IllegalAccessException illegalAccessException) {
                log.error("Field not existing");
            }
        }
        mongoTemplate.insert(cardHistory, collectionName);
    }

    public void auditDelete(Card card) {
        CardHistory cardHistory = getCardFields(card);
        cardHistory.setPurpose("TRACK_DELETE");
        mongoTemplate.insert(cardHistory, collectionName);
    }

    private String formatChanges(Object original, Object changed) {
        return String.format("Field changed from: (%s) to (%s)", original.toString(), changed.toString());
    }

    private CardHistory getCardFields(Card card) {
        CardHistory cardHistory = new CardHistory();
        for (Field field : card.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                cardHistory.getFieldsHistory().put(field.getName(), Objects.toString(field.get(card)));
            } catch (IllegalAccessException illegalAccessException) {
                log.error("Field not existing");
            }
        }
        return cardHistory;
    }

}
