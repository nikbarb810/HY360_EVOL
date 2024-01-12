package Database.tables;

import Database.DB_Connection;
import model.Bicycle;
import model.Car;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

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

    public ArrayList<Bicycle> getAllBicycles() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Bicycle";
        ArrayList<Bicycle> bicycles = new ArrayList<>();
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
            Bicycle bc = new Bicycle(vehicleID, brand, model, color, rentalPrice, status, insurPrice, mileage);
            bicycles.add(bc);
        }
        stmt.close();
        conn.close();
        return bicycles;
    }


    // Get all cars that are available or will be available by the booking date
    public ArrayList<Bicycle> getAllAvailableBicycles(LocalDate bookingDate) throws SQLException, ClassNotFoundException {
        ArrayList<Bicycle> bicycles = new ArrayList<>();

        String sql = "SELECT Bicycle.* FROM Bicycle " +
                "LEFT JOIN Booking ON Bicycle.vehicleID = Booking.vehicleID " +
                "LEFT JOIN Repair ON Booking.bookingID = Repair.bookingID " +
                "WHERE Bicycle.status = 'Available' OR " +
                "((Bicycle.status = 'Crashed' OR Bicycle.status = 'Maintenance') AND Repair.endYear IS NOT NULL AND " +
                "(Repair.endYear < ? OR (Repair.endYear = ? AND Repair.endMonth < ?) OR (Repair.endYear = ? AND Repair.endMonth = ? AND Repair.endDay < ?)))";

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the booking date parameters for the prepared statement
            pstmt.setInt(1, bookingDate.getYear());
            pstmt.setInt(2, bookingDate.getYear());
            pstmt.setInt(3, bookingDate.getMonthValue());
            pstmt.setInt(4, bookingDate.getYear());
            pstmt.setInt(5, bookingDate.getMonthValue());
            pstmt.setInt(6, bookingDate.getDayOfMonth());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Create a Car object using the constructor with the correct order of parameters
                Bicycle b = new Bicycle(
                        rs.getInt("vehicleID"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("rentalPrice"),
                        rs.getString("status"),
                        rs.getInt("insurPrice"),
                        rs.getInt("mileage")
                );
                bicycles.add(b);
            }
        }
        return bicycles;
    }


    public ArrayList<Bicycle> getAllAvailableBicycles() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Bicycle WHERE status = 'Available'";
        ArrayList<Bicycle> bicycles = new ArrayList<>();
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
            Bicycle bc = new Bicycle(vehicleID, brand, model, color, rentalPrice, status, insurPrice, mileage);
            bicycles.add(bc);
        }
        stmt.close();
        conn.close();
        return bicycles;
    }

    public void updateBicycleStatus(int vehicleId, String status) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE Bicycle SET status = '" + status + "' WHERE vehicleID = " + vehicleId;
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public Bicycle getBicycleByVehicleID(int vehicleID) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Bicycle WHERE vehicleID = " + vehicleID;
        ResultSet rs = stmt.executeQuery(sql);
        Bicycle bc = null;
        while (rs.next()) {
            String model = rs.getString("model");
            String brand = rs.getString("brand");
            String color = rs.getString("color");
            int mileage = rs.getInt("mileage");
            int rentalPrice = rs.getInt("rentalPrice");
            int insurPrice = rs.getInt("insurPrice");
            String status = rs.getString("status");
            bc = new Bicycle(vehicleID, brand, model, color, rentalPrice, status, insurPrice, mileage);
        }
        stmt.close();
        conn.close();
        return bc;
    }

}