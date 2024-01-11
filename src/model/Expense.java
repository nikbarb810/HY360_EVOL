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
}
