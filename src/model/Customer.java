package model;
import java.time.LocalDate;
public class Customer {
	String Username;
	String Password;
	String FirstName;
    String LastName;
    String Email;
	LocalDate Dob;
	int NumberCC;
	int BicCC;
	LocalDate YearCC;
	String LicenceId;
	
	public Customer(String username, String password, String email, String firstName, String lastName, LocalDate dob, int numberCC, int bicCC, LocalDate yearCC, String licenceId) {
        this.Username = username;
        this.Password = password;
        this.Email = email;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Dob = dob;
        this.NumberCC = numberCC;
        this.BicCC = bicCC;
        this.YearCC = yearCC;
        this.LicenceId = licenceId;
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

    public LocalDate getDob() {
        return Dob;
    }

    public int getNumberCC() {
        return NumberCC;
    }

    public int getBicCC() {
        return BicCC;
    }

    public LocalDate getYearCC() {
        return YearCC;
    }

    public String getLicenceId() {
        return LicenceId;
    }

    // Setter methods
    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setDob(LocalDate dob) {
        Dob = dob;
    }

    public void setNumberCC(int numberCC) {
        NumberCC = numberCC;
    }

    public void setBicCC(int bicCC) {
        BicCC = bicCC;
    }

    public void setYearCC(LocalDate yearCC) {
        YearCC = yearCC;
    }

    public void setLicenceId(String licenceId) {
        LicenceId = licenceId;
    }
}