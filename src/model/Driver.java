package model;

public class Driver {
	int DriverId;
	int CustomerId;
	public String FirstName;
	public String LastName;
	public int LicenseId;

    public int getDriverId() {
        return DriverId;
    }

    public void setDriverId(int driverId) {
        DriverId = driverId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getLicenseId() {
        return LicenseId;
    }

    public void setLicenseId(int licenseId) {
        LicenseId = licenseId;
    }

    public Driver(int driverId, int customerId, String firstName, String lastName, int licenseId) {
        DriverId = driverId;
        CustomerId = customerId;
        FirstName = firstName;
        LastName = lastName;
        LicenseId = licenseId;
    }

	public Driver() {
		// TODO Auto-generated constructor stub
	}

	public String getSurname() {
		// TODO Auto-generated method stub
		return this.LastName;
	}
}
