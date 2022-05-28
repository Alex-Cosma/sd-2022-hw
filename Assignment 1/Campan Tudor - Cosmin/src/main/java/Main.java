import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.JDBConnectionWrapper;
import model.validator.UserValidator;
import repository.Action.ActionRepositoryMySQL;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main {
  public static void main(String[] args) {
    final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

    final RightsRolesRepositoryMySQL rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
    final UserRepositoryMySQL userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    final ClientRepositoryMySQL clientRepository = new ClientRepositoryMySQL(connection);
    final AuthenticationServiceMySQL authenticationService = new AuthenticationServiceMySQL(userRepository,
        rightsRolesRepository);
    final ActionRepositoryMySQL actionRepositoryMySQL= new ActionRepositoryMySQL(connection);
    final AccountRepositoryMySQL accountRepository= new AccountRepositoryMySQL(connection);

    final LoginView loginView = new LoginView();
    //final RightsRolesRepositoryMySQL rightsRolesRepositoryMySQL = new RightsRolesRepositoryMySQL(connection);
    final EmployeeView employeeView = new EmployeeView();
    final AdminView adminView =new AdminView();
    final EmployeeController employeeController=new EmployeeController(employeeView,clientRepository, accountRepository, loginView, actionRepositoryMySQL);
    System.out.println(userRepository.findByUsername("testadmin1@gmail.com"));
    //System.out.println(rightsRolesRepository.findRolesForUser((long) 2));
    employeeView.setVisible(false);

    final UserValidator userValidator = new UserValidator(userRepository);
    new AdminController(authenticationService,adminView,userRepository,rightsRolesRepository, actionRepositoryMySQL);
    new LoginController(loginView, authenticationService, userValidator,employeeView, adminView, userRepository, rightsRolesRepository);

  }
}
