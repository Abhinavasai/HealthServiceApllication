package org.example.design;

//import org.example.service.InsertService;
import org.example.service.UpdateService;
import org.example.service.ViewService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EmployeeFacilityManagementDesign {

    //ViewService viewService = new ViewService();

    public static void main(String args[]) {
        new EmployeeFacilityManagementDesign();
    }

    public EmployeeFacilityManagementDesign() {
        JFrame EmployeeFacilityManagement = new JFrame("EmployeeFacilityManagement");
        EmployeeFacilityManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EmployeeFacilityManagement.setSize(1200, 800);

        EmployeeFacilityManagement.setLayout(null);

        JLabel statementLabel = new JLabel("What would you like to do?");
        Font labelFont = statementLabel.getFont();
        statementLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
        statementLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statementLabel.setBounds(400, 50, 500, 50);
        EmployeeFacilityManagement.add(statementLabel);


        // First Drop-down
        String[] actions = { "SELECT", "Insert", "Update", "View" };
        JComboBox<String> action = new JComboBox<>(actions);
        action.setBounds(300, 300, 200, 50);
        action.setPreferredSize(new Dimension(200, 50));
        action.setFont(new Font("Arial", Font.PLAIN, 20));



        EmployeeFacilityManagement.add(action);

        Map<String, String> tableMappings = new HashMap<>();
        tableMappings.put("Employees", "employee");
        tableMappings.put("Medical Officers", "facility");
        tableMappings.put("Out-patient surgery facilities", "outpatient_surgery");
        tableMappings.put("Employee assignments", "makes_appointment");
        tableMappings.put("Insurance companies", "insurance_company");


        // Second Drop-down
        String[] entities = { "SELECT", "Employees", "Medical Officers", "Out-patient surgery facilities", "Employee assignments", "Insurance companies"};
        JComboBox<String> entity = new JComboBox<>(entities);
        entity.setBounds(700, 300, 200, 50);
        entity.setPreferredSize(new Dimension(200, 50));
        entity.setFont(new Font("Arial", Font.PLAIN, 20));

        EmployeeFacilityManagement.add(entity);

        JButton execute = new JButton("Execute");
        execute.setBounds(500, 450, 200, 50);
        execute.setFont(new Font("Arial", Font.PLAIN, 20));

        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAction = (String) action.getSelectedItem();
                String selectedEntity = (String) entity.getSelectedItem();
                String tableName = tableMappings.get(selectedEntity);

                String ans;
                if (Objects.equals(selectedAction, "Insert")) {
                    try {
                        new InsertDesign(tableName).setVisible(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (Objects.equals(selectedAction, "Update")) {
                    ans = JOptionPane.showInputDialog("Enter ID of " + selectedEntity+" which you want to update");
                    int id = Integer.parseInt(ans);
                    try {
                        new UpdateDesign(tableName,id).setVisible(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    assert selectedAction != null;
                    if(selectedAction.equals("View"))
                    {
                        ViewDesign.displayView(tableName);
                    }
                }

                EmployeeFacilityManagement.dispose();

            }
        });

        JButton back = new JButton("Back");
        back.setBounds(500, 550, 200, 50);
        back.setFont(new Font("Arial", Font.PLAIN, 20));

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                new DasboardDesign().setVisible(true);
                EmployeeFacilityManagement.dispose();


            }
        });
        EmployeeFacilityManagement.add(back);
        EmployeeFacilityManagement.add(execute);
        EmployeeFacilityManagement.setVisible(true);

    }

}
