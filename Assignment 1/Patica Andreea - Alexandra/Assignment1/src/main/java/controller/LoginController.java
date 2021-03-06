package controller;

import javafx.event.EventHandler;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.UserValidator;
import repository.user.UserRepository;
import service.user.AuthenticationService;
import view.MessageView;
import view.LoginView;

import javafx.event.ActionEvent;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    private  LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;
    private final UserController userController;


    public LoginController( AuthenticationService authenticationService, UserValidator userValidator, UserController userController) {
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.userController = userController;
        //this.loginView.addLoginButtonListener(new LoginButtonListener());
        //this.loginView.initializeFields();
        //this.loginView.addRegisterButtonListener(handleRegisterListener());
        //this.loginView.setController(this);
        //this.loginView.launchGUI();

        //this.loginView.initializeButtonAction();
        //this.loginView.addRegisterButtonListener(handleRegisterListener());

    }

   /*private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            authenticationService.login(username, password);
        }
    }*/


    public EventHandler<ActionEvent> handleLoginListener(){
        return e -> {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

                                                                             //TODO
            User user = authenticationService.login(username, password);

            if (user == null){
                new MessageView().display( "Invalid username or password");
            }
            else{
                userController.startController(user);
            }
        };
    }

    public void setLoginView(LoginView loginView){
        this.loginView = loginView;
    }

    public EventHandler<ActionEvent>  handleRegisterListener() {

        return e -> {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validate(username, password);
            String role = userValidator.getRole(username);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.register(username, password, role);
                new MessageView().display("User registered successfully");
            } else {
                new MessageView().display( userValidator.getFormattedErrors());
                //JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
            }
        };
    }
}
