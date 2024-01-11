package model;

public class MotorBike extends Vehicle{
	int RegNum;

	public MotorBike(int vehicleId, String brand, String model, String color, int rentalPrice, String status,
					 int insurPrice , int RegNum, int mileage){
		super(vehicleId,brand,model,color,rentalPrice,status,insurPrice, mileage);
		this.RegNum = RegNum;
	}

	public int getRegNum() {
		return RegNum;
	}

	public void setRegNum(int regNum) {
		RegNum = regNum;
	}

}
