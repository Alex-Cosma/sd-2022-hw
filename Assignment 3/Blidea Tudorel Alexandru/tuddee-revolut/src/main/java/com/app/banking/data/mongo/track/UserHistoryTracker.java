package com.app.banking.data.mongo.track;

import com.app.banking.data.mongo.entity.UserHistory;
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
public class UserHistoryTracker {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Value("${app.mongodb.userHistoryCollectionName}")
    private String collectionName;

    public void auditCreate(User user) {
        UserHistory userHistory = getUserFields(user);
        userHistory.setPurpose("TRACK_CREATE");
        mongoTemplate.insert(userHistory, collectionName);
    }

    public void auditUpdate(User original, User old) {
        UserHistory userHistory = new UserHistory();
        userHistory.setPurpose("TRACK_UPDATE");
        for (Field field : original.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (!Objects.equals(field.get(original), field.get(old))) {
                    userHistory.getFieldsHistory().put(field.getName(), formatChanges(field.get(original), field.get(old)));
                }
            } catch (IllegalAccessException illegalAccessException) {
                log.error("Field not existing");
            }
        }
        mongoTemplate.insert(userHistory, collectionName);
    }

    public void auditDelete(User user) {
        UserHistory userHistory = getUserFields(user);
        userHistory.setPurpose("TRACK_DELETE");
        mongoTemplate.insert(userHistory, collectionName);
    }

    private String formatChanges(Object original, Object changed) {
        return String.format("Field changed from: (%s) to (%s)", original.toString(), changed.toString());
    }

    private UserHistory getUserFields(User user) {
        UserHistory userHistory = new UserHistory();
        for (Field field : user.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                userHistory.getFieldsHistory().put(field.getName(), Objects.toString(field.get(user)));
            } catch (IllegalAccessException illegalAccessException) {
                log.error("Field not existing");
            }
        }
        return userHistory;
    }

}
