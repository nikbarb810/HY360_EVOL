package Database.tables;

import Database.DB_Connection;
import model.Customer;

import java.sql.*;
import java.time.LocalDate;

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
                + "    licenseID VARCHAR(255),\n"
                + "    UNIQUE (username)\n"
                + ");";
        stmt.execute(sql);
        stmt.close();
        conn.close();
    }

    public void insertCustomer(Customer c) {

        // print the customer
        System.out.println(c.getEmail());
        System.out.println(c.getUsername());
        System.out.println(c.getPassword());
        System.out.println(c.getFirstName());
        System.out.println(c.getLastName());
        System.out.println(c.getDob().getDayOfMonth());
        System.out.println(c.getDob().getMonthValue());
        System.out.println(c.getDob().getYear());
        System.out.println(c.getNumberCC());
        System.out.println(c.getMonthCC());
        System.out.println(c.getYearCC());
        System.out.println(c.getCvvCC());
        System.out.println("lic:" + c.getLicenceId());



        try {
            Connection conn = DB_Connection.getConnection();
            Statement stmt = conn.createStatement();

            String sql;
            if(c.getLicenceId() == null)
                sql = "INSERT INTO Customer (username, password, email, firstname, lastname, dayDOB, monthDOB, yearDOB, numberCC, monthCC, yearCC, cvvCC, licenseID) VALUES ('" + c.getUsername() + "', '" + c.getPassword() + "', '" + c.getEmail() + "', '" + c.getFirstName() + "', '" + c.getLastName() + "', " + c.getDob().getDayOfMonth() + ", " + c.getDob().getMonthValue() + ", " + c.getDob().getYear() + ", " + c.getNumberCC() + ", " + c.getMonthCC() + ", " + c.getYearCC() + ", " + c.getCvvCC() + ", null);";
            else
                sql = "INSERT INTO Customer (username, password, email, firstname, lastname, dayDOB, monthDOB, yearDOB, numberCC, monthCC, yearCC, cvvCC, licenseID) VALUES ('" + c.getUsername() + "', '" + c.getPassword() + "', '" + c.getEmail() + "', '" + c.getFirstName() + "', '" + c.getLastName() + "', " + c.getDob().getDayOfMonth() + ", " + c.getDob().getMonthValue() + ", " + c.getDob().getYear() + ", " + c.getNumberCC() + ", " + c.getMonthCC() + ", " + c.getYearCC() + ", " + c.getCvvCC() + ", '" + c.getLicenceId() + "');";
            
            stmt.execute(sql);

            // get c_id of inserted customer
            int c_id = getCustomerID(c.getUsername());

            // insert into driver table
            if (c_id != 0) {

                String sql2;
                if (c.getLicenceId() == null) {
                    sql2 = "INSERT INTO Driver (customerID, firstName, lastName, licenseID) VALUES (" + c_id + ", '" + c.getFirstName() + "', '" + c.getLastName() + "', null);";
                    stmt.executeUpdate(sql2);
                } else {
                    sql2 = "INSERT INTO Driver (customerID, firstName, lastName, licenseID) VALUES (" + c_id + ", '" + c.getFirstName() + "', '" + c.getLastName() + "', '" + c.getLicenceId() + "');";
                    stmt.executeUpdate(sql2);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public Customer getCustomer(String username) {
        Customer c = null;
        try {
            Connection conn = DB_Connection.getConnection();
            String sql = "SELECT * FROM customer WHERE username = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int dayDOB = rs.getInt("dayDOB");
                int monthDOB = rs.getInt("monthDOB");
                int yearDOB = rs.getInt("yearDOB");
                LocalDate dob = LocalDate.of(yearDOB, monthDOB, dayDOB);

                c = new Customer(
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        dob,
                        rs.getInt("numberCC"),
                        rs.getInt("cvvCC"),
                        rs.getInt("monthCC"),
                        rs.getInt("yearCC"),
                        rs.getString("licenseID")
                );
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    public int getCustomerID(String username) {
        int customerID = 0;
        try {
            Connection conn = DB_Connection.getConnection();
            String sql = "SELECT customerID FROM Customer WHERE username = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customerID = rs.getInt("customerID");
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customerID;
    }

    public Customer authenticateUser(String username, String password) {
        Customer customer = null;

        String sql = "SELECT * FROM Customer WHERE username = ? AND password = ?";

        try (Connection conn = DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int customerId = rs.getInt("customerID");
                String email = rs.getString("email");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                int dayDOB = rs.getInt("dayDOB");
                int monthDOB = rs.getInt("monthDOB");
                int yearDOB = rs.getInt("yearDOB");
                LocalDate dob = LocalDate.of(yearDOB, monthDOB, dayDOB);
                int numberCC = rs.getInt("numberCC");
                int cvvCC = rs.getInt("cvvCC");
                int monthCC = rs.getInt("monthCC");
                int yearCC = rs.getInt("yearCC");
                String licenceId = rs.getString("licenseID");

                customer = new Customer(email, username, password, firstName, lastName, dob, numberCC, cvvCC, monthCC, yearCC, licenceId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return customer;
    }

}
