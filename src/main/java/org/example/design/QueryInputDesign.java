package org.example.design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryInputDesign extends JFrame {

    private String fromDateValue = "";
    private String toDateValue = "";
    private String facilityValue = "";
    private String physicianValue = "";

    public QueryInputDesign(String query_selector, Boolean from_date, Boolean to_date, Boolean facility, Boolean physician) {
        setTitle("Enter details to query");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800); // Set window size
        Font font = new Font("Arial", Font.PLAIN, 20);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20); // Adjusted insets for spacing

        JLabel fromDateLabel = new JLabel("From Date:");
        JTextField fromDatePicker = new JTextField();
        fromDateLabel.setVisible(from_date);
        fromDatePicker.setVisible(from_date);
        fromDatePicker.setPreferredSize(new Dimension(300, 30)); // Adjusted text field size
        fromDateLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjusted font size
        fromDatePicker.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjusted font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(fromDateLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(fromDatePicker, gbc);

        JLabel toDateLabel = new JLabel("To Date:");
        JTextField toDatePicker = new JTextField();
        toDateLabel.setVisible(to_date);
        toDatePicker.setVisible(to_date);
        toDatePicker.setPreferredSize(new Dimension(300, 30)); // Adjusted text field size
        toDateLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjusted font size
        toDatePicker.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjusted font size
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(toDateLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(toDatePicker, gbc);

        JLabel facilityLabel = new JLabel("Facility:");
        JTextField facilityField = new JTextField();
        facilityLabel.setVisible(facility);
        facilityField.setVisible(facility);
        facilityField.setPreferredSize(new Dimension(300, 30)); // Adjusted text field size
        facilityLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjusted font size
        facilityField.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjusted font size
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(facilityLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(facilityField, gbc);

        JLabel physicianLabel = new JLabel("Physician:");
        JTextField physicianField = new JTextField();
        physicianLabel.setVisible(physician);
        physicianField.setVisible(physician);
        physicianField.setPreferredSize(new Dimension(300, 30)); // Adjusted text field size
        physicianLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjusted font size
        physicianField.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjusted font size
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(physicianLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(physicianField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 60));
        submitButton.setFont(font);
        submitButton.addActionListener(e -> {

            fromDateValue = fromDatePicker.getText();
            toDateValue = toDatePicker.getText();
            facilityValue = facilityField.getText();
            physicianValue = physicianField.getText();

            // Parse the date strings into Date objects
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int facilityId = facilityValue.isEmpty() ? 0 : Integer.parseInt(facilityValue);
            int physicianId = physicianValue.isEmpty() ? 0 : Integer.parseInt(physicianValue);
            try {
                Date fromDate = !fromDateValue.isEmpty() ? dateFormat.parse(fromDateValue) : null;
                Date toDate = !toDateValue.isEmpty() ? dateFormat.parse(toDateValue) : null;

                // Now you have the fromDate and toDate as Date objects
                // You can use these Date objects in your function calls or further processing
            } catch (ParseException ex) {
                ex.printStackTrace();
                // Handle parsing exception
            }
            dispose();
            ViewDesign.displayView(query_selector,fromDateValue, toDateValue, facilityValue, physicianValue);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel nestedButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 60)); // Set button size
        backButton.setFont(font);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DasboardDesign().setVisible(true);
            }
        });

        nestedButtonPanel.add(submitButton);
        nestedButtonPanel.add(backButton);

        buttonPanel.add(nestedButtonPanel, BorderLayout.EAST); // Align buttons to the right
        add(inputPanel, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.SOUTH);

//        add(submitButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QueryInputDesign("Management and Reporting for a selected Facility",true,true,true,false).setVisible(true));
    }
}
