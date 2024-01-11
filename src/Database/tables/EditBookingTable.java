package Database.tables;

import Database.DB_Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditBookingTable {

    public void createBookingTable() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sqlCreateBookingTable = "CREATE TABLE IF NOT EXISTS Booking ("
                + "bookingID INT AUTO_INCREMENT PRIMARY KEY, "
                + "orderID INT NOT NULL, "
                + "vehicleID INT NOT NULL, "
                + "driverID INT NOT NULL, "
                + "bookingCost INT, "
                + "coveredInsur BOOLEAN, "
                + "FOREIGN KEY (orderID) REFERENCES `Order`(orderID), "
                + "FOREIGN KEY (driverID) REFERENCES Driver(driverID)"
                + ");";

        stmt.execute(sqlCreateBookingTable);
        stmt.close();
        conn.close();
    }
}
