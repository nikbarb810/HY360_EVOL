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
}
