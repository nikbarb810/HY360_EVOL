package Database.tables;

import Database.DB_Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
                    + "FOREIGN KEY (bookingID) REFERENCES booking(bookingID)"
                    + ");";

            stmt.execute(sqlCreateMaintenanceExpenseTable);
        } // try-with-resources will auto close resources
    }

    public void insertRepair(int bookingId, int totalCost, String type, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, String description) {

        try {
            Connection conn = DB_Connection.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO Repair (bookingID, totalCost, type, startDay, startMonth, startYear, endDay, endMonth, endYear, description) VALUES (" + bookingId + ", " + totalCost + ", '" + type + "', " + startDay + ", " + startMonth + ", " + startYear + ", " + endDay + ", " + endMonth + ", " + endYear + ", '" + description + "');";

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
