package Database.tables;

import Database.DB_Connection;
import model.Booking;
import model.Order;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                "AND (o.startYear < ? OR (o.startYear = ? AND o.startMonth < ?) OR (o.startYear = ? AND o.startMonth = ? AND o.startDay <= ?));";

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

    public ArrayList<Booking> getBookingsByDate(LocalDate startDate, LocalDate endDate) {
        ArrayList<Booking> bookings = new ArrayList<>();
        String sql = "SELECT Booking.* FROM Booking " +
                "INNER JOIN `Order` ON Booking.orderID = `Order`.orderID " +
                "WHERE (`Order`.startYear > ? OR (`Order`.startYear = ? AND (`Order`.startMonth > ? OR (`Order`.startMonth = ? AND `Order`.startDay >= ?)))) " +
                "AND (`Order`.startYear < ? OR (`Order`.startYear = ? AND (`Order`.startMonth < ? OR (`Order`.startMonth = ? AND `Order`.startDay <= ?))));";

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set start date parameters
            pstmt.setInt(1, startDate.getYear());
            pstmt.setInt(2, startDate.getYear());
            pstmt.setInt(3, startDate.getMonthValue());
            pstmt.setInt(4, startDate.getMonthValue());
            pstmt.setInt(5, startDate.getDayOfMonth());

            // Set end date parameters
            pstmt.setInt(6, endDate.getYear());
            pstmt.setInt(7, endDate.getYear());
            pstmt.setInt(8, endDate.getMonthValue());
            pstmt.setInt(9, endDate.getMonthValue());
            pstmt.setInt(10, endDate.getDayOfMonth());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Assuming you have a constructor or method to convert a ResultSet to a Booking object
                bookings.add(new Booking(rs.getInt("bookingID"),
                        rs.getInt("orderID"),
                        rs.getInt("vehicleID"),
                        rs.getInt("driverID"),
                        rs.getInt("bookingCost"),
                        rs.getBoolean("coveredInsur"),
                        rs.getString("status")));

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    /**
     * Calculates the statistics for car rentals
     * @return a Map of Strings containing the max, min and avg duration of car rentals in format [max,max_val] [min,min_val] [avg, avg_val]
     */
    public Map<String,String> calculateCarStatistics() {
        String sql = "SELECT " +
                "MAX(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS MaxDuration, " +
                "MIN(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS MinDuration, " +
                "AVG(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS AvgDuration " +
                "FROM `Order` " +
                "JOIN Booking ON `Order`.orderID = Booking.orderID " +
                "JOIN Car ON Booking.vehicleID = Car.vehicleID;";
        Map<String,String> stats = new HashMap<>();
        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int maxDuration = rs.getInt("MaxDuration");
                int minDuration = rs.getInt("MinDuration");
                double avgDuration = rs.getDouble("AvgDuration");
                stats.put("max",Integer.toString(maxDuration));
                stats.put("min",Integer.toString(minDuration));
                stats.put("avg",Double.toString(avgDuration));

                System.out.println("Car Rental Duration Statistics:");
                System.out.println("Max Duration: " + maxDuration + " days");
                System.out.println("Min Duration: " + minDuration + " days");
                System.out.println("Avg Duration: " + String.format("%.2f", avgDuration) + " days");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stats;
    }

    /**
     * Calculates the statistics for motorbike rentals
     * @return a Map of Strings containing the max, min and avg duration of motorbike rentals in format [max,max_val] [min,min_val] [avg, avg_val]
     */
    public Map<String,String> calculateMotorBikeStatistics(){
        String sql = "SELECT " +
                "MAX(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS MaxDuration, " +
                "MIN(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS MinDuration, " +
                "AVG(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS AvgDuration " +
                "FROM `Order` " +
                "JOIN Booking ON `Order`.orderID = Booking.orderID " +
                "JOIN Motorbike ON Booking.vehicleID = Motorbike.vehicleID;";
        Map<String,String> stats = new HashMap<>();
        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int maxDuration = rs.getInt("MaxDuration");
                int minDuration = rs.getInt("MinDuration");
                double avgDuration = rs.getDouble("AvgDuration");
                stats.put("max",Integer.toString(maxDuration));
                stats.put("min",Integer.toString(minDuration));
                stats.put("avg",Double.toString(avgDuration));

                System.out.println("MotorBike Rental Duration Statistics:");
                System.out.println("Max Duration: " + maxDuration + " days");
                System.out.println("Min Duration: " + minDuration + " days");
                System.out.println("Avg Duration: " + String.format("%.2f", avgDuration) + " days");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stats;
    }

    /**
     * Calculates the statistics for bicycle rentals
     * @return a Map of Strings containing the max, min and avg duration of bicycle rentals in format [max,max_val] [min,min_val] [avg, avg_val]
     */
    public Map<String,String> calculateBicycleStatistics(){
        String sql = "SELECT " +
                "MAX(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS MaxDuration, " +
                "MIN(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS MinDuration, " +
                "AVG(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS AvgDuration " +
                "FROM `Order` " +
                "JOIN Booking ON `Order`.orderID = Booking.orderID " +
                "JOIN Bicycle ON Booking.vehicleID = Bicycle.vehicleID;";
        Map<String,String> stats = new HashMap<>();
        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int maxDuration = rs.getInt("MaxDuration");
                int minDuration = rs.getInt("MinDuration");
                double avgDuration = rs.getDouble("AvgDuration");
                stats.put("max",Integer.toString(maxDuration));
                stats.put("min",Integer.toString(minDuration));
                stats.put("avg",Double.toString(avgDuration));


                System.out.println("Bicycle Rental Duration Statistics:");
                System.out.println("Max Duration: " + maxDuration + " days");
                System.out.println("Min Duration: " + minDuration + " days");
                System.out.println("Avg Duration: " + String.format("%.2f", avgDuration) + " days");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stats;
    }

    /**
     * Calculates the statistics for scooter rentals
     * @return a Map of Strings containing the max, min and avg duration of scooter rentals in format [max,max_val] [min,min_val] [avg, avg_val]
     */
    public Map<String,String> calculateScooterStatistics(){
        String sql = "SELECT " +
                "MAX(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS MaxDuration, " +
                "MIN(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS MinDuration, " +
                "AVG(DATEDIFF(CONCAT(`Order`.endYear, '-', LPAD(`Order`.endMonth, 2, '0'), '-', LPAD(`Order`.endDay, 2, '0')), " +
                "            CONCAT(`Order`.startYear, '-', LPAD(`Order`.startMonth, 2, '0'), '-', LPAD(`Order`.startDay, 2, '0')))) AS AvgDuration " +
                "FROM `Order` " +
                "JOIN Booking ON `Order`.orderID = Booking.orderID " +
                "JOIN Scooter ON Booking.vehicleID = Scooter.vehicleID;";
        Map<String,String> stats = new HashMap<>();
        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int maxDuration = rs.getInt("MaxDuration");
                int minDuration = rs.getInt("MinDuration");
                double avgDuration = rs.getDouble("AvgDuration");
                stats.put("max",Integer.toString(maxDuration));
                stats.put("min",Integer.toString(minDuration));
                stats.put("avg",Double.toString(avgDuration));


                System.out.println("Scooter Rental Duration Statistics:");
                System.out.println("Max Duration: " + maxDuration + " days");
                System.out.println("Min Duration: " + minDuration + " days");
                System.out.println("Avg Duration: " + String.format("%.2f", avgDuration) + " days");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stats;
    }


}
