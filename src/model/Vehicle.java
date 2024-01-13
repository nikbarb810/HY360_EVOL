package model;

public class Vehicle {
	 
	int vehicleId;
	String Brand;
	String Model;
	String Color;
	int RentalPrice;
	String Status;
	int InsurPrice;
	int mileage;
	
	public Vehicle(int vehicleId, String brand, String model, String color, int rentalPrice, String status, int insurPrice,int mileage) {
	    this.vehicleId = vehicleId;
	    this.Brand = brand;
	    this.Model = model;
	    this.Color = color;
	    this.RentalPrice = rentalPrice;
	    this.Status = status;
	    this.InsurPrice = insurPrice;
	    this.mileage = mileage;
	}

	public Vehicle() {
		// TODO Auto-generated constructor stub
	}


	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getBrand() {
		return Brand;
	}

	public void setBrand(String brand) {
		Brand = brand;
	}

	public String getModel() {
		return Model;
	}

	public void setModel(String model) {
		Model = model;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	public int getRentalPrice() {
		return RentalPrice;
	}

	public void setRentalPrice(int rentalPrice) {
		RentalPrice = rentalPrice;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public int getInsurPrice() {
		return InsurPrice;
	}

	public void setInsurPrice(int insurPrice) {
		InsurPrice = insurPrice;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}
}
