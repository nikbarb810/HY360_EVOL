package Database.tables;

import Database.DB_Connection;
import model.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        try (Connection conn = DB_Connection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (Statement stmt = conn.createStatement()) {

                // Insert the booking
                String sql = "INSERT INTO Booking (orderID, vehicleID, driverID, bookingCost, coveredInsur, status) VALUES ("
                        + b.getOrderId() + ", " + b.getVehicleId() + ", " + b.getDriverId() + ", "
                        + b.getBookingCost() + ", " + b.isCoveredInsur() + ", '" + b.getStatus() + "');";
                stmt.executeUpdate(sql);

                // Update the order cost by the booking cost
                sql = "UPDATE `Order` SET cost = cost + " + b.getBookingCost() + " WHERE orderID = " + b.getOrderId() + ";";
                stmt.executeUpdate(sql);

                // Update the vehicle status to "Rented"
                String vehicleTable = findVehicleTable(conn, b.getVehicleId());
                if (vehicleTable != null) {
                    updateVehicleStatus(conn, vehicleTable, b.getVehicleId(), "Rented");
                }

                conn.commit(); // Commit transaction
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                throw e;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String findVehicleTable(Connection conn, int vehicleId) throws SQLException {
        String[] tables = {"Car", "Motorbike", "Bicycle", "Scooter"};
        for (String table : tables) {
            String sql = "SELECT COUNT(*) FROM " + table + " WHERE vehicleID = ?;";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, vehicleId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return table; // Found the table
                }
            }
        }
        return null; // Vehicle table not found
    }

    public void updateBookingStatus(int bookingId, String status) {
        String sqlBooking = "UPDATE Booking SET status = ? WHERE bookingID = ?;";

        // Start transaction
        try (Connection conn = DB_Connection.getConnection()) {
            conn.setAutoCommit(false); // Disable auto-commit for transaction

            try (PreparedStatement pstmtBooking = conn.prepareStatement(sqlBooking)) {
                // Update the booking status
                pstmtBooking.setString(1, status);
                pstmtBooking.setInt(2, bookingId);
                pstmtBooking.executeUpdate();
            }

            if ("Finished".equals(status) || "Crashed".equals(status)) {
                // Get the vehicleID from Booking
                int vehicleId = getVehicleIdFromBooking(conn, bookingId);

                // Determine the correct vehicle table and update the status
                String vehicleTable = findVehicleTable(conn, vehicleId);
                if (vehicleTable != null) {
                    updateVehicleStatus(conn, vehicleTable, vehicleId, "Finished".equals(status) ? "Available" : "Crashed");
                }
            }

            conn.commit(); // Commit transaction
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get vehicleID from a Booking
    private int getVehicleIdFromBooking(Connection conn, int bookingId) throws SQLException {
        String sql = "SELECT vehicleID FROM Booking WHERE bookingID = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("vehicleID");
            }
        }
        return -1; // VehicleID not found
    }

    // Helper method to update vehicle status
    private void updateVehicleStatus(Connection conn, String vehicleTable, int vehicleId, String status) throws SQLException {
        String sql = "UPDATE " + vehicleTable + " SET status = ? WHERE vehicleID = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, vehicleId);
            pstmt.executeUpdate();
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

    /**
     * Calculates the total rental income for a specified vehicle type and time period.
     * This method returns a list of maps, with each map representing a record of income data.
     *
     * @param vehicleType The type of vehicle for which income data is calculated (e.g., "Car", "Motorbike", "Scooter").
     * @param startDate   The start date from which to calculate income. Only the year and month of this date are used.
     * @return An {@code ArrayList<Map<String, String>>} where each {@code Map} contains the following key-value pairs:
     *         <ul>
     *             <li>"VehicleType": The type of the vehicle as specified in the {@code vehicleType} parameter.</li>
     *             <li>"Year": The year for which the income data is calculated.</li>
     *             <li>"Month": The month for which the income data is calculated.</li>
     *             <li>"TotalIncome": The total income for the given vehicle type, month, and year. This income
     *                 is the sum of the booking cost and, if applicable, the insurance price.</li>
     *         </ul>
     *         Each {@code Map} in the list represents income data for a distinct month and year.
     * @throws SQLException             If a database access error occurs or the method is called on a closed connection.
     * @throws ClassNotFoundException   If the JDBC driver class is not found.
     */
    public ArrayList<Map<String, String>> calculateRentalIncomeByCategoryAndTimePeriod(String vehicleType, LocalDate startDate) {
        int startYear = startDate.getYear();
        int startMonth = startDate.getMonthValue();

        String sql = "SELECT CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0')) AS TimePeriod, " +
                "SUM(Booking.bookingCost + CASE WHEN Booking.coveredInsur THEN Vehicle.insurPrice ELSE 0 END) AS TotalIncome " +
                "FROM Booking " +
                "JOIN `Order` ON Booking.orderID = `Order`.orderID " +
                "JOIN " + vehicleType + " Vehicle ON Booking.vehicleID = Vehicle.vehicleID " +
                "WHERE `Order`.startYear = ? AND `Order`.startMonth = ? " +
                "GROUP BY TimePeriod";
        ArrayList<Map<String,String>> results = new ArrayList<>();

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, startYear);
            pstmt.setInt(2, startMonth);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String timePeriod = rs.getString("TimePeriod");
                int totalIncome = rs.getInt("TotalIncome");
                int year = rs.getInt("startYear");
                int month = rs.getInt("startMonth");

                Map<String,String> record = new HashMap<>();
                record.put("vehicleType", vehicleType);
                record.put("year", String.valueOf(year));
                record.put("month", String.valueOf(month));
                record.put("totalIncome", String.valueOf(totalIncome));
                results.add(record);
                System.out.println("Vehicle Type: " + vehicleType + ", Time Period: " + timePeriod + ", Total Income: " + totalIncome);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return results;
    }



//    //function that updates the status of a booking and the vehicles availability
//    public void updateBookingStatus(int bookingId, String status) {
//        String sql = "UPDATE Booking SET status = ? WHERE bookingID = ?;";
//
//        try (Connection conn = DB_Connection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, status);
//            pstmt.setInt(2, bookingId);
//
//            pstmt.executeUpdate();
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

}
