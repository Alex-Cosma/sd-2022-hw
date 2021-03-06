package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private Container container;
    private JLabel usernameLabel;
    private JTextField usernameField;

    private JLabel passwordLabel;
    private JPasswordField passwordField;

    private JButton btnLogin;
    private JButton btnRegister;

    JCheckBox showPassword;

    public LoginView() {
        initializeFields();
        setBounds();
        addComponentsToContainer();
        initializeFrame();
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        usernameLabel = new JLabel("username");
        usernameField = new JTextField();

        passwordLabel = new JLabel("password");
        passwordField = new JPasswordField();

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        showPassword = new JCheckBox("Show password");
    }

    private void setBounds() {
        usernameLabel.setBounds(100, 150, 100, 30);
        passwordLabel.setBounds(100, 220, 100, 30);
        usernameField.setBounds(200, 150, 150, 30);
        passwordField.setBounds(200, 220, 150, 30);
        showPassword.setBounds(197, 260, 150, 30);
        btnLogin.setBounds(100, 300, 100, 30);
        btnRegister.setBounds(250, 300, 100, 30);
    }

    private void addComponentsToContainer() {
        container.add(usernameLabel);
        container.add(passwordLabel);
        container.add(usernameField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(btnLogin);
        container.add(btnRegister);
    }

    private void initializeFrame() {
        setSize(500, 500);
        setResizable(false);
        setVisible(true);
        setTitle("Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public void addLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

    public void addRegisterButtonListener(ActionListener registerButtonListener) {
        btnRegister.addActionListener(registerButtonListener);
    }

    public void addShowPasswordListener(ActionListener showPassowrdListener) {
        showPassword.addActionListener(showPassowrdListener);
    }

    public boolean isCheckBoxSelected() {
        return showPassword.isSelected();
    }

    public String getPasswordField() {
        return String.valueOf(passwordField.getPassword());
    }

    public void setPasswordFieldEchoChar(char c) {
        passwordField.setEchoChar(c);
    }
}
