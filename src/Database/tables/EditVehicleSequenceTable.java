package Database.tables;

import Database.DB_Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditVehicleSequenceTable {

    public void createVehicleTable() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sqlCreateSequenceTable = "CREATE TABLE IF NOT EXISTS VehicleID_Sequence ("
                + "id INT AUTO_INCREMENT PRIMARY KEY"
                + ");";

        stmt.execute(sqlCreateSequenceTable);
        stmt.close();
        conn.close();
    }

    public void createVehicleTriggers() throws SQLException, ClassNotFoundException {
        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sqlCreateSequenceTable = "CREATE TABLE IF NOT EXISTS VehicleID_Sequence ("
                + "id INT AUTO_INCREMENT PRIMARY KEY"
                + ");";

        String sqlCreateCarTrigger = "CREATE TRIGGER Before_Insert_Car "
                + "BEFORE INSERT ON Car "
                + "FOR EACH ROW "
                + "BEGIN "
                + "INSERT INTO VehicleID_Sequence () VALUES (); "
                + "SET NEW.VehicleID = LAST_INSERT_ID(); "
                + "END;";

        String sqlCreateMotorbikeTrigger = "CREATE TRIGGER Before_Insert_Motorbike "
                + "BEFORE INSERT ON Motorbike "
                + "FOR EACH ROW "
                + "BEGIN "
                + "INSERT INTO VehicleID_Sequence () VALUES (); "
                + "SET NEW.VehicleID = LAST_INSERT_ID(); "
                + "END;";

        String sqlCreateBicycleTrigger = "CREATE TRIGGER Before_Insert_Bicycle "
                + "BEFORE INSERT ON Bicycle "
                + "FOR EACH ROW "
                + "BEGIN "
                + "INSERT INTO VehicleID_Sequence () VALUES (); "
                + "SET NEW.VehicleID = LAST_INSERT_ID(); "
                + "END;";

        String sqlCreateScooterTrigger = "CREATE TRIGGER Before_Insert_Scooter "
                + "BEFORE INSERT ON Scooter "
                + "FOR EACH ROW "
                + "BEGIN "
                + "INSERT INTO VehicleID_Sequence () VALUES (); "
                + "SET NEW.VehicleID = LAST_INSERT_ID(); "
                + "END;";

        stmt.execute(sqlCreateCarTrigger);
        stmt.execute(sqlCreateMotorbikeTrigger);
        stmt.execute(sqlCreateBicycleTrigger);
        stmt.execute(sqlCreateScooterTrigger);

        stmt.close();
        conn.close();
    }
}
