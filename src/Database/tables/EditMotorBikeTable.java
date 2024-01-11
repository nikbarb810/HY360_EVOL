package Database.tables;

import Database.DB_Connection;
import model.MotorBike;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
//mpallhs
public class EditMotorBikeTable {

    public void createMotorBikeTable() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS Motorbike (\n"
                + "    vehicleID INT PRIMARY KEY,\n"
                + "    model VARCHAR(255) NOT NULL,\n"
                + "    brand VARCHAR(255) NOT NULL,\n"
                + "    color VARCHAR(255) NOT NULL,\n"
                + "    mileage INT NOT NULL,\n"
                + "    regNumber VARCHAR(255) NOT NULL,\n"
                + "    rentalPrice INT NOT NULL,\n"
                + "    insurPrice INT NOT NULL,\n"
                + "    status ENUM('Available', 'Rented', 'Maintenance', 'Crashed') NOT NULL\n"
                + ");";
        stmt.execute(sql);
        stmt.close();
        conn.close();
    }
    public void insertMotorBikeTable(MotorBike toBeAdded) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String insertQuery = "INSERT INTO Motorbike (vehicleID, model, brand, color, mileage, regNumber, rentalPrice, insurPrice, status) " +
                "VALUES ('" + toBeAdded.getVehicleId() +
                "', '" + toBeAdded.getModel() +
                "', '" + toBeAdded.getBrand() +
                "', '" + toBeAdded.getColor() +
                "', '" + toBeAdded.getMileage() +
                "', '" + toBeAdded.getRegNum() +
                "', '" + toBeAdded.getRentalPrice() +
                "', '" + toBeAdded.getInsurPrice() +
                "', '" + toBeAdded.getStatus() + "')";
    }
}
