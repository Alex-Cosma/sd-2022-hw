package view.client.account;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class AddAccountView extends JFrame {

    protected Container container;

    private JLabel numberLabel;
    private JLabel typeLabel;
    private JLabel moneyLabel;
    private JLabel creationLabel;
    private JLabel idLabel;

    private JTextField numberField;
    private JTextField typeField;
    private JTextField moneyField;
    private JTextField creationField;
    private JTextField idField;

    private JButton addAccountButton;
    private JButton cancelButton;

    public AddAccountView() {
        initializeFields();
        setLocationAndBounds();
        addComponentsToContainer();
        initializeFrame();
    }

    private void initializeFrame() {
        setSize(280, 280);
        setResizable(false);
        setVisible(true);
        setTitle("Add information");
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        idLabel = new JLabel("Client id");
        numberLabel = new JLabel("Card number");
        typeLabel = new JLabel("Type");
        moneyLabel = new JLabel("Money");
        creationLabel = new JLabel("Creation");

        idField = new JTextField();
        numberField = new JTextField();
        typeField = new JTextField();
        moneyField = new JTextField();
        creationField = new JTextField();

        addAccountButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
    }

    private void setLocationAndBounds() {
        idLabel.setBounds(30, 40, 100, 20);
        numberLabel.setBounds(30, 70, 100, 20);
        typeLabel.setBounds(30, 100, 100, 20);
        moneyLabel.setBounds(30, 130, 100, 20);
        creationLabel.setBounds(30, 160, 100, 20);

        idField.setBounds(130, 40, 110, 20);
        numberField.setBounds(130, 70, 110, 20);
        typeField.setBounds(130, 100, 110, 20);
        moneyField.setBounds(130, 130, 110, 20);
        creationField.setBounds(130, 160, 110, 20);

        addAccountButton.setBounds(30, 200, 90, 30);
        cancelButton.setBounds(130, 200, 90, 30);
    }

    private void addComponentsToContainer() {
        container.add(numberLabel);
        container.add(typeLabel);
        container.add(moneyLabel);
        container.add(creationLabel);
        container.add(numberField);
        container.add(typeField);
        container.add(moneyField);
        container.add(creationField);
        container.add(addAccountButton);
        container.add(cancelButton);
    }

    public void setAddInformationListener(ActionListener actionListener) {
        this.addAccountButton.addActionListener(actionListener);
    }

    public void setCancelAddInformationListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }
}