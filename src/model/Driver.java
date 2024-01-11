package model;

public class Driver {
	int DriverId;
	int CustomerId;
	String FirstName;
	String LastName;
	public Driver(int driverId, int customerId, String firstName, String lastName) {
        DriverId = driverId;
        CustomerId = customerId;
        FirstName = firstName;
        LastName = lastName;
    }
}
