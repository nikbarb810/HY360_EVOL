package Database.tables;

import Database.DB_Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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

            int endDay,endMonth,endYear;
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

}
