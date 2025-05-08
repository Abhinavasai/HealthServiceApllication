package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertDAO {
    public List<String> getColumnNames(String tableName) throws SQLException{
        Connection connection = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mhs", "root", "Abhinav1#");
            Statement statement = connection.createStatement();

            String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableName + "'";
            resultSet = statement.executeQuery(query);

            List<String> columnNames = new ArrayList<>();
            while (resultSet.next()) {
                columnNames.add(resultSet.getString("COLUMN_NAME"));
            }

            return columnNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                // Close ResultSet
                if (resultSet != null) {
                    resultSet.close();
                }
                // Close Statement
                if (stmt != null) {
                    stmt.close();
                }
                // Close Connection
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void insertData(List<String> columnNames,List<Object> data, String tableName) throws SQLException{
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append(" (");

        // Construct column names for the prepared statement
        for (int i = 0; i < columnNames.size(); i++) {
            sqlBuilder.append(columnNames.get(i));
            if (i < columnNames.size() - 1) {
                sqlBuilder.append(", ");
            }
        }
        sqlBuilder.append(") VALUES (");

        // Construct placeholders for the prepared statement
        for (int i = 0; i < data.size(); i++) {
            sqlBuilder.append("?");
            if (i < data.size() - 1) {
                sqlBuilder.append(", ");
            }
        }
        sqlBuilder.append(")");

        String sql = sqlBuilder.toString();
        try {
            Connection connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/mhs", "root", "Abhinav1#");
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set values for the prepared statement
            for (int i = 0; i < data.size(); i++) {
                preparedStatement.setObject(i + 1, data.get(i));
            }

            // Execute the SQL statement
            preparedStatement.executeUpdate();
            System.out.println("Data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
