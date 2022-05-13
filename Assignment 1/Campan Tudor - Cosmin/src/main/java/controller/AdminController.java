package controller;

import repository.Action.ActionRepositoryMySQL;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminController {
    private final AuthenticationServiceMySQL authenticationServiceMySQL;
    private final AdminView adminView;
    private final UserRepositoryMySQL userRepository;
    private final RightsRolesRepositoryMySQL rightRolesRepository;
    private final ActionRepositoryMySQL actionRepositoryMySQL;

    public AdminController(AuthenticationServiceMySQL authenticationServiceMySQL, AdminView adminView, UserRepositoryMySQL userRepository, RightsRolesRepositoryMySQL rightRolesRepository, ActionRepositoryMySQL actionRepositoryMySQL) {
        this.authenticationServiceMySQL = authenticationServiceMySQL;
        this.adminView = adminView;
        this.userRepository = userRepository;
        this.rightRolesRepository = rightRolesRepository;
        this.actionRepositoryMySQL = actionRepositoryMySQL;

        this.adminView.addCreateButtonListener(new AdminController.CreateButtonListener());
        this.adminView.addDeleteButtonListener(new AdminController.DeleteButtonListener());
        this.adminView.addReportsButtonListener(new AdminController.ReportsButtonListener());
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            userRepository.deleteByUsername(adminView.getUsername());
        }
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            authenticationServiceMySQL.register(adminView.getUsername(), adminView.getPassword());

        }
    }

    private class ReportsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                adminView.writeInArea(actionRepositoryMySQL.findAllActions());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }


    }

