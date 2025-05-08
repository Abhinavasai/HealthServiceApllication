package org.example.design;

import org.example.service.InsertService;
import org.example.service.UpdateService;
import org.example.service.ViewService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateDesign extends JFrame {

    UpdateService updateService = new UpdateService();
    InsertService insertService = new InsertService();
    ViewService viewService = new ViewService();
    List<JTextField> textFields;

    public UpdateDesign(String tableName,Integer id) throws SQLException {
        this.textFields = new ArrayList<>();

        setTitle("Update Data");
        setSize(1200, 800); // Set window size
        Font font = new Font("Arial", Font.PLAIN, 20);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create JPanel with GridLayout
        JPanel panel = new JPanel(new GridLayout(0, 2));

        // Add padding around the panel
        int padding = 20; // Adjust the padding size as needed
        panel.setBorder(new EmptyBorder(padding, padding, padding, padding));

        List<String> columnNames = insertService.getColumnNames(tableName);
        String queryID = "";
        for (String columnName : columnNames) {
            if (columnName.length() >= 2 && columnName.substring(columnName.length() - 2).equalsIgnoreCase("ID")) {
                queryID = columnName;
            }
        }

        ResultSet resultSet = viewService.viewID(tableName, queryID, id);

        List<String> originalValues = new ArrayList<>();

// Move the cursor to the first row
        if (resultSet.next()) {
            // Add labels and text fields for each column
            for (String columnName : columnNames) {
                JLabel label = new JLabel(columnName + ":");
                JTextField textField = new JTextField(20);
                label.setFont(font);
                textField.setFont(font);
                textFields.add(textField);
                panel.add(label);
                panel.add(textField);

                // Check if the column name exists in the result set
                if (resultSet.findColumn(columnName) != 0) {
                    // Set text field value from result set
                    String value = resultSet.getString(columnName);
                    textField.setText(value);
                    // Store the original value
                    originalValues.add(value);
                } else {
                    // Handle the case where the column name is not found in the result set
                    textField.setText("N/A");
                    originalValues.add(""); // Store an empty value
                }
            }
        } else {
            // Handle the case where the result set is empty
            System.out.println("No data found in the result set");
        }


        // Create and configure the "Add" button
        JButton addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 60)); // Set button size
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean hasChanges = false;
                List<Object> data = new ArrayList<>();
                for (int i = 0; i < textFields.size(); i++) {
                    JTextField textField = textFields.get(i);
                    String inputValue = textField.getText();
                    // Check if the current value is different from the original value
                    if (!inputValue.equals(originalValues.get(i))) {
                        hasChanges = true;
                    }
                    // Perform type conversion based on the expected data type of each column
                    if (isInteger(inputValue)) {
                        data.add(Integer.parseInt(inputValue));
                    } else if (isDouble(inputValue)) {
                        data.add(Double.parseDouble(inputValue));
                    } else {
                        data.add(inputValue); // Default to String if not an integer or double
                    }
                }
                // Check if at least one value has changed
                if (hasChanges) {
                    try {
                        updateService.updateData(columnNames, data, tableName, id);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    ViewDesign.displayView(tableName);
                } else {
                    // Alert the user that they need to update at least one value
                    JOptionPane.showMessageDialog(null, "You must update at least one value.", "No changes made", JOptionPane.WARNING_MESSAGE);
                }
            }

            private boolean isInteger(String s) {
                try {
                    Integer.parseInt(s);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            private boolean isDouble(String s) {
                try {
                    Double.parseDouble(s);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 60)); // Set button size
        backButton.setFont(font);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DasboardDesign().setVisible(true);
            }
        });

        addButton.setFont(font);
        backButton.setFont(font);
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel);


        UIManager.put("OptionPane.messageFont", font);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
    }

    public static void main(String[] args) {
        InsertService insertService = new InsertService();
        SwingUtilities.invokeLater(() -> {
            try {
                new UpdateDesign("your_table_name",2).setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}