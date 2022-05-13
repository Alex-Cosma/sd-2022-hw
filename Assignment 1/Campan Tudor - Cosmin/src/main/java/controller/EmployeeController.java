package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import repository.Action.ActionRepositoryMySQL;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import view.EmployeeView;
import view.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final ClientRepositoryMySQL clientRepository;
    private final AccountRepositoryMySQL accountRepository;
    private final LoginView loginView;
    private final ActionRepositoryMySQL actionRepositoryMySQL;
    public EmployeeController(EmployeeView employeeView, ClientRepositoryMySQL clientRepository, AccountRepositoryMySQL accountRepository, LoginView loginView, ActionRepositoryMySQL actionRepositoryMySQL) {
        this.employeeView = employeeView;
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.loginView = loginView;
        this.actionRepositoryMySQL = actionRepositoryMySQL;


        this.employeeView.addCreateButtonListener(new CreateButtonListener());
        this.employeeView.addUpdateButtonListener(new UpdateButtonListener());
        this.employeeView.addDeleteButtonListener(new DeleteButtonListener());
        this.employeeView.addViewButtonListener(new ViewButtonListener());
        this.employeeView.addACreateButtonListener(new ACreateButtonListener());
        this.employeeView.addADeleteButtonListener(new ADeleteButtonListener());
        this.employeeView.addAViewButtonListener(new AViewButtonListener());
        this.employeeView.addAUpdateButtonListener(new AUpdateButtonListener());
        this.employeeView.addATransferButtonListener(new ATransferButtonListener());
        this.employeeView.setVisible(true);

    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Client user = new ClientBuilder()
                    .setName(employeeView.getName())
                    .setIdcardnumber(Long.parseLong(employeeView.getIdcardnumber()))
                    .setCnp(Long.parseLong(employeeView.getCnp()))
                    .setAddress(employeeView.getAddress())
                    .build();
            clientRepository.save(user);
            actionRepositoryMySQL.save("Employee "+loginView.getUsername()+" added Client "+employeeView.getName());
        }
    }
    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Client user = new ClientBuilder()
                    .setName(employeeView.getName())
                    .setIdcardnumber(Long.parseLong(employeeView.getIdcardnumber()))
                    .setCnp(Long.parseLong(employeeView.getCnp()))
                    .setAddress(employeeView.getAddress())
                    .build();
            clientRepository.updateByName(employeeView.getName(),user);
            actionRepositoryMySQL.save("Employee "+loginView.getUsername()+" updated Client "+employeeView.getName());
        }
    }
    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            clientRepository.deleteByName(employeeView.getName());
            actionRepositoryMySQL.save("Employee "+loginView.getUsername()+" deleted Client "+employeeView.getName());
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Client c=clientRepository.findByName(employeeView.getName());
            employeeView.setName(c.getName());
            employeeView.setAddress(c.getAddress());
            employeeView.setCnp(c.getCnp());
            employeeView.setIdcardnumber(c.getIdcardnumber());
            employeeView.setId(c.getId());
        }
    }

    private class ACreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Account user = new AccountBuilder()
                    .setIdnumber(Long.parseLong(employeeView.getaIdnumber()))
                    .setType(employeeView.getaType())
                    .setMoney(Long.parseLong(employeeView.getaMoney()))
                    .setDateOfCreation(employeeView.getaDateOfCreation())
                    .setClientID(Integer.parseInt(employeeView.getaClientID()))
                    .build();
            accountRepository.save(user);
            actionRepositoryMySQL.save("Employee "+loginView.getUsername()+" added Account "+employeeView.getaIdnumber());
        }
    }
    private class AViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Account c=accountRepository.findByIdnumber(Long.parseLong(employeeView.getaIdnumber()));
            employeeView.setAdateOfCreation(c.getDateOfCreation());
            employeeView.setAType(c.getType());
            employeeView.setAMoney(c.getMoney());
            employeeView.setAIdnumber(c.getIdnumber());
            employeeView.setAClientId(c.getClientID());
        }
    }

    private class ADeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            accountRepository.deleteByIdnumber(Long.parseLong(employeeView.getaIdnumber()));
            actionRepositoryMySQL.save("Employee "+loginView.getUsername()+" deleted Account "+employeeView.getaIdnumber());

        }

    }

    private class AUpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Account user = new AccountBuilder()
                    .setIdnumber(Long.parseLong(employeeView.getaIdnumber()))
                    .setType(employeeView.getaType())
                    .setMoney(Long.parseLong(employeeView.getaMoney()))
                    .setDateOfCreation(employeeView.getaDateOfCreation())
                    .setClientID(Integer.parseInt(employeeView.getaClientID()))
                    .build();
            accountRepository.updateByIdnumber(Long.parseLong(employeeView.getaIdnumber()),user);
            actionRepositoryMySQL.save("Employee "+loginView.getUsername()+" updated Account "+employeeView.getaIdnumber());

        }
    }
    private class ATransferButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Account a1=accountRepository.findByIdnumber(employeeView.getAccount1());
            Account a2=accountRepository.findByIdnumber(employeeView.getAccount2());
            Long sum=employeeView.getSum();

            Account user1 = new AccountBuilder()
                    .setIdnumber(a1.getIdnumber())
                    .setType(a1.getType())
                    .setMoney(a1.getMoney()-sum)
                    .setDateOfCreation(a1.getDateOfCreation())
                    .setClientID(a1.getClientID())
                    .build();
            accountRepository.updateByIdnumber(a1.getIdnumber(),user1);
            Account user2 = new AccountBuilder()
                    .setIdnumber(a2.getIdnumber())
                    .setType(a2.getType())
                    .setMoney(a2.getMoney()+sum)
                    .setDateOfCreation(a2.getDateOfCreation())
                    .setClientID(a2.getClientID())
                    .build();
            accountRepository.updateByIdnumber(a2.getIdnumber(),user2);
            actionRepositoryMySQL.save("Employee "+loginView.getUsername()+" transfered "+Long.toString(employeeView.getSum())+" between account "+Long.toString(a1.getIdnumber())+" and "+Long.toString(a2.getIdnumber()));

        }
    }


}
