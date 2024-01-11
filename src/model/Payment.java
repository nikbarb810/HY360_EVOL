package model;
import java.time.LocalTime;
import java.time.LocalDate;
public class Payment {
	int PaymentId;
	int OrderId;
	int Amount;
	String Type;
	LocalTime Time;
	LocalDate Date;
	public Payment(int paymentId, int orderId, int amount, String type, LocalTime time, LocalDate date) {
        this.PaymentId = paymentId;
        this.OrderId = orderId;
        this.Amount = amount;
        this.Type = type;
        this.Time = time;
        this.Date = date;
    }
}
