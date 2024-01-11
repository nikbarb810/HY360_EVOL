package Database.init;

import Database.tables.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Car;

import static Database.DB_Connection.getInitialConnection;

public class InitDatabase {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        InitDatabase init = new InitDatabase();
        init.initDatabase();
        init.initTables();
        init.addEntries();

//        EditCustomerTable ect = new EditCustomerTable();
//        Customer c = ect.getCustomer("johndoe");
//
//
//        System.out.println(c.getFirstName());
//        System.out.println(c.getLastName());
//        System.out.println(c.getEmail());
//        init.dropDatabase();




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

        EditRepairTable emaintt = new EditRepairTable();
        emaintt.createRepairTable();
    }

    public void addEntries() throws SQLException, ClassNotFoundException {
        EditCustomerTable ect = new EditCustomerTable();
        ect.insertCustomer(Resources.customer1);
        ect.insertCustomer(Resources.customer2);
        ect.insertCustomer(Resources.customer3);
        ect.insertCustomer(Resources.customer4);
        ect.insertCustomer(Resources.customer5);
        ect.insertCustomer(Resources.customer6);
        EditOrderTable eot = new EditOrderTable();
        int order_id = eot.insertOrder(Resources.order1);
        System.out.println("order_id: " + order_id);
        eot.updateOrder(order_id, 1000);


        Resources resources = new Resources();
        Resources.addCars();
        ArrayList<Car> cars = resources.getAllCars();
        for (Car car : cars) {
            //print for every car everything
            resources.printCar(car);
        }
        resources.updateCarStatus(cars.get(3).getVehicleId(), "Rented");
        resources.updateCarStatus(cars.get(4).getVehicleId(), "Rented");
        cars = resources.getAllCars();
        EditBicycleTable editBicycleTable = new EditBicycleTable();
        Resources.addCars();
        editBicycleTable.insertBicycle(Resources.bike1);
        editBicycleTable.insertBicycle(Resources.bike2);
        editBicycleTable.insertBicycle(Resources.bike3);
        editBicycleTable.insertBicycle(Resources.bike4);
        editBicycleTable.insertBicycle(Resources.bike5);

        EditMotorBikeTable editMotorBikeTable = new EditMotorBikeTable();

        editMotorBikeTable.insertMotorBikeTable(Resources.motorBike1);
        editMotorBikeTable.insertMotorBikeTable(Resources.motorBike2);
        editMotorBikeTable.insertMotorBikeTable(Resources.motorBike3);
        editMotorBikeTable.insertMotorBikeTable(Resources.motorBike4);
        editMotorBikeTable.insertMotorBikeTable(Resources.motorBike5);

        EditScooterTable editScooterTable = new EditScooterTable();

        editScooterTable.insertScooter(Resources.scooter1);
        editScooterTable.insertScooter(Resources.scooter2);
        editScooterTable.insertScooter(Resources.scooter3);
        editScooterTable.insertScooter(Resources.scooter4);
        editScooterTable.insertScooter(Resources.scooter5);

    }

}
