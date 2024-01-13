package Database.tables;

import Database.DB_Connection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditRepairTable {

    public void createRepairTable() throws SQLException, ClassNotFoundException {
        try (Connection conn = DB_Connection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sqlCreateMaintenanceExpenseTable = "CREATE TABLE IF NOT EXISTS Repair ("
                    + "repairID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "bookingID INT NOT NULL, "
                    + "totalCost INT NOT NULL, "
                    + "type VARCHAR(255) NOT NULL, "
                    + "startDay INT, "
                    + "startMonth INT, "
                    + "startYear INT, "
                    + "endDay INT, "
                    + "endMonth INT, "
                    + "endYear INT, "
                    + "description TEXT, "
                    + "FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)"
                    + ");";

            stmt.execute(sqlCreateMaintenanceExpenseTable);
        }
    }

    public void insertRepair(int bookingId, int totalCost, String type, int startDay, int startMonth, int startYear, String description) {

        try {
            Connection conn = DB_Connection.getConnection();
            Statement stmt = conn.createStatement();

            int endDay, endMonth, endYear;
            // Create a LocalDate object for the start date
            LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);

            // Determine the number of days to add based on the type of repair
            int daysToAdd = 0;
            if ("crash".equalsIgnoreCase(type)) {
                daysToAdd = 3;
            } else if ("maintenance".equalsIgnoreCase(type)) {
                daysToAdd = 1;
            }

            // Add the days to the start date to get the end date
            LocalDate endDate = startDate.plusDays(daysToAdd);

            // Extract end day, month, and year from endDate
            endDay = endDate.getDayOfMonth();
            endMonth = endDate.getMonthValue();
            endYear = endDate.getYear();
            String sql = "INSERT INTO Repair (bookingID, totalCost, type, startDay, startMonth, startYear, endDay, endMonth, endYear, description) VALUES (" + bookingId + ", " + totalCost + ", '" + type + "', " + startDay + ", " + startMonth + ", " + startYear + ", " + endDay + ", " + endMonth + ", " + endYear + ", '" + description + "');";

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param startDate
     * @param endDate
     * @return String of the total cost of repairs by type and period
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<Map<String, Integer>> calculateMonthlyCostsByTypeFromStartDate(LocalDate startDate) throws SQLException, ClassNotFoundException {
        LocalDate oneMonthLater = startDate.plusMonths(1);
        String sql = "SELECT type, SUM(totalCost) AS totalCost " +
                "FROM Repair " +
                "WHERE (startYear > ? OR (startYear = ? AND startMonth > ?) OR (startYear = ? AND startMonth = ? AND startDay >= ?)) " +
                "AND (startYear < ? OR (startYear = ? AND startMonth < ?) OR (startYear = ? AND startMonth = ? AND startDay < ?)) " +
                "GROUP BY type";

        Map<String, Integer> costs = new HashMap<>();
        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters for the start date
            pstmt.setInt(1, startDate.getYear());
            pstmt.setInt(2, startDate.getYear());
            pstmt.setInt(3, startDate.getMonthValue());
            pstmt.setInt(4, startDate.getYear());
            pstmt.setInt(5, startDate.getMonthValue());
            pstmt.setInt(6, startDate.getDayOfMonth());
            // Set parameters for one month after the start date
            pstmt.setInt(7, oneMonthLater.getYear());
            pstmt.setInt(8, oneMonthLater.getYear());
            pstmt.setInt(9, oneMonthLater.getMonthValue());
            pstmt.setInt(10, oneMonthLater.getYear());
            pstmt.setInt(11, oneMonthLater.getMonthValue());
            pstmt.setInt(12, oneMonthLater.getDayOfMonth());

            ResultSet rs = pstmt.executeQuery();

            // Initialize the totals
            costs.put("Maintenance", 0);
            costs.put("Crash", 0);

            while (rs.next()) {
                String type = rs.getString("type");
                int totalCost = rs.getInt("totalCost");
                if ("Maintenance".equalsIgnoreCase(type) || "Crash".equalsIgnoreCase(type)) {
                    costs.put(type, totalCost);
                }
            }
        }

        // Convert the map to an ArrayList
        ArrayList<Map<String, Integer>> costsList = new ArrayList<>();
        costsList.add(costs);
        // Print the results
        System.out.println("Maintenance: " + costs.get("Maintenance"));
        System.out.println("Crash: " + costs.get("Crash"));
        return costsList;
    }
}