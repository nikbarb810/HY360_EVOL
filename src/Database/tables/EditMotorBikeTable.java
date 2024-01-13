package Database.tables;

import Database.DB_Connection;
import model.Car;
import model.MotorBike;

import java.sql.*;
import java.time.LocalDate;
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

    // Get all cars that are available or will be available by the booking date
    public ArrayList<MotorBike> getAllAvailableMotorbikes(LocalDate bookingDate) throws SQLException, ClassNotFoundException {
        ArrayList<MotorBike> mtbs = new ArrayList<>();
        String sql = "SELECT Motorbike.* FROM Motorbike " +
                "LEFT JOIN Booking ON Motorbike.vehicleID = Booking.vehicleID " +
                "LEFT JOIN Repair ON Booking.bookingID = Repair.bookingID " +
                "WHERE Motorbike.status = 'Available' OR " +
                "((Motorbike.status = 'Crashed' OR Motorbike.status = 'Maintenance') AND Repair.endYear IS NOT NULL AND " +
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
                MotorBike m = new MotorBike(
                        rs.getInt("vehicleID"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("rentalPrice"),
                        rs.getString("status"),
                        rs.getInt("insurPrice"),
                        rs.getInt("regNumber"),
                        rs.getInt("mileage")
                );
                mtbs.add(m);
            }
        }
        return mtbs;
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
    
    public MotorBike rentFirstAvailableMotorbike() throws SQLException, ClassNotFoundException {
        MotorBike motorbike = null;

        // SQL query to select the first available motorbike
        String selectSql = "SELECT * FROM Motorbike WHERE status = 'Available' LIMIT 1;";

        // SQL query to update the status of the motorbike
        String updateSql = "UPDATE Motorbike SET status = 'Rented' WHERE vehicleID = ?;";

        try (Connection conn = DB_Connection.getConnection()) {
            // Start transaction
            conn.setAutoCommit(false);

            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                 PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

                // Select the first available motorbike
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    int vehicleID = rs.getInt("vehicleID");

                    motorbike =new MotorBike(
                            rs.getInt("vehicleID"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getString("color"),
                            rs.getInt("rentalPrice"),
                            "Rented",
                            rs.getInt("insurPrice"),
                            rs.getInt("regNumber"),
                            rs.getInt("mileage")
                    );

                    // Update the status of the motorbike to 'Rented'
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

        return motorbike; // Return the motorbike (null if no available motorbikes)
    }
    public void updateMotorbikeStatus(int vehicleID, String status) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String updateQuery = "UPDATE Motorbike SET status='"+status+"' WHERE vehicleID= '"+vehicleID+"'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        conn.close();
    }



}
