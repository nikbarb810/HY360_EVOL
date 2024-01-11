package Database.tables;

import Database.DB_Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
}
