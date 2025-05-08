package org.example.service;
import java.sql.SQLException;
import org.example.dao.InsertDAO;
import org.example.dao.UpdateDAO;

import java.sql.SQLException;
import java.util.List;


public class UpdateService {
        UpdateDAO updateDAO = new UpdateDAO();

        public void updateData(List<String> columnNames,List<Object> data, String tablename, int id) throws SQLException {
            updateDAO.updateData(columnNames,data, tablename, id);
        }


}
