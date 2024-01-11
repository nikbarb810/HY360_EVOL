package model;
import java.time.LocalTime;
import java.time.LocalDate;
public class Order {
	int OrderId;
	int CustomerId;
	int Cost;
	LocalTime StartTime;
	LocalDate StartDate;
	LocalTime EndTime;
	LocalDate EndDate;

    public Order(int orderId, int customerId, int cost, LocalTime startTime, LocalDate startDate, LocalTime endTime, LocalDate endDate) {
        this.OrderId = orderId;
        this.CustomerId = customerId;
        this.Cost = cost;
        this.StartTime = startTime;
        this.StartDate = startDate;
        this.EndTime = endTime;
        this.EndDate = endDate;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public LocalTime getStartTime() {
        return StartTime;
    }

    public void setStartTime(LocalTime startTime) {
        StartTime = startTime;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalTime getEndTime() {
        return EndTime;
    }

    public void setEndTime(LocalTime endTime) {
        EndTime = endTime;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }
}
