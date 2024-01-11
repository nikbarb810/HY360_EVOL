package model;

public class Car extends Vehicle{
	int RegNum;
	String Type;
	int NumPassengers;
	
	
	public Car(int vehicleId,String Brand,String Model,String Color,int RentalPrice,String Status,int InsurPrice,int RegNum,String Type,int NumPassengers,int mileage) {
		super(vehicleId,Brand,Model,Color,RentalPrice,Status,InsurPrice, mileage);

		this.Type = Type;
		this.RegNum = RegNum;
		this.NumPassengers = NumPassengers;
	}

	public int getRegNum() {
		return RegNum;
	}

	public void setRegNum(int regNum) {
		RegNum = regNum;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public int getNumPassengers() {
		return NumPassengers;
	}

	public void setNumPassengers(int numPassengers) {
		NumPassengers = numPassengers;
	}
}
