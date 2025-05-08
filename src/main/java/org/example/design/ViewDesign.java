package org.example.design;

import org.example.service.ViewService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ViewDesign {
    public static void displayView(String query_selector, Object... data) {
        ResultSet resultSet = null;
        ViewService viewService = new ViewService();
        try {

            List<Object> dataList = Arrays.asList(data);

            if (query_selector.equals("Daily Revenue Report") ||
                    query_selector.equals("List of Appointments for a selected Physician") ||
                    query_selector.equals("Management and Reporting for a selected Facility") ||
                    query_selector.equals("Highest Revenue") ||
                    query_selector.equals("Average Daily Revenue")) {
                resultSet = viewService.executeQuery(query_selector, dataList);
            } else {
                resultSet = viewService.viewEntities(query_selector);
            }

            DefaultTableModel model = new DefaultTableModel();
            JTable dynamic = new JTable(model);

            JFrame view = new JFrame("View");
            view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view.setSize(1200, 800);

            JScrollPane scrollPane = new JScrollPane(dynamic);
            view.getContentPane().add(scrollPane, BorderLayout.CENTER);

            model.setRowCount(0);
            int columnCount = resultSet.getMetaData().getColumnCount();
            String[] columnNames = new String[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = resultSet.getMetaData().getColumnName(i);
            }

            model.setColumnIdentifiers(columnNames);

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }

            JButton back = new JButton("Back");
            back.addActionListener(e -> {
                view.dispose();
                new DasboardDesign().setVisible(true);
            });
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(back);

            dynamic.setRowHeight(30); // Adjust the row height as desired
            JTableHeader header = dynamic.getTableHeader();
            header.setPreferredSize(new Dimension(header.getWidth(), 40));
            Font customFont = new Font("Arial", Font.PLAIN, 20);

            dynamic.setFont(customFont);
            view.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            view.setVisible(true);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException e) {
                // Handle exceptions
                e.printStackTrace();
            }

        }
    }
}
