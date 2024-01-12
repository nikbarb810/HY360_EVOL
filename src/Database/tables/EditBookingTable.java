package Database.tables;

import Database.DB_Connection;
import model.Booking;

import java.sql.*;
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

            String sql = "INSERT INTO Booking (orderID, vehicleID, driverID, bookingCost, coveredInsur, status) VALUES (" + b.getOrderId() + ", " + b.getVehicleId() + ", " + b.getDriverId() + ", " + b.getBookingCost() + ", " + b.isCoveredInsur() + ", '" + b.getStatus() + "');";

            stmt.executeUpdate(sql);

            // update the order cost by the booking cost
            sql = "UPDATE `Order` SET cost = cost + " + b.getBookingCost() + " WHERE orderID = " + b.getOrderId() + ";";
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

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int bookingId = rs.getInt("bookingID");
                int orderId = rs.getInt("orderID");
                int vehicleId = rs.getInt("vehicleID");
                int driverId = rs.getInt("driverID");
                int bookingCost = rs.getInt("bookingCost");
                boolean coveredInsur = rs.getBoolean("coveredInsur");
                String status = rs.getString("status");

                Booking b = new Booking(bookingId, orderId, vehicleId, driverId, bookingCost, coveredInsur, status);
                bookings.add(b);
            }


            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return bookings;

    }

    //function that gets orderID from bookingID
    public int getOrderId(int bookingId) {
        int orderId = -1;

        String sql = "SELECT orderID FROM Booking WHERE bookingID = ?;";

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookingId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    orderId = rs.getInt("orderID");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return orderId;
    }

}
