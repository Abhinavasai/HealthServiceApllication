package org.example.dao;

import java.sql.*;
import java.util.List;

public class ViewDAO {
    public ResultSet viewEntities(String table) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mhs", "root", "Abhinav1#");
            stmt = con.createStatement();
            if (table.equals("invoice_detail")) {
                 resultSet = stmt.executeQuery("SELECT " +
                         "    i.Inv_id," +
                         "    i.Inv_Date," +
                         "    i.Ins_id," +
                         "    ic.Name AS Insurance_Company," +
                         "    p.P_id," +
                         "    p.FName," +
                         "    p.LName," +
                         "    SUM(id.Cost) AS Patient_Subtotal " +
                         "FROM" +
                         "    Invoice AS i " +
                         "JOIN" +
                         "    Invoice_Detail AS id ON i.Inv_id = id.Inv_id " +
                         "JOIN" +
                         "    Patient AS p ON id.P_id = p.P_id " +
                         "JOIN" +
                         "    Insurance_Company AS ic ON i.Ins_id = ic.Ins_id " +
                         "GROUP BY " +
                         "i.Inv_id, i.Inv_Date, i.Ins_id, ic.Name, p.P_id, p.FName");

            } else {
                resultSet = stmt.executeQuery("SELECT * FROM " + table);
            }
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResultSet executeDailyRevenueReportQuery(List<Object> data) throws SQLException {
        // Get the query string
        String query = "SELECT FacID, SUM(Cost) AS Total_Revenue " +
                "FROM Invoice_Detail " +
                "WHERE Inv_id IN (" +
                "    SELECT Inv_id" +
                "    FROM Invoice" +
                "    WHERE Inv_Date =?" +
                ") " +
                "GROUP BY FacID WITH ROLLUP";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mhs", "root", "Abhinav1#");

             PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the date parameter
            preparedStatement.setObject(1, data.get(0)); // Assuming fromDateValue is at index 0 in the list

            // Execute the query
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeAppointmentsForPhysicianQuery(List<Object> data) throws SQLException {
        // Get the query string
        String query = "SELECT Date_Time, Patient.P_id, FName, LName, Speciality " +
                "FROM Makes_Appointment " +
                "JOIN Doctor ON Makes_Appointment.ESSN = Doctor.ESSN " +
                "JOIN Patient ON Makes_Appointment.P_id = Patient.P_id " +
                "WHERE Date(Date_Time) = ? " +
                "AND Doctor.ESSN = ?";

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mhs", "root", "Abhinav1#");

            // Create PreparedStatement with the query
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the date and physician parameters
            preparedStatement.setObject(1, data.get(0)); // fromDateValue
            preparedStatement.setObject(2, data.get(3)); // physicianValue

            // Execute the query
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            // Wrap SQLException in a RuntimeException to simplify error handling
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeFacilityManagementQuery(List<Object> data) throws SQLException {
        // Get the query string
        String query = "SELECT Date_Time, FName, LName, Speciality, Descr " +
                "FROM Makes_Appointment " +
                "JOIN Doctor ON Makes_Appointment.ESSN = Doctor.ESSN " +
                "JOIN Patient ON Makes_Appointment.P_id = Patient.P_id " +
                "JOIN Outpatient_Surgery ON Makes_Appointment.FacID = Outpatient_Surgery.FacID " +
                "WHERE Date_Time BETWEEN ? AND ? " +
                "AND Outpatient_Surgery.FacID = ?";

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mhs", "root", "Abhinav1#");

            // Create PreparedStatement with the query
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the fromDate, toDate, and facilityID parameters
            preparedStatement.setObject(1, data.get(0)); // fromDateValue
            preparedStatement.setObject(2, data.get(1)); // toDateValue
            preparedStatement.setObject(3, data.get(2)); // facilityValue

            // Execute the query
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            // Wrap SQLException in a RuntimeException to simplify error handling
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeHighestRevenueQuery(List<Object> data) throws SQLException {
        // Get the query string
        String query = "SELECT DATE(Inv_Date) AS Revenue_Date, SUM(Inv_Amt) AS Total_Revenue " +
                "FROM Invoice " +
                "WHERE MONTH(Inv_Date) = ? AND YEAR(Inv_Date) = ? " +
                "GROUP BY DATE(Inv_Date) " +
                "ORDER BY Total_Revenue DESC " +
                "LIMIT 5";

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mhs", "root", "Abhinav1#");

            // Create PreparedStatement with the query
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Extract year and month from fromDateValue
            String fromDate = (String) data.get(0);
            String[] yearMonth = fromDate.split("-");
            String month = yearMonth[1]; // Extract month
            String year = yearMonth[0]; // Extract year

            // Set the month and year parameters
            preparedStatement.setObject(1, month); // Set month
            preparedStatement.setObject(2, year); // Set year

            // Execute the query
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            // Wrap SQLException in a RuntimeException to simplify error handling
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeAverageDailyRevenueQuery(List<Object> data) throws SQLException {
        // Get the query string
        String query = "SELECT Ins_id, AVG(Daily_Revenue) AS Avg_Daily_Revenue " +
                "FROM ( " +
                "    SELECT DATE(Inv_Date) AS Date, Ins_id, SUM(Inv_Amt) AS Daily_Revenue " +
                "    FROM Invoice " +
                "    WHERE Inv_Date BETWEEN ? AND ? " +
                "    GROUP BY DATE(Inv_Date), Ins_id " +
                ") AS Daily_Revenue_Per_Ins " +
                "GROUP BY Ins_id";

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mhs", "root", "Abhinav1#");

            // Create PreparedStatement with the query
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the from date and to date parameters
            preparedStatement.setObject(1, data.get(0)); // Set from date
            preparedStatement.setObject(2, data.get(1)); // Set to date

            // Execute the query
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            // Wrap SQLException in a RuntimeException to simplify error handling
            throw new RuntimeException(e);
        }
    }


    public ResultSet viewID(String tableName, String idColumnName, Integer id) {
        try {
            Connection con = null;
            PreparedStatement pstmt = null;
            ResultSet resultSet = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mhs", "root", "Abhinav1#");
                String query = "SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?";
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, id);
                resultSet = pstmt.executeQuery();
                return resultSet;
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
//            finally {
//                // Close the resources in the finally block
//                if (pstmt != null) {
//                    try {
//                        pstmt.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (con != null) {
//                    try {
//                        con.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
