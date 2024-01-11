package Database.tables;

import Database.DB_Connection;
import model.Car;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                + "    regNumber VARCHAR(255) NOT NULL,\n"
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
    //get all available for renting cars
    public ArrayList<Car> getAllAvailableCars() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Car WHERE status = 'Available'";
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
        stmt.close();
        conn.close();
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

}
