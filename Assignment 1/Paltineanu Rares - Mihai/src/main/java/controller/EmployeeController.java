package controller;

import model.Account;
import model.Client;
import model.validator.ClientAccountValidator;
import model.validator.ClientInformationValidator;
import service.client.ClientService;
import view.EmployeeView;
import view.client.account.AddAccountView;
import view.client.information.AddInformationView;
import view.client.information.UpdateInformationView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeController {

    private static final String[] informationTableColumns = new String[]{"ID", "Name", "CNP", "Address"};
    private static final String[] accountTableColumns = new String[]{"ID", "Client", "Number", "Type", "Money", "Creation"};
    private EmployeeView employeeView;
    private ClientInformationValidator clientValidator;
    private ClientAccountValidator clientAccountValidator;
    private ClientService<Client, Long> clientService;
    private ClientService<Account, Long> accountService;

    public EmployeeController(EmployeeView employeeView,
                              ClientInformationValidator clientValidator,
                              ClientAccountValidator clientAccountValidator,
                              ClientService<Client, Long> clientService,
                              ClientService<Account, Long> accountService) {
        this.employeeView = employeeView;
        this.clientValidator = clientValidator;
        this.clientAccountValidator = clientAccountValidator;
        this.clientService = clientService;
        this.accountService = accountService;

        setTableColumns();
        initializeButtonsListener();
    }

    private void initializeButtonsListener() {
        employeeView.getInformationView().setAddClientInformationListener(new AddInformationButtonListener());
        employeeView.getInformationView().setUpdateClientInformationListener(new UpdateInformationButtonListener());
        employeeView.getInformationView().setViewClientInformationListener(new ViewInformationButtonListener());

        employeeView.getAccountView().setAddClientAccountListener(new AddAccountButtonListener());
        employeeView.getAccountView().setUpdateClientAccountListener(new UpdateAccountButtonListener());
        employeeView.getAccountView().setDeleteClientAccountListener(new DeleteAccountButtonListener());
        employeeView.getAccountView().setViewClientAccountListener(new ViewAccountButtonListener());

        employeeView.getAccountView().setTransferAccountListener(new TransferButtonListener());

        employeeView.getInformationView().getBtnViewClientInformation().doClick();

    }

    private void setTableColumns() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) employeeView.getInformationView().getClientsIntormationTable().getModel();
        defaultTableModel.setColumnIdentifiers(informationTableColumns);

        DefaultTableModel accountDefaultTableModel = (DefaultTableModel) employeeView.getAccountView().getAccountInformationTable().getModel();
        accountDefaultTableModel.setColumnIdentifiers(accountTableColumns);
    }

    protected void setViewVisible(boolean visible) {
        this.employeeView.getInformationView().setVisible(visible);
        this.employeeView.getAccountView().setVisible(visible);
    }

    private class AddInformationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddInformationController(new AddInformationView(), clientValidator, clientService);
        }
    }

    private class UpdateInformationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!isOneClientSelected()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(),
                        "Please select one client");
                return;
            }

            int selectedRow = employeeView.getInformationView().getClientsIntormationTable().getSelectedRow();
            String id = (String) employeeView.getInformationView().getClientsIntormationTable().getValueAt(selectedRow, 0);
            new UpdateInformationController(new UpdateInformationView(),
                    clientValidator,
                    clientService,
                    employeeView,
                    Long.valueOf(id));
        }

        private boolean isOneClientSelected() {
            return employeeView.getInformationView().getClientsIntormationTable().getSelectedRows().length == 1;
        }
    }

    private class ViewInformationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> clients = clientService.findall();
            DefaultTableModel defaultTableModel = (DefaultTableModel) employeeView.getInformationView().getClientsIntormationTable().getModel();
            defaultTableModel.setRowCount(0);
            for(Client client: clients) {
                final String[] data = new String[]{String.valueOf(client.getId()),
                        client.getName(),
                        client.getCNP(),
                        client.getAddress()};
                defaultTableModel.addRow(data);
            }
        }
    }

    private class AddAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddAccountController(new AddAccountView(), clientService);
        }
    }

    private class UpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ViewAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TransferButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}