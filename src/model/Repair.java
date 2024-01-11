package model;
import java.time.LocalDate;

public class Repair {
	int RepairId;
	int BookingId;
	LocalDate StartDate;
	LocalDate EndDate;
	String description;
	String Type;

	public Repair(int repairId, int bookingId, LocalDate startDate, LocalDate endDate, String type, String description) {
        this.RepairId = repairId;
        this.BookingId = bookingId;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.Type = type;
        this.description= description;
    }

	public int getRepairId() {
		return RepairId;
	}

	public void setRepairId(int repairId) {
		RepairId = repairId;
	}

	public int getBookingId() {
		return BookingId;
	}

	public void setBookingId(int bookingId) {
		BookingId = bookingId;
	}

	public LocalDate getStartDate() {
		return StartDate;
	}

	public void setStartDate(LocalDate startDate) {
		StartDate = startDate;
	}

	public LocalDate getEndDate() {
		return EndDate;
	}

	public void setEndDate(LocalDate endDate) {
		EndDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}
}
