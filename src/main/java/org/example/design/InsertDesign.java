package org.example.design;

import org.example.dao.InsertDAO;
import org.example.service.InsertService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class InsertDesign extends JFrame {
    InsertService insertService = new InsertService();
    List<JTextField> textFields;

    public InsertDesign(String tableName) throws SQLException {
        this.textFields = new ArrayList<>();

        setTitle("Insert Data");
        setSize(1000, 600); // Set window size
        Font font = new Font("Arial", Font.PLAIN, 20);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create JPanel with GridLayout
        JPanel panel = new JPanel(new GridLayout(0, 2));

        // Add padding around the panel
        int padding = 20; // Adjust the padding size as needed
        panel.setBorder(new EmptyBorder(padding, padding, padding, padding));

        // Get column names from the table
        List<String> columnNames = insertService.getColumnNames(tableName);

        // Set font for labels
        Font labelFont = new Font("Arial", Font.BOLD, 20);

        // Add labels and text fields for each column
        for (String columnName : columnNames) {
            JLabel label = new JLabel(columnName + ":");
            label.setFont(labelFont); // Set font for labels
            JTextField textField = new JTextField(20);
            textField.setFont(labelFont.deriveFont(Font.PLAIN, 18)); // Set font size for text fields
            textFields.add(textField);
            panel.add(label);
            panel.add(textField);
        }

        // Create and configure the "Add" button
        JButton addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 60)); // Set button size
        addButton.setFont(labelFont.deriveFont(Font.PLAIN, 18)); // Set font size for button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Object> data = new ArrayList<>();
                for (JTextField textField : textFields) {
                    String inputValue = textField.getText();
                    // Perform type conversion based on the expected data type of each column
                    if (isInteger(inputValue)) {
                        data.add(Integer.parseInt(inputValue));
                    } else if (isDouble(inputValue)) {
                        data.add(Double.parseDouble(inputValue));
                    } else {
                        data.add(inputValue); // Default to String if not an integer or double
                    }
                }
                try {
                    insertService.insertData(columnNames, data, tableName);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                ViewDesign.displayView(tableName);
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

        // Add components to the content pane
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
    }

    public static void main(String[] args) {
        InsertService insertService = new InsertService();
        SwingUtilities.invokeLater(() -> {
            try {
                new InsertDesign("your_table_name").setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

