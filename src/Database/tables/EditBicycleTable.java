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
    
    public Bicycle rentFirstAvailableBicycle() throws SQLException, ClassNotFoundException {
        Bicycle bicycle = null;

        // SQL query to select the first available bicycle
        String selectSql = "SELECT * FROM Bicycle WHERE status = 'Available' LIMIT 1;";

        // SQL query to update the status of the bicycle
        String updateSql = "UPDATE Bicycle SET status = 'Rented' WHERE vehicleID = ?;";

        try (Connection conn = DB_Connection.getConnection()) {
            // Start transaction
            conn.setAutoCommit(false);

            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                 PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

                // Select the first available bicycle
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    int vehicleID = rs.getInt("vehicleID");

                    bicycle = new Bicycle(
                        vehicleID,
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("rentalPrice"),
                        "Rented", // Setting the status as 'Rented' directly here
                        rs.getInt("insurPrice"),
                        rs.getInt("mileage")
                    );

                    // Update the status of the bicycle to 'Rented'
                    updateStmt.setInt(1, vehicleID);
                    updateStmt.executeUpdate();
                }

                // Commit transaction
                conn.commit();
            } catch (SQLException e) {
                // Rollback transaction in case of error
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }

        return bicycle; // Return the bicycle (null if no available bicycles)
    }


    public Bicycle getMostPopularBicycle() throws SQLException, ClassNotFoundException {
        Bicycle mostPopularBicycle = null;
        String sql = "SELECT Bicycle.*, COUNT(Booking.vehicleID) as bookingCount " +
                "FROM Bicycle " +
                "JOIN Booking ON Bicycle.vehicleID = Booking.vehicleID " +
                "GROUP BY Bicycle.vehicleID " +
                "ORDER BY bookingCount DESC " +
                "LIMIT 1;";

        try (Connection conn = DB_Connection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                mostPopularBicycle = new Bicycle(
                        rs.getInt("vehicleID"),
                        rs.getString("model"),
                        rs.getString("brand"),
                        rs.getString("color"),
                        rs.getInt("rentalPrice"),
                        rs.getString("status"),
                        rs.getInt("insurPrice"),
                        rs.getInt("mileage")
                        // Include other Bicycle attributes if necessary
                );
            }
        }

        return mostPopularBicycle;
    }


}