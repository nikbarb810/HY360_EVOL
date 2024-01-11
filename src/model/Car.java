package model;

public class Car extends Vehicle{
	int RegNum;
	String Type;
	int NumPassengers;
	
	
	public Car(int vehicleId,String Brand,String Model,String Color,int RentalPrice,String Status,int InsurPrice, int Mileage,int RegNum,String Type,int NumPassengers) {
		super(vehicleId,Brand,Model,Color,RentalPrice,Status,InsurPrice,Mileage);
		this.NumPassengers = NumPassengers;
		this.Type = Type;
		this.NumPassengers = NumPassengers;
	}
}
