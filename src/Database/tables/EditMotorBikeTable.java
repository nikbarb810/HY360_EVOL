package Database.tables;

import Database.DB_Connection;
import model.MotorBike;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
        String insertQuery = "INSERT INTO Motorbike (model, brand, color, mileage, regNumber, rentalPrice, insurPrice, status) " +
                "VALUES ('" + toBeAdded.getModel() +
                "', '" + toBeAdded.getBrand() +
                "', '" + toBeAdded.getColor() +
                "', " + toBeAdded.getMileage() +
                ", " + toBeAdded.getRegNum() +
                ", " + toBeAdded.getRentalPrice() +
                ", " + toBeAdded.getInsurPrice() +
                ", '" + toBeAdded.getStatus() + "')";

        stmt.execute(insertQuery);
        stmt.close();
        conn.close();
    }

    public ArrayList<MotorBike> getAllMotorBikes() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Motorbike";
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<MotorBike> motorbikes = new ArrayList<>();
        while (rs.next()) {
            int vehicleID = rs.getInt("vehicleID");
            String model = rs.getString("model");
            String brand = rs.getString("brand");
            String color = rs.getString("color");
            int mileage = rs.getInt("mileage");
            int regNumber = rs.getInt("regNumber");
            int rentalPrice = rs.getInt("rentalPrice");
            int insurPrice = rs.getInt("insurPrice");
            String status = rs.getString("status");
            MotorBike mb = new MotorBike(vehicleID, brand, model, color, rentalPrice, status, insurPrice, regNumber, mileage);
            motorbikes.add(mb);
        }
        stmt.close();
        conn.close();
        return motorbikes;
    }
    public ArrayList<MotorBike> getAvailableMotorBikes() throws SQLException, ClassNotFoundException{
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Motorbike WHERE status = 'Available'";
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<MotorBike> motorbikes = new ArrayList<>();
        while (rs.next()) {
            int vehicleID = rs.getInt("vehicleID");
            String model = rs.getString("model");
            String brand = rs.getString("brand");
            String color = rs.getString("color");
            int mileage = rs.getInt("mileage");
            int regNumber = rs.getInt("regNumber");
            int rentalPrice = rs.getInt("rentalPrice");
            int insurPrice = rs.getInt("insurPrice");
            String status = rs.getString("status");
            MotorBike mb = new MotorBike(vehicleID, brand, model, color, rentalPrice, status, insurPrice, regNumber, mileage);
            motorbikes.add(mb);
        }
        stmt.close();
        conn.close();
        return motorbikes;
    }

    public void updateMotorBikeTable(int vehicleID, String Status) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String updateQuery = "UPDATE Motorbike SET status = '" + Status + "' WHERE vehicleID = " + vehicleID;
        stmt.executeUpdate(updateQuery);
        stmt.close();
        conn.close();
    }

    public MotorBike getMotorBikeByVehicleID(int vehicleID) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Motorbike WHERE vehicleID = " + vehicleID;
        ResultSet rs = stmt.executeQuery(sql);
        MotorBike mb = null;
        while (rs.next()) {
            String model = rs.getString("model");
            String brand = rs.getString("brand");
            String color = rs.getString("color");
            int mileage = rs.getInt("mileage");
            int regNumber = rs.getInt("regNumber");
            int rentalPrice = rs.getInt("rentalPrice");
            int insurPrice = rs.getInt("insurPrice");
            String status = rs.getString("status");
            mb = new MotorBike(vehicleID, brand, model, color, rentalPrice, status, insurPrice, regNumber, mileage);
        }
        stmt.close();
        conn.close();
        return mb;
    }


}
