package Database.tables;

import Database.DB_Connection;
import model.Car;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

//mpallhs
public class EditCarTable {

    public void createCarTable() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS Car (\n"
                + "    vehicleID INT PRIMARY KEY,\n"
                + "    model VARCHAR(255) NOT NULL,\n"
                + "    brand VARCHAR(255) NOT NULL,\n"
                + "    color VARCHAR(255) NOT NULL,\n"
                + "    mileage INT NOT NULL,\n"
                + "    type VARCHAR(255) NOT NULL,\n"
                + "    numPassengers INT NOT NULL,\n"
                + "    regNumber INT NOT NULL,\n"
                + "    rentalPrice INT NOT NULL,\n"
                + "    insurPrice INT NOT NULL,\n"
                + "    status ENUM('Available', 'Rented', 'Maintenance', 'Crashed') NOT NULL\n"
                + ");";
        stmt.execute(sql);
        stmt.close();
        conn.close();
    }
    public void insertCarTable(Car toBeAdded) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String insertQuery = "INSERT INTO Car (model, brand, color, mileage, type, numPassengers, regNumber, rentalPrice, insurPrice, status) " +
                "VALUES ('" + toBeAdded.getModel() +
                "', '" + toBeAdded.getBrand() +
                "', '" + toBeAdded.getColor() +
                "', " + toBeAdded.getMileage() +
                ", '" + toBeAdded.getType() +
                "', " + toBeAdded.getNumPassengers() +
                ", " + toBeAdded.getRegNum() +
                ", " + toBeAdded.getRentalPrice() +
                ", " + toBeAdded.getInsurPrice() +
                ", '" + toBeAdded.getStatus() + "')";

        stmt.execute(insertQuery);
        stmt.close();
        conn.close();
    }
    public ArrayList<Car> getAllCars() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Car";
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<Car> cars = new ArrayList<>();
        while (rs.next()) {
            // Create a Car object using the constructor with the correct order of parameters
            Car car = new Car(rs.getInt("vehicleID"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("color"),
                    rs.getInt("rentalPrice"),
                    rs.getString("status"),
                    rs.getInt("insurPrice"),
                    rs.getInt("regNumber"),
                    rs.getString("type"),
                    rs.getInt("numPassengers"),
                    rs.getInt("mileage"));
            cars.add(car);
        }
        rs.close();
        stmt.close();
        conn.close();
        return cars;
    }

    // Get all cars that are available or will be available by the booking date
    public ArrayList<Car> getAllAvailableCars(LocalDate bookingDate) throws SQLException, ClassNotFoundException {
        ArrayList<Car> cars = new ArrayList<>();
        String sql = "SELECT Car.* FROM Car " +
                "LEFT JOIN Booking ON Car.vehicleID = Booking.vehicleID " +
                "LEFT JOIN Repair ON Booking.bookingID = Repair.bookingID " +
                "WHERE Car.status = 'Available' OR " +
                "((Car.status = 'Crashed' OR Car.status = 'Maintenance') AND Repair.endYear IS NOT NULL AND " +
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
                Car car = new Car(
                        rs.getInt("vehicleID"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("rentalPrice"),
                        rs.getString("status"),
                        rs.getInt("insurPrice"),
                        rs.getInt("regNumber"),
                        rs.getString("type"),
                        rs.getInt("numPassengers"),
                        rs.getInt("mileage")
                );
                cars.add(car);
            }
        }
        return cars;
    }

    public void updateCarStatus(int vehicleID, String status) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String updateQuery = "UPDATE Car SET status='"+status+"' WHERE vehicleID= '"+vehicleID+"'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        conn.close();
    }

    public Car getCarByVehicleID(int vehicleID) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Car WHERE vehicleID = " + vehicleID;
        ResultSet rs = stmt.executeQuery(sql);
        Car car = null;
        while (rs.next()) {
            car = new Car(rs.getInt("vehicleID"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("color"),
                    rs.getInt("rentalPrice"),
                    rs.getString("status"),
                    rs.getInt("insurPrice"),
                    rs.getInt("regNumber"),
                    rs.getString("type"),
                    rs.getInt("numPassengers"),
                    rs.getInt("mileage"));
        }
        stmt.close();
        conn.close();
        return car;
    }

    public Car getMostPopularCar() throws SQLException, ClassNotFoundException {
        Car mostPopularCar = null;
        String sql = "SELECT Car.*, COUNT(Booking.vehicleID) as bookingCount " +
                "FROM Car " +
                "JOIN Booking ON Car.vehicleID = Booking.vehicleID " +
                "GROUP BY Car.vehicleID " +
                "ORDER BY bookingCount DESC " +
                "LIMIT 1;";

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                mostPopularCar = new Car(
                        rs.getInt("vehicleID"),
                        rs.getString("model"),
                        rs.getString("brand"),
                        rs.getString("color"),
                        rs.getInt("regNumber"),
                        rs.getString("status"),
                        rs.getInt("rentalPrice"),
                        rs.getInt("insurPrice"),
                        rs.getString("type"),
                        rs.getInt("numPassengers"),
                        rs.getInt("mileage")
                        // Include other Car attributes if necessary
                );
            }
        }

        return mostPopularCar;
    }

}
