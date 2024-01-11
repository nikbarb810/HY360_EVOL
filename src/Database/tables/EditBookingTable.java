package Database.tables;

import Database.DB_Connection;
import model.Booking;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
                + "status VARCHAR(255), "
                + "FOREIGN KEY (orderID) REFERENCES `Order`(orderID), "
                + "FOREIGN KEY (driverID) REFERENCES Driver(driverID)"
                + ");";

        stmt.execute(sqlCreateBookingTable);
        stmt.close();
        conn.close();
    }

    public void insertBooking(Booking b) {

        try {
            Connection conn = DB_Connection.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO Booking (orderID, vehicleID, driverID, bookingCost, coveredInsur, status) VALUES (" + b.getOrderId() + ", " + b.getVehicleId() + ", " + b.getDriverId() + ", " + b.getBookingCost() + ", " + b.isCoveredInsur() + ", '" + b.getVehicleId() + "');";

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Booking> getCustomerBookings(int customerId) {

        ArrayList<Booking> bookings = new ArrayList<>();

        try {
            Connection conn = DB_Connection.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM Booking WHERE orderID IN (SELECT orderID FROM `Order` WHERE customerID = " + customerId + ");";

            stmt.executeQuery(sql);

            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return bookings;

    }
}
