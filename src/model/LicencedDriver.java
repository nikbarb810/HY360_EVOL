package model;

public class LicencedDriver extends Driver{
	int LicenceId;
	public LicencedDriver(int driverId, int customerId, String firstName, String lastName,int licence) {
		super(driverId, customerId, firstName, lastName);
		this.LicenceId = licence;
	}

	
	
	
	
}
