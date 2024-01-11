package model;

public class Booking {
	int BookingId;
	int OrderId;
	int VehicleId;
	int DriverId;
	int BookingCost;
	boolean CoveredInsur;
    String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getBookingId() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        BookingId = bookingId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }

    public int getDriverId() {
        return DriverId;
    }

    public void setDriverId(int driverId) {
        DriverId = driverId;
    }

    public int getBookingCost() {
        return BookingCost;
    }

    public void setBookingCost(int bookingCost) {
        BookingCost = bookingCost;
    }

    public boolean isCoveredInsur() {
        return CoveredInsur;
    }

    public void setCoveredInsur(boolean coveredInsur) {
        CoveredInsur = coveredInsur;
    }

    public Booking(int bookingId, int orderId, int vehicleId, int driverId, int bookingCost, boolean coveredInsur, String status) {
        this.BookingId = bookingId;
        this.OrderId = orderId;
        this.VehicleId = vehicleId;
        this.DriverId = driverId;
        this.BookingCost = bookingCost;
        this.CoveredInsur = coveredInsur;
        this.Status = status;
    }
}
