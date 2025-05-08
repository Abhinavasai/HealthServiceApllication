package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateDAO {

    public void updateData(List<String> columnNames, List<Object> data, String tableName, int id) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ")
                .append(tableName)
                .append(" SET ");

        // Construct the SET clause with column names
        for (int i = 0; i < columnNames.size(); i++) {
            sqlBuilder.append(columnNames.get(i)).append(" = ?");
            if (i < columnNames.size() - 1) {
                sqlBuilder.append(", ");
            }
        }
        sqlBuilder.append(" WHERE id = ?");

        String sql = sqlBuilder.toString();

        try {
             Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mhs", "root", "Abhinav1#");
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set values for the prepared statement
            int paramIndex = 1;
            for (Object value : data) {
                preparedStatement.setObject(paramIndex++, value);
            }
            // Set ID value
            preparedStatement.setInt(paramIndex, id);

            // Execute the SQL statement
            preparedStatement.executeUpdate();
            System.out.println("Data updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
