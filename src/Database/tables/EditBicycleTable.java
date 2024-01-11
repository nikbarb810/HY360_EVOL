package Database.tables;

import Database.DB_Connection;
import model.Bicycle;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditBicycleTable {

    public void createBicycleTable() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS Bicycle (\n"
                + "    vehicleID INT PRIMARY KEY,\n"
                + "    model VARCHAR(255) NOT NULL,\n"
                + "    brand VARCHAR(255) NOT NULL,\n"
                + "    color VARCHAR(255) NOT NULL,\n"
                + "    mileage INT NOT NULL,\n"
                + "    rentalPrice INT NOT NULL,\n"
                + "    insurPrice INT NOT NULL,\n"
                + "    status ENUM('Available', 'Rented', 'Maintenance', 'Crashed') NOT NULL\n"
                + ");";
        stmt.execute(sql);
        stmt.close();
        conn.close();
    }

    public void insertBicycle(Bicycle bc) {
    // do not include vehicleID
        String sql = "INSERT INTO Bicycle (model, brand, color, mileage, rentalPrice, insurPrice, status) "
                + "VALUES ('" + bc.getModel() + "', '" + bc.getBrand() + "', '" + bc.getColor() + "', " + bc.getMileage() + ", " + bc.getRentalPrice() + ", " + bc.getInsurPrice() + ", '" + bc.getStatus() + "');";
        try {
            Connection conn = DB_Connection.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
