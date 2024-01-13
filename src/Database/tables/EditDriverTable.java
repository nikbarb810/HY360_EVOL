package Database.tables;

import Database.DB_Connection;
import model.Driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EditDriverTable {

    public void createLicensedDriverTable() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sqlCreateLicensedDriverTable = "CREATE TABLE IF NOT EXISTS Driver ("
                + "driverID INT AUTO_INCREMENT PRIMARY KEY, "
                + "customerID INT NOT NULL, "
                + "firstName VARCHAR(255) NOT NULL, "
                + "lastName VARCHAR(255) NOT NULL, "
                + "licenseID VARCHAR(255), "
                + "FOREIGN KEY (customerID) REFERENCES Customer(customerID)"
                + ");";

        stmt.execute(sqlCreateLicensedDriverTable);
        stmt.close();
        conn.close();
    }

    public void insertDriver(Driver d) {
        try {
            Connection conn = DB_Connection.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO Driver (customerID, firstName, lastName, licenseID) VALUES (" + d.getCustomerId() + ", '" + d.getFirstName() + "', '" + d.getLastName() + "', '" + d.getLicenseId() + "');";

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Driver> getAvailableDrivers() throws SQLException, ClassNotFoundException {
        ArrayList<Driver> availableDrivers = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Establishing a connection
            conn = DB_Connection.getConnection();

            // Create and execute SQL query
            String sql = "SELECT * FROM Driver d WHERE NOT EXISTS (" +
                    "SELECT 1 FROM Booking b WHERE b.driverID = d.driverID AND b.status = 'Active')";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // Processing the result set
            while (rs.next()) {
                int driverId = rs.getInt("driverID");
                int customerId = rs.getInt("customerID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String licenseId = rs.getString("licenseID");

                Driver driver = new Driver(driverId, customerId, firstName, lastName, licenseId);
                availableDrivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close all connections
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return availableDrivers;
    }

}
