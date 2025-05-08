package org.example.design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;

public class PatientManagementDesign {

    public static void main(String args[]) {
        new PatientManagementDesign();
    }

    public PatientManagementDesign() {
        JFrame PatientManagement = new JFrame("PatientManagement");
        PatientManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font font = new Font("Arial", Font.PLAIN, 20);
        PatientManagement.setSize(1200, 800);

        PatientManagement.setLayout(null);

        JLabel statementLabel = new JLabel("What would you like to do?");
        Font labelFont = statementLabel.getFont();
        statementLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20)); // Adjust the font size as needed
        statementLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statementLabel.setBounds(400, 50, 500, 50); // Adjust the bounds as needed
        PatientManagement.add(statementLabel);


        // First Drop-down
        String[] actions = { "SELECT", "Insert", "Update", "View" };
        JComboBox<String> action = new JComboBox<>(actions);

        action.setBounds(300, 300, 200, 50);

        // Set font size for action JComboBox
        Font comboFont = new Font("Arial", Font.PLAIN, 20);
        action.setFont(comboFont);

        PatientManagement.add(action);

        Map<String, String> tableMappings = new HashMap<>();
        tableMappings.put("Patients", "patient");
        tableMappings.put("Appointments", "makes_appointment");
        tableMappings.put("Invoice", "invoice_detail");


        // Second Drop-down
        String[] entities = { "SELECT", "Patients", "Appointments", "Invoice"};
        JComboBox<String> entity = new JComboBox<>(entities);
        entity.setBounds(700, 300, 200, 50);
        entity.setFont(comboFont);

        // Set font size for entity JComboBox
        entity.setFont(comboFont);

        PatientManagement.add(entity);

        JButton execute = new JButton("Execute");
        execute.setBounds(500, 450, 200, 50);

        // Set font size for execute JButton
        execute.setFont(comboFont);

        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAction = (String) action.getSelectedItem();
                String selectedEntity = (String) entity.getSelectedItem();
                String tableName = tableMappings.get(selectedEntity);

                if (Objects.equals(selectedAction, "Insert")) {
                    if (tableName.equals("invoice_detail")) {
                        JOptionPane.showMessageDialog(null, "Insert action cannot be performed on invoice table.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            new InsertDesign(tableName).setVisible(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        PatientManagement.dispose();
                    }
                } else if (Objects.equals(selectedAction, "Update")) {
                    if (tableName.equals("invoice_detail")) {
                        JOptionPane.showMessageDialog(null, "Update action cannot be performed on invoice table.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String ans = JOptionPane.showInputDialog("Enter ID of " + selectedEntity + " which you want to update");
                        int id = Integer.parseInt(ans);
                        try {
                            new UpdateDesign(tableName, id).setVisible(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        PatientManagement.dispose();
                    }
                } else if (Objects.equals(selectedAction, "View")) {
                    ViewDesign.displayView(tableName);
                    PatientManagement.dispose();
                }
            }
        });


        JButton back = new JButton("Back");
        back.setBounds(500, 550, 200, 50);

        // Set font size for back JButton
        back.setFont(comboFont);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                PatientManagement.dispose();
                new DasboardDesign().setVisible(true);

            }
        });
        UIManager.put("OptionPane.messageFont", font);
        PatientManagement.add(back);
        PatientManagement.add(execute);
        PatientManagement.setVisible(true);

    }

}
