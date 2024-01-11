package Database.init;

import Database.tables.EditCarTable;
import model.Car;

import java.sql.SQLException;
import java.util.ArrayList;

public class Resources {

    // Customers

    // Cars
    public void addCar() throws SQLException, ClassNotFoundException {
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

    // Scooters

    // Bicycles

    // Orders

    // Bookings

    // Expenses

    // Payments
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Resources resources = new Resources();
//        resources.addCar();
        ArrayList<Car> cars = resources.getAllCars();
        for (Car car : cars) {
            //print for every car everything
            resources.printCar(car);
        }
        resources.updateCarStatus(cars.get(3).getVehicleId(), "Rented");
        resources.updateCarStatus(cars.get(4).getVehicleId(), "Rented");
        cars = resources.getAllCars();
    }
}
