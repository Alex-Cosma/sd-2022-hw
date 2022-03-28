package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame {

    private JLabel l1,l2;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton View;
    private JButton Update;
    private JButton Create;
    private JButton Delete;
    private JButton Reports;
    private JTextArea area;

    public AdminView() {
        setSize(700, 700);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(l1);
        add(tfUsername);
        add(l2);
        add(tfPassword);
        add(View);
        add(Update);
        add(Create);
        add(Delete);
        add(Reports);
        add(area);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initializeFields() {
        l1= new JLabel("Usename");
        l2=new JLabel("Password");
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        Update = new JButton("Update");
        Create = new JButton("Create");
        Delete = new JButton("Delete");
        View = new JButton("View");
        Reports = new JButton("Reports");
        area=new JTextArea("");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void addCreateButtonListener(ActionListener CreateButtonListener) {
        Create.addActionListener(CreateButtonListener);
    }

    public void addDeleteButtonListener(ActionListener DeleteButtonListener) {
        Delete.addActionListener(DeleteButtonListener);
    }
    public void addReportsButtonListener(ActionListener ReportsButtonListener) {
        Reports.addActionListener(ReportsButtonListener);
    }
    public void writeInArea(String s)
    {
        area.setText(s);
    }
}
