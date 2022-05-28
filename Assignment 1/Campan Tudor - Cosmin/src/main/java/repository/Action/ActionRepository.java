package repository.Action;

import model.Account;

import java.sql.SQLException;

public interface ActionRepository {
    boolean save(String action);
    String findAllActions() throws SQLException;
}
