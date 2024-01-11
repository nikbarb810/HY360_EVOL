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

    public int getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(int paymentId) {
        PaymentId = paymentId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public LocalTime getTime() {
        return Time;
    }

    public void setTime(LocalTime time) {
        Time = time;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }
}
