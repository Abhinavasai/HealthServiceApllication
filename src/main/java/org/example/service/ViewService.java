package org.example.service;

import org.example.dao.ViewDAO;
import org.example.design.ViewDesign;

import javax.swing.text.View;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ViewService {
    public ViewDAO viewDAO = new ViewDAO();

    public ResultSet viewEntities(String table) {
        try {
            return viewDAO.viewEntities(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeQuery(String query_selector, List<Object> dataList) {
        try {
            switch (query_selector) {
                case "Daily Revenue Report":
                    // Execute query for Daily Revenue Report
                    return viewDAO.executeDailyRevenueReportQuery(dataList);
                case "List of Appointments for a selected Physician":
                    // Execute query for List of Appointments for a selected Physician
                    return viewDAO.executeAppointmentsForPhysicianQuery(dataList);
                case "Management and Reporting for a selected Facility":
                    // Execute query for Management and Reporting for a selected Facility
                    return viewDAO.executeFacilityManagementQuery(dataList);
                case "Highest Revenue":
                    // Execute query for Highest Revenue
                    return viewDAO.executeHighestRevenueQuery(dataList);
                case "Average Daily Revenue":
                    // Execute query for Average Daily Revenue
                    return viewDAO.executeAverageDailyRevenueQuery(dataList);
                default:
                    // Handle unrecognized query_selector
                    System.err.println("Unrecognized query_selector: " + query_selector);
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet viewID(String tableName,String queryID, Integer id) {
        try {
            return viewDAO.viewID(tableName,queryID,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
