package view.client.information;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class InformationView extends JFrame{

    private Container container;
    private JScrollPane jScrollPane;

    private JTable clientsIntormationTable;

    private JButton btnAddClientInformation;
    private JButton btnUpdateClientInformation;
    private JButton btnViewClientInformation;

    public InformationView() {
        initializeFields();
        setBounds();
        addComponentsToContainer();
        initializeFrame();
    }

    private void initializeFrame() {
        setSize(500, 500);
        setResizable(false);
        setVisible(false);
        setTitle("Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        container = getContentPane();
        container.setLayout(null);

        clientsIntormationTable = new JTable(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        jScrollPane = new JScrollPane(clientsIntormationTable);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        btnAddClientInformation = new JButton("Add information");
        btnUpdateClientInformation = new JButton("Update information");
        btnViewClientInformation = new JButton("View information");
    }

    private void setBounds() {
        jScrollPane.setBounds(0, 0, 488, 200);
        btnAddClientInformation.setBounds(50, 250, 150, 30);
        btnUpdateClientInformation.setBounds(50, 300, 150, 30);
        btnViewClientInformation.setBounds(50, 350, 150, 30);
    }

    private void addComponentsToContainer() {
        container.add(btnAddClientInformation);
        container.add(btnUpdateClientInformation);
        container.add(btnViewClientInformation);
        container.add(jScrollPane);
    }

    public void setAddClientInformationListener(ActionListener actionListener) {
        this.btnAddClientInformation.addActionListener(actionListener);
    }

    public void setUpdateClientInformationListener(ActionListener actionListener) {
        this.btnUpdateClientInformation.addActionListener(actionListener);
    }

    public void setViewClientInformationListener(ActionListener actionListener) {
        this.btnViewClientInformation.addActionListener(actionListener);
    }

    public void clickViewButton() {
        btnViewClientInformation.doClick();
    }

    public String getValueFromInformationTableCell(int row, int column) {
        return (String) clientsIntormationTable.getValueAt(row, column);
    }

    public DefaultTableModel getInformationTableDefaultTableModel() {
        return (DefaultTableModel) this.clientsIntormationTable.getModel();
    }

    public int[] getInformationTableSelectedRows() {
        return this.clientsIntormationTable.getSelectedRows();
    }

    public void setClientsTableReordering(boolean flag) {
        clientsIntormationTable.getTableHeader().setReorderingAllowed(flag);
    }
}
