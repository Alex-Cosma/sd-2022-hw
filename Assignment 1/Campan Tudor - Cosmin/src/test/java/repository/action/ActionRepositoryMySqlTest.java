package repository.action;

import database.DBConnectionFactory;
import model.Client;
import model.builder.BookBuilder;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.Action.ActionRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActionRepositoryMySqlTest {
    private static ActionRepositoryMySQL actionRepositoryMySQL;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        actionRepositoryMySQL = new ActionRepositoryMySQL(connection);
    }
    @Test
    public void save() throws Exception {
        assertTrue(actionRepositoryMySQL.save("Test action"));
    }
    @Test
    public void find() throws Exception {
        assertFalse(actionRepositoryMySQL.findAllActions() == "");
        System.out.println(actionRepositoryMySQL.findAllActions());;
    }

}