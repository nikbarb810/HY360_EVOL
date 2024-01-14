package model;
import java.time.LocalDate;
public class Customer {
    int CustomerId;
	String Email;
	String Username;
	String Password;
	String FirstName;
	String LastName;
	LocalDate Dob;
	int NumberCC;
	int CvvCC;
    int monthCC;
	int YearCC;
	String LicenceId;

	public Customer(String Email,String username, String password, String firstName,String LastName, LocalDate dob, int numberCC, int cvvCC, int monthCC, int yearCC, String licenceId) {
        this.Email = Email;
		this.Username = username;
        this.Password = password;
        this.FirstName = firstName;
        this.Dob = dob;
        this.NumberCC = numberCC;
        this.CvvCC = cvvCC;
        this.monthCC = monthCC;
        this.YearCC = yearCC;
        this.LicenceId = licenceId;
        this.LastName = LastName;
    }

    public Customer() {
		// TODO Auto-generated constructor stub
	}

	public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

	public String getEmail() {
        return Username;
    }
	public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }

    public LocalDate getDob() {
        return Dob;
    }

    public int getNumberCC() {
        return NumberCC;
    }

    public int getCvvCC() {
        return CvvCC;
    }

    public void setCvvCC(int cvvCC) {
        CvvCC = cvvCC;
    }

    public int getMonthCC() {
        return monthCC;
    }

    public void setMonthCC(int monthCC) {
        this.monthCC = monthCC;
    }

    public int getYearCC() {
        return YearCC;
    }

    public String getLicenceId() {
        return LicenceId;
    }

    // Setter methods
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setDob(LocalDate dob) {
        Dob = dob;
    }

    public void setNumberCC(int numberCC) {
        NumberCC = numberCC;
    }

    public void setBicCC(int bicCC) {
        CvvCC = bicCC;
    }

    public void setYearCC(int yearCC) {
        YearCC = yearCC;
    }

    public void setLicenceId(String licenceId) {
        LicenceId = licenceId;
    }

	public void setCustomerId(int customerId2) {
		// TODO Auto-generated method stub
		this.CustomerId = customerId2;
		
	}
}