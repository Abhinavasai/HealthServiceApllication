package org.example.design;

import org.example.design.EmployeeFacilityManagementDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DasboardDesign extends JFrame {

    public DasboardDesign() {
        setTitle("Dashboard");
        setSize(1200, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton button1 = new JButton("Employee and Facility Management");
        JButton button2 = new JButton("Patient Management");
        JButton button3 = new JButton("Management and Reporting");

        // Set button sizes
        Dimension buttonSize = new Dimension(500, 100);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);

        // Set font size for buttons
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        button1.setFont(buttonFont);
        button2.setFont(buttonFont);
        button3.setFont(buttonFont);

        // Add action listeners to buttons
        button1.addActionListener(e -> {
            dispose();
            new EmployeeFacilityManagementDesign();
        });

        button2.addActionListener(e -> {
            new PatientManagementDesign();
            dispose();
        });

        button3.addActionListener(e -> {
            new ManagementAndReportingDesign().setVisible(true);
            dispose();
        });

        // Create panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        // Create a panel to center the button panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(buttonPanel, gbc);

        // Add panel to frame
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Create and display the dashboard page
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DasboardDesign().setVisible(true);
            }
        });
    }
}
