package Database.init;

import Database.tables.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Bicycle;
import model.Booking;
import model.Car;
import model.MotorBike;

import static Database.DB_Connection.getInitialConnection;

public class InitDatabase {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        InitDatabase init = new InitDatabase();
        init.initDatabase();
        init.initTables();
        init.addEntries();
        performOtherActions(); // This is just for testing purposes
//        init.dropDatabase();

    }

    // Define a new function to encapsulate the commands
    private static void performOtherActions() throws SQLException, ClassNotFoundException {
        EditBookingTable ebt = new EditBookingTable();
        ebt.updateBookingStatus(3, "Crashed");

        EditRepairTable ert = new EditRepairTable();
        ert.insertRepair(3, 100, "Crash", 15, 9, 2024, "Xalase h alysida");

        EditBicycleTable ebict = new EditBicycleTable();
        for(Bicycle bicycle : ebict.getAllAvailableBicycles(LocalDate.of(2024,9,17))){
            System.out.println(bicycle.getVehicleId());
        }

        System.out.println("__________________________");

        for(Bicycle bicycle : ebict.getAllAvailableBicycles(LocalDate.of(2024,9,20))){
            System.out.println(bicycle.getVehicleId());
        }

        EditMotorBikeTable embt = new EditMotorBikeTable();
        for(MotorBike motorBike : embt.getAllAvailableMotorbikes(LocalDate.of(2024,9,14))){
            System.out.println(motorBike.getVehicleId());
        }

        System.out.println("__________________________");

        for(MotorBike motorBike : embt.getAllAvailableMotorbikes(LocalDate.of(2024,9,16))){
            System.out.println(motorBike.getVehicleId());
        }

        EditCarTable ect = new EditCarTable();

        for(Car car : ect.getAllAvailableCars(LocalDate.of(2024,9,14))){
            System.out.println(car.getRegNum());
        }

        System.out.println("__________________________");

        for(Car car : ect.getAllAvailableCars(LocalDate.of(2024,9,17))){
            System.out.println(car.getRegNum());
        }
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

        Resources resources = new Resources();
        Resources.addCars();
        ArrayList<Car> cars = resources.getAllCars();
        for (Car car : cars) {
            //print for every car everything
            resources.printCar(car);
        }

        cars = resources.getAllCars();
        EditBicycleTable editBicycleTable = new EditBicycleTable();
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

        EditOrderTable editOrderTable = new EditOrderTable();
        int order_id1 = editOrderTable.insertOrder(Resources.order2);
        int order_id2 = editOrderTable.insertOrder(Resources.order3);
        int order_id3 = editOrderTable.insertOrder(Resources.order4);

        EditBookingTable editBookingTable = new EditBookingTable();
        Booking booking1 = new Booking(0, order_id1, 1, 1, 150, true, "Active");
        Booking booking2 = new Booking(0, order_id1, 14, 2, 200, false, "Active");
        Booking booking3 = new Booking(0, order_id1, 8, 3, 250, true, "Active");

        Booking booking4 = new Booking(0, order_id2, 2, 4, 150, true, "Active");
        Booking booking5 = new Booking(0, order_id2, 15, 5, 200, false, "Active");
        Booking booking6 = new Booking(0, order_id3, 9, 6, 250, true, "Active");

        editBookingTable.insertBooking(booking1);
        editBookingTable.insertBooking(booking2);
        editBookingTable.insertBooking(booking3);
        editBookingTable.insertBooking(booking4);
        editBookingTable.insertBooking(booking5);
        editBookingTable.insertBooking(booking6);


        ArrayList<Booking> bookings = editBookingTable.getCustomerBookings(2);

        EditRepairTable editRepairTable = new EditRepairTable();
        editRepairTable.insertRepair(bookings.get(0).getBookingId(), 100, "Crash", 12, 9, 2024, "3 toympes ekane");

        editRepairTable.insertRepair(bookings.get(1).getBookingId(), 200, "Maintenance", 14, 9, 2024, "mpouzi");

        editOrderTable.calculateBicycleStatistics();
        editOrderTable.calculateCarStatistics();
        editOrderTable.calculateMotorBikeStatistics();
        editOrderTable.calculateScooterStatistics();

        editBookingTable.calculateRentalIncomeByCategoryAndTimePeriod("Car",LocalDate.of(2024, 9, 1));
        editBookingTable.calculateRentalIncomeByCategoryAndTimePeriod("Motorbike",LocalDate.of(2024, 9, 1));
        editBookingTable.calculateRentalIncomeByCategoryAndTimePeriod("Scooter",LocalDate.of(2024, 9, 1));
        editBookingTable.calculateRentalIncomeByCategoryAndTimePeriod("Bicycle",LocalDate.of(2024, 9, 1));


        editRepairTable. calculateMonthlyCostsByTypeFromStartDate(LocalDate.of(2024, 9, 1));
    }

}
