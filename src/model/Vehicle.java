package model;

public class Vehicle {
	 
	int vehicleId;
	String Model;
	String Brand;
	String Color;
	int RentalPrice;
	int InsurPrice;
	int Mileage;
	String Status;

	public Vehicle(int vehicleId, String brand, String model, String color, int rentalPrice, String status, int insurPrice, int Mileage) {
	    this.vehicleId = vehicleId;
	    this.Brand = brand;
	    this.Model = model;
	    this.Color = color;
	    this.RentalPrice = rentalPrice;
	    this.Status = status;
	    this.InsurPrice = insurPrice;
		this.Mileage = Mileage;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public String getBrand() {
		return Brand;
	}

	public String getModel() {
		return Model;
	}

	public String getColor() {
		return Color;
	}

	public int getRentalPrice() {
		return RentalPrice;
	}

	public String getStatus() {
		return Status;
	}

	public int getInsurPrice() {
		return InsurPrice;
	}

	public int getMileage() {
		return Mileage;
	}

		
}
