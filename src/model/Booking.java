package model;

public class Booking {
	int BookingId;
	int OrderId;
	int VehicleId;
	int DriverId;
	int BookingCost;
	boolean CoveredInsur;
	public Booking(int bookingId, int orderId, int vehicleId, int driverId, int bookingCost, boolean coveredInsur) {
        this.BookingId = bookingId;
        this.OrderId = orderId;
        this.VehicleId = vehicleId;
        this.DriverId = driverId;
        this.BookingCost = bookingCost;
        this.CoveredInsur = coveredInsur;
    }
}
