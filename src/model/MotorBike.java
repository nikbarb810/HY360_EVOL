package model;

public class MotorBike extends Vehicle{
	int RegNum;
	public MotorBike(int vehicleId, String brand, String model, String color, int rentalPrice, String status,
			int insurPrice ,int RegNum, int Mileage) {
		super(vehicleId, brand, model, color, rentalPrice, status, insurPrice, Mileage);
		this.RegNum = RegNum;
	}
	
}
