package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import Database.DB_Connection;

public class Bicycle extends Vehicle{

	public Bicycle(int vehicleId, String brand, String model, String color, int rentalPrice, String status,
			int insurPrice, int mileage) {
		super(vehicleId,brand,model,color,rentalPrice,status,insurPrice, mileage);
	}
	
	public void updateBicycleStatus(int vehicleID, String status) throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();
        String updateQuery = "UPDATE Bicycle SET status='"+status+"' WHERE vehicleID= '"+vehicleID+"'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        conn.close();
    }

	@Override
	public String toString() {
		return super.toString();
	}

}
