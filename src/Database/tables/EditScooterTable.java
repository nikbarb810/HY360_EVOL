package Database.tables;

import Database.DB_Connection;
import model.Scooter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditScooterTable {

    public void createScooterTable() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS Scooter (\n"
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

    public void insertScooter(Scooter sc) {

        String sql = "INSERT INTO Scooter (model, brand, color, mileage, rentalPrice, insurPrice, status) "
                + "VALUES ('" + sc.getModel() + "', '" + sc.getBrand() + "', '" + sc.getColor() + "', " + sc.getMileage() + ", " + sc.getRentalPrice() + ", " + sc.getInsurPrice() + ", '" + sc.getStatus() + "');";
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
