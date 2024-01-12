package Database.init;

import Database.tables.EditCarTable;
import model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Resources {

    // Customers
    static Customer customer1 = new Customer(
            "john.doe@example.com", "johndoe", "secure123", "John", "Doe",
            LocalDate.of(1990, 5, 15), 1234567890, 123, 12, 2025, "X720456339"
    );

    static Customer customer2 = new Customer(
            "jane.smith@example.com", "janesmith", "pass456", "Jane", "Smith",
            LocalDate.of(1985, 8, 22), 1345678901, 234, 11, 2024, "X321123456"
    );

    static Customer customer3 = new Customer(
            "alice.jones@example.com", "alicejones", "password789", "Alice", "Jones",
            LocalDate.of(1992, 3, 10), 1456789012, 345, 10, 2023, "X654987321"
    );

    static Customer customer4 = new Customer(
            "bob.brown@example.com", "bobbrown", "pass1234", "Bob", "Brown",
            LocalDate.of(1988, 7, 19), 1567890123, 456, 9, 2026, "X987654321"
    );

    static Customer customer5 = new Customer(
            "carol.white@example.com", "carolwhite", "mypassword", "Carol", "White",
            LocalDate.of(1995, 12, 5), 1678901234, 567, 8, 2022, "X123456789"
    );

    static Customer customer6 = new Customer(
            "giwrgos.mitsos@example.com", "gmitsos", "mypassword", "Giwrgos", "Mitsos",
            LocalDate.of(1992, 4, 9), 1600901234, 217, 9, 2024,null
    );

    // Cars
    public static void addCars() throws SQLException, ClassNotFoundException {
        Car car1 = new Car(1, "Toyota", "Corolla", "Red", 20000, "Available", 1500, 12345, "Sedan", 5, 50000);
        Car car2 = new Car(2, "Honda", "Civic", "Blue", 22000, "Available", 1600, 12346, "Coupe", 4, 30000);
        Car car3 = new Car(3, "Ford", "Focus", "White", 18000, "Available", 1400, 12347, "Hatchback", 5, 25000);
        Car car4 = new Car(4, "BMW", "320i", "Black", 35000, "Available", 2000, 12348, "Sedan", 5, 10000);
        Car car5 = new Car(5, "Audi", "A4", "Silver", 33000, "Available", 2100, 12349, "Sedan", 5, 15000);
        EditCarTable editCarTable = new EditCarTable();
        editCarTable.insertCarTable(car1);
        editCarTable.insertCarTable(car2);
        editCarTable.insertCarTable(car3);
        editCarTable.insertCarTable(car4);
        editCarTable.insertCarTable(car5);
    }
    public ArrayList<Car> getAllCars() throws SQLException, ClassNotFoundException {
        EditCarTable editCarTable = new EditCarTable();
        return editCarTable.getAllCars();
    }
    public void printCar(Car car){
        System.out.println( car.getVehicleId() + " " +
                car.getModel() + " " +
                car.getBrand() + " " +
                car.getColor() + " " +
                car.getMileage() + " " +
                car.getRegNum() + " " +
                car.getRentalPrice() + " " +
                car.getInsurPrice() + " " +
                car.getStatus() + " " +
                car.getType() + " " +
                car.getNumPassengers());
    }
    public void updateCarStatus(int vehicleId, String status) throws SQLException, ClassNotFoundException {
        EditCarTable editCarTable = new EditCarTable();
        editCarTable.updateCarStatus(vehicleId, status);
    }

    // Motorcycles
    static MotorBike motorBike1 = new MotorBike(6, "Yamaha", "MT-07", "Black", 20000, "Available", 1000, 12350, 5000);
    static MotorBike motorBike2 = new MotorBike(7, "Honda", "CB500F", "Red", 18000, "Available", 950, 12351, 8000);
    static MotorBike motorBike3 = new MotorBike(8, "Kawasaki", "Z650", "Green", 19000, "Available", 990, 12352, 6000);
    static MotorBike motorBike4 = new MotorBike(9, "Suzuki", "GSX-S750", "Blue", 21000, "Available", 1050, 12353, 3000);
    static MotorBike motorBike5 = new MotorBike(10, "Ducati", "Monster 821", "Yellow", 22000, "Available", 1100, 12354, 4000);

    // Scooters
    static Scooter scooter1 = new Scooter(11, "Vespa", "GTS Super", "White", 5000, "Available", 200, 1000);
    static Scooter scooter2 = new Scooter(12, "Honda", "PCX150", "Black", 4500, "Available", 180, 1500);
    static Scooter scooter3 = new Scooter(13, "Yamaha", "NMAX", "Red", 4800, "Available", 190, 1200);
    static Scooter scooter4 = new Scooter(14, "Suzuki", "Burgman 400", "Blue", 5500, "Available", 210, 900);
    static Scooter scooter5 = new Scooter(15, "Kymco", "Like 150i", "Grey", 4600, "Available", 185, 1100);

    // Bicycles
    static Bicycle bike1 = new Bicycle(1, "Giant", "Escape 3", "Blue", 100, "Available", 500, 0);
    static Bicycle bike2 = new Bicycle(2, "Trek", "Marlin 5", "Red", 120, "Available", 550, 10);
    static Bicycle bike3 = new Bicycle(3, "Specialized", "Rockhopper", "Black", 150, "Available", 600, 20);
    static Bicycle bike4 = new Bicycle(4, "Cannondale", "Trail 7", "Green", 130, "Available", 570, 5);
    static Bicycle bike5 = new Bicycle(5, "Scott", "Aspect 950", "Grey", 140, "Available", 580, 15);


    // Orders
    static Order order1 = new Order(0, 1, 0, LocalTime.of(10, 0), LocalDate.of(2024, 5, 15), LocalTime.of(18, 0), LocalDate.of(2024, 5, 20));
    static Order order2 = new Order(0, 2, 0, LocalTime.of(15, 0), LocalDate.of(2024, 9, 10), LocalTime.of(15, 0), LocalDate.of(2024, 9, 15));
    // Bookings
    static Booking booking1 = new Booking(0, 1, 1, 1, 150, true, "Active");
    static Booking booking2 = new Booking(0, 1, 7, 2, 200, false, "Active");
    static Booking booking3 = new Booking(0, 1, 3, 3, 250, true, "Active");
    static Booking booking4 = new Booking(0, 2, 4, 204, 300, false, "Active");
    static Booking booking5 = new Booking(0, 2, 5, 205, 350, true, "Active");
    // Bookings

    // Expenses

    // Payments
}
