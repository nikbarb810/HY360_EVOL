package Database.tables;

import Database.DB_Connection;
import model.Booking;
import model.Order;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EditOrderTable {

    public void createOrderTable() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sqlCreateOrderTable = "CREATE TABLE IF NOT EXISTS `Order` ("
                + "orderID INT AUTO_INCREMENT PRIMARY KEY, "
                + "customerID INT NOT NULL, "
                + "cost INT, "
                + "startHour INT, "
                + "startDay INT, "
                + "startMonth INT, "
                + "startYear INT, "
                + "endHour INT, "
                + "endDay INT, "
                + "endMonth INT, "
                + "endYear INT, "
                + "FOREIGN KEY (customerID) REFERENCES Customer(customerID)"
                + ");";
        stmt.execute(sqlCreateOrderTable);
        stmt.close();
        conn.close();
    }

    public int insertOrder(Order order) {
        int orderId = -1; // Default to -1 to indicate failure

        String sql = "INSERT INTO `Order` (customerID, cost, startHour, startDay, startMonth, startYear, endHour, endDay, endMonth, endYear) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getCustomerId());
            pstmt.setInt(2, order.getCost());
            pstmt.setInt(3, order.getStartTime().getHour());
            pstmt.setInt(4, order.getStartDate().getDayOfMonth());
            pstmt.setInt(5, order.getStartDate().getMonthValue());
            pstmt.setInt(6, order.getStartDate().getYear());
            pstmt.setInt(7, order.getEndTime().getHour());
            pstmt.setInt(8, order.getEndDate().getDayOfMonth());
            pstmt.setInt(9, order.getEndDate().getMonthValue());
            pstmt.setInt(10, order.getEndDate().getYear());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return orderId;
    }

    //function that gets customerID from orderID
    public int getCustomerId(int orderId) {
        int customerId = -1;

        String sql = "SELECT customerID FROM `Order` WHERE orderID = ?;";

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    customerId = rs.getInt("customerID");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return customerId;
    }

    // Method to get all bookings within a given date range
    public List<Booking> getBookingsBetweenDates(LocalDate fromDate, LocalDate toDate) throws SQLException, ClassNotFoundException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.* FROM Booking b " +
                "JOIN `Order` o ON b.orderID = o.orderID " +
                "WHERE (o.startYear > ? OR (o.startYear = ? AND o.startMonth > ?) OR (o.startYear = ? AND o.startMonth = ? AND o.startDay >= ?)) " +
                "AND (o.endYear < ? OR (o.endYear = ? AND o.endMonth < ?) OR (o.endYear = ? AND o.endMonth = ? AND o.endDay <= ?));";

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters for fromDate
            pstmt.setInt(1, fromDate.getYear());
            pstmt.setInt(2, fromDate.getYear());
            pstmt.setInt(3, fromDate.getMonthValue());
            pstmt.setInt(4, fromDate.getYear());
            pstmt.setInt(5, fromDate.getMonthValue());
            pstmt.setInt(6, fromDate.getDayOfMonth());

            // Set parameters for toDate
            pstmt.setInt(7, toDate.getYear());
            pstmt.setInt(8, toDate.getYear());
            pstmt.setInt(9, toDate.getMonthValue());
            pstmt.setInt(10, toDate.getYear());
            pstmt.setInt(11, toDate.getMonthValue());
            pstmt.setInt(12, toDate.getDayOfMonth());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Assuming Booking class has a constructor that matches the result set
                Booking booking = new Booking(
                        rs.getInt("bookingID"),
                        rs.getInt("orderID"),
                        rs.getInt("vehicleID"),
                        rs.getInt("driverID"),
                        rs.getInt("bookingCost"),
                        rs.getBoolean("coveredInsur"),
                        rs.getString("status")
                        // Add other Booking fields if necessary
                );
                bookings.add(booking);
            }
        }

        return bookings;
    }
    
    public Order getOrderById(int orderId) throws SQLException, ClassNotFoundException {
        Order order = null;

        String sql = "SELECT * FROM `Order` WHERE orderID = ?;";

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, orderId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int customerID = rs.getInt("customerID");
                int cost = rs.getInt("cost");
                int startHour = rs.getInt("startHour");
                int startDay = rs.getInt("startDay");
                int startMonth = rs.getInt("startMonth");
                int startYear = rs.getInt("startYear");
                int endHour = rs.getInt("endHour");
                int endDay = rs.getInt("endDay");
                int endMonth = rs.getInt("endMonth");
                int endYear = rs.getInt("endYear");

                // Assuming the Order class has a constructor that matches these fields
                order = new Order(
                	    orderId, 
                	    customerID, 
                	    cost, 
                	    LocalTime.of(startHour, 0),                 // Converts int hour to LocalTime (with 0 minutes)
                	    LocalDate.of(startYear, startMonth, startDay), // Converts int year, month, day to LocalDate
                	    LocalTime.of(endHour, 0),                    // Converts int hour to LocalTime (with 0 minutes)
                	    LocalDate.of(endYear, endMonth, endDay)      // Converts int year, month, day to LocalDate
                	);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return order;
    }


}
