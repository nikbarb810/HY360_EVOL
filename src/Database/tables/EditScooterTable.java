package Database.tables;

import Database.DB_Connection;
import model.Scooter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
                + "VALUES ('" + sc.getModel() +
                "', '" + sc.getBrand() +
                "', '" + sc.getColor() +
                "', " + sc.getMileage() +
                ", " + sc.getRentalPrice() +
                ", " + sc.getInsurPrice() +
                ", '" + sc.getStatus() + "');";
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

    public ArrayList<Scooter> getAllScooters() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Scooter";
        ArrayList<Scooter> scooters = new ArrayList<>();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int vehicleID = rs.getInt("vehicleID");
            String model = rs.getString("model");
            String brand = rs.getString("brand");
            String color = rs.getString("color");
            int mileage = rs.getInt("mileage");
            int rentalPrice = rs.getInt("rentalPrice");
            int insurPrice = rs.getInt("insurPrice");
            String status = rs.getString("status");
            Scooter sc = new Scooter(vehicleID, brand, model, color, rentalPrice, status, insurPrice, mileage);
            scooters.add(sc);
        }
        return scooters;
    }

    public ArrayList<Scooter> getAllAvailableScooters() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Scooter WHERE status = 'Available'";
        ArrayList<Scooter> scooters = new ArrayList<>();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int vehicleID = rs.getInt("vehicleID");
            String model = rs.getString("model");
            String brand = rs.getString("brand");
            String color = rs.getString("color");
            int mileage = rs.getInt("mileage");
            int rentalPrice = rs.getInt("rentalPrice");
            int insurPrice = rs.getInt("insurPrice");
            String status = rs.getString("status");
            Scooter sc = new Scooter(vehicleID, brand, model, color, rentalPrice, status, insurPrice, mileage);
            scooters.add(sc);
        }
        return scooters;
    }

    public void updateScooterStatus(int vehicleId, String status) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE Scooter SET status = '" + status + "' WHERE vehicleID = " + vehicleId;
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public Scooter getScooterByVehicleID(int vehicleID) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Scooter WHERE vehicleID = " + vehicleID;
        ResultSet rs = stmt.executeQuery(sql);
        Scooter sc = null;
        while (rs.next()) {
            sc = new Scooter(rs.getInt("vehicleID"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("color"),
                    rs.getInt("rentalPrice"),
                    rs.getString("status"),
                    rs.getInt("insurPrice"),
                    rs.getInt("mileage"));
        }
        stmt.close();
        conn.close();
        return sc;
    }
}
