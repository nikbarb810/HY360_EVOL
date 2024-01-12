package Database.tables;

import Database.DB_Connection;
import model.Order;

import java.math.BigDecimal;
import java.sql.*;

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


    public void updateOrder(int orderId, int cost) {
        String sql = "UPDATE `Order` SET cost = ? WHERE orderID = ?;";

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cost);
            pstmt.setInt(2, orderId);

            pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
