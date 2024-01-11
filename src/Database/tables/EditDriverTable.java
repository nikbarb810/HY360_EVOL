package Database.tables;

import Database.DB_Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditDriverTable {

    public void createLicensedDriverTable() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sqlCreateLicensedDriverTable = "CREATE TABLE IF NOT EXISTS Driver ("
                + "driverID INT AUTO_INCREMENT PRIMARY KEY, "
                + "customerID INT NOT NULL, "
                + "firstName VARCHAR(255) NOT NULL, "
                + "lastName VARCHAR(255) NOT NULL, "
                + "licenseID VARCHAR(255) NOT NULL, "
                + "FOREIGN KEY (customerID) REFERENCES Customer(customerID)"
                + ");";

        stmt.execute(sqlCreateLicensedDriverTable);
        stmt.close();
        conn.close();
    }
}
