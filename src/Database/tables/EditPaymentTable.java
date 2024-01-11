package Database.tables;

import Database.DB_Connection;
import model.Payment;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditPaymentTable {

    public void createPaymentTable() throws SQLException, ClassNotFoundException {
        try (Connection conn = DB_Connection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sqlCreatePaymentTable = "CREATE TABLE IF NOT EXISTS payment ("
                    + "paymentID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "orderID INT NOT NULL, "
                    + "amount INT NOT NULL, "
                    + "type VARCHAR(255) NOT NULL, "
                    + "paymentHour INT, "
                    + "paymentDay INT, "
                    + "paymentMonth INT, "
                    + "paymentYear INT, "
                    + "FOREIGN KEY (orderID) REFERENCES `order`(orderID)"
                    + ");";

            stmt.execute(sqlCreatePaymentTable);
        } // try-with-resources will auto close resources
    }

    public void insertPayment(Payment p) {

        try {
            Connection conn = DB_Connection.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO payment (orderID, amount, type, paymentHour, paymentDay, paymentMonth, paymentYear) VALUES (" + p.getOrderId() + ", " + p.getAmount() + ", '" + p.getType() + "', " + p.getTime().getHour() + ", " + p.getDate().getDayOfMonth() + ", " + p.getDate().getMonthValue() + ", " + p.getDate().getYear() + ");";

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
