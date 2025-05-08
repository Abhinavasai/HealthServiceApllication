package org.example.design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ManagementAndReportingDesign extends JFrame {

    public ManagementAndReportingDesign() {
        setTitle("Management and Reporting");
        setSize(1200, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton button1 = new JButton("Daily Revenue Report");
        JButton button2 = new JButton("List of Appointments for a selected Physician");
        JButton button3 = new JButton("Management and Reporting for a selected Facility");
        JButton button4 = new JButton("Highest Revenue");
        JButton button5 = new JButton("Average Daily Revenue");
        JButton backButton = new JButton("Back");

        // Set button sizes
        Dimension buttonSize = new Dimension(500, 100);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        button4.setPreferredSize(buttonSize);
        button5.setPreferredSize(buttonSize);
        backButton.setPreferredSize(new Dimension(100, 50));

        // Set font size for buttons
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        button1.setFont(buttonFont);
        button2.setFont(buttonFont);
        button3.setFont(buttonFont);
        button4.setFont(buttonFont);
        button5.setFont(buttonFont);
        backButton.setFont(buttonFont);

        // Add action listeners to buttons
        button1.addActionListener(e -> {
            new QueryInputDesign("Daily Revenue Report", true, false, false, false).setVisible(true);
            dispose();
        });

        button2.addActionListener(e -> {
            new QueryInputDesign("List of Appointments for a selected Physician", true, false, false, true).setVisible(true);
            dispose();
        });

        button3.addActionListener(e -> {
            new QueryInputDesign("Management and Reporting for a selected Facility", true, true, true, false).setVisible(true);
            dispose();
        });

        button4.addActionListener(e -> {
            new QueryInputDesign("Highest Revenue", true, false, false, false).setVisible(true);
            dispose();
        });

        button5.addActionListener(e -> {
            new QueryInputDesign("Average Daily Revenue", true, true, false, false).setVisible(true);
            dispose();
        });

        // Add action listener to the back button
        backButton.addActionListener(e -> {
            // Navigate to another page
            new DasboardDesign().setVisible(true);
            dispose();
        });

        // Create panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20)); // GridLayout with 3 rows, 1 column, and gaps of 20 pixels
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        buttonPanel.add(button5);

        // Create a panel to center the button panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(buttonPanel, gbc);

        // Create a panel for the back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);

        // Add panels to the frame
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagementAndReportingDesign().setVisible(true));
    }
}
