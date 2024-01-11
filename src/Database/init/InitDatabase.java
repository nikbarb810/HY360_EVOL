package Database.init;

import Database.tables.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static Database.DB_Connection.getInitialConnection;

public class InitDatabase {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        InitDatabase init = new InitDatabase();
        init.initDatabase();
        init.initTables();


    }

    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE HY360_EVOL");
        stmt.close();
        conn.close();
    }

    public void dropDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        String sql = "DROP DATABASE  HY360_EVOL";
        stmt.executeUpdate(sql);
        System.out.println("Database dropped successfully...");
    }

    public void initTables() throws SQLException, ClassNotFoundException {
        EditCustomerTable ect = new EditCustomerTable();
        ect.createCustomerTable();

        EditVehicleSequenceTable evt = new EditVehicleSequenceTable();
        evt.createVehicleTable();

        EditCarTable ecart = new EditCarTable();
        ecart.createCarTable();

        EditMotorBikeTable embt = new EditMotorBikeTable();
        embt.createMotorBikeTable();

        EditBicycleTable ebt = new EditBicycleTable();
        ebt.createBicycleTable();

        EditScooterTable est = new EditScooterTable();
        est.createScooterTable();

        evt.createVehicleTriggers();

        EditOrderTable eot = new EditOrderTable();
        eot.createOrderTable();

        EditDriverTable eldt = new EditDriverTable();
        eldt.createLicensedDriverTable();


        EditBookingTable ebookt = new EditBookingTable();
        ebookt.createBookingTable();

        EditPaymentTable epayt = new EditPaymentTable();
        epayt.createPaymentTable();

        EditExpenseTable emaintt = new EditExpenseTable();
        emaintt.createMaintenanceExpenseTable();
    }

    public void addEntries() {

    }



}
