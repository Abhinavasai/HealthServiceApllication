package org.example.service;

import org.example.dao.InsertDAO;

import java.sql.SQLException;
import java.util.List;

public class InsertService {
   InsertDAO insertDAO = new InsertDAO();

    public List<String> getColumnNames(String tableName) throws SQLException{
        return insertDAO.getColumnNames(tableName);
    }

    public void insertData(List<String> columnNames,List<Object> data, String tablename) throws SQLException {
        // Additional business logic can be added here before inserting data
        insertDAO.insertData(columnNames,data, tablename);
    }
}


