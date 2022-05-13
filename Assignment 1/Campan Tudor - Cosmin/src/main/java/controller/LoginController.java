package controller;

import model.User;
import model.validator.UserValidator;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginController {

  private final LoginView loginView;
  private final AuthenticationService authenticationService;
  private final UserValidator userValidator;
  private final EmployeeView employeeView;
  private final AdminView adminView;
  private final UserRepositoryMySQL userRepository;
  private final RightsRolesRepositoryMySQL rightsRolesRepositoryMySQL;

  public LoginController(LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator, EmployeeView employeeView, AdminView adminView, UserRepositoryMySQL userRepository, RightsRolesRepositoryMySQL rightsRolesRepositoryMySQL) {
    this.loginView = loginView;
    this.authenticationService = authenticationService;
    this.userValidator = userValidator;
    this.employeeView =employeeView;
    this.adminView = adminView;
    this.userRepository = userRepository;
    this.rightsRolesRepositoryMySQL = rightsRolesRepositoryMySQL;

    this.loginView.addLoginButtonListener(new LoginButtonListener());
    this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    this.loginView.setVisible(true);
  }

  private class LoginButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getUsername();
      String password = loginView.getPassword();

      if(authenticationService.login(username, password)==null){
        JOptionPane.showMessageDialog(loginView.getContentPane(),"Invalid credentials");
      }
      else {
        System.out.println(username);
        User u1=userRepository.findByUsername(username);
//        System.out.println(u1);
        Long id=u1.getId();
//      System.out.println(id);
        if(rightsRolesRepositoryMySQL.findRolesForUser(id).size()!=0)
        {  adminView.setVisible(true);
          loginView.setVisible(false);}
        else
        {
          employeeView.setVisible(true);
      loginView.setVisible(false);}
      }
    }
  }

  private class RegisterButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String username = loginView.getUsername();
      String password = loginView.getPassword();

      userValidator.validate(username, password);
      final List<String> errors = userValidator.getErrors();
      if (errors.isEmpty()) {
        authenticationService.register(username, password);
      } else {
        JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
      }
    }
  }
}
