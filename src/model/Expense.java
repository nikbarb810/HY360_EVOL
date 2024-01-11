package model;
import java.time.LocalDate;
enum TYPE{
	CRASH,
	MAINTENANCE
};
public class Expense {
	int ExpenseId;
	int BookingId;
	LocalDate StartDate;
	LocalDate EndDate;
	String description;
	TYPE type;
	public Expense(int expenseId, int bookingId, LocalDate startDate, LocalDate endDate, String type,String description,TYPE type1) {
        this.ExpenseId = expenseId;
        this.BookingId = bookingId;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.type = type1;
        this.description= description;
    }

	public int getExpenseId() {
		return ExpenseId;
	}

	public void setExpenseId(int expenseId) {
		ExpenseId = expenseId;
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

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}
}
