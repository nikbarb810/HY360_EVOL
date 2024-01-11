package Database.tables;

import Database.DB_Connection;
import model.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditCustomerTable {

    public void createCustomerTable() throws SQLException, ClassNotFoundException {

        Connection conn = DB_Connection.getConnection();
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS Customer (\n"
                + "    customerID INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "    username VARCHAR(255) NOT NULL,\n"
                + "    password VARCHAR(255) NOT NULL,\n"
                + "    email VARCHAR(255) NOT NULL,\n"
                + "    firstname VARCHAR(255) NOT NULL,\n"
                + "    lastname VARCHAR(255) NOT NULL,\n"
                + "    dayDOB INT NOT NULL,\n"
                + "    monthDOB INT NOT NULL,\n"
                + "    yearDOB INT NOT NULL,\n"
                + "    numberCC INT NOT NULL,\n"
                + "    monthCC INT NOT NULL,\n"
                + "    yearCC INT NOT NULL,\n"
                + "    cvvCC INT NOT NULL,\n"
                + "    licenseID INT,\n"
                + "    UNIQUE (username)\n"
                + ");";
        stmt.execute(sql);
        stmt.close();
        conn.close();
    }

    public void insertCustomer(Customer c) {
        try {
          Connection conn = DB_Connection.getConnection();
          Statement stmt = conn.createStatement();

          String sql = "INSERT INTO Customer (username, password, email, firstname, lastname, dayDOB, monthDOB, yearDOB, numberCC, monthCC, yearCC, cvvCC, licenseID) VALUES ('" + c.getUsername() + "', '" + c.getPassword() + "', '" + c.getEmail() + "', '" + c.getFirstName() + "', '" + c.getLastName() + "', " + c.getDob().getDayOfMonth() + ", " + c.getDob().getMonthValue() + ", " + c.getDob().getYear() + ", " + c.getNumberCC() + ", " + c.getMonthCC() + ", " + c.getYearCC() + ", " + c.getCvvCC() + ", " + c.getLicenceId() + ");";



        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
