package Database.tables;

import Database.DB_Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditExpenseTable {

    public void createMaintenanceExpenseTable() throws SQLException, ClassNotFoundException {
        try (Connection conn = DB_Connection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sqlCreateMaintenanceExpenseTable = "CREATE TABLE IF NOT EXISTS Expense ("
                    + "expenseID INT AUTO_INCREMENT PRIMARY KEY, "
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

}
