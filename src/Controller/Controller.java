package Controller;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import View.AdminPage;
import View.CustomerPage;
import View.Inventory;
import View.LogInPage;
import View.MainPage;
import View.RentPage;
import View.SignUp;
import model.*;
public class Controller {

	public static Customer customer;
	Order cart;
	public static ArrayList<Booking> Bookings;
	public static ArrayList<Vehicle> cars;

	public Controller() {
		this.cart = null;
		Bookings = null;
		cars = null;
	}

	public void signup(String Username, String password) {
		this.customer.setUsername(Username);
		this.customer.setPassword(password);
	}

	public void login(String Username, String password) {
		this.customer.setUsername(Username);
		this.customer.setPassword(password);
	}
	public static void LoadMainPage() {
		SwingUtilities.invokeLater(() -> {
			MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
	}
	public static void UploadAccount(Customer ok) {

	};
	public static void LoadSignUpPage() {
		SwingUtilities.invokeLater(() -> {
			SignUp page = new SignUp();
			page.setVisible(true);
        });
	}
	public static void LoadLogInPage(){
		SwingUtilities.invokeLater(() -> {
			LogInPage page = new LogInPage();
			page.setVisible(true);
        });
	}
	public static void SetCustomer(Customer papa) {
		Controller.customer = papa;
	}
	public static void LoadCustomerPage() {
		SwingUtilities.invokeLater(() -> {
			CustomerPage page = new CustomerPage();
			page.setVisible(true);
        });
	}
	public static void LoadRentPage() {
		SwingUtilities.invokeLater(() -> {
			RentPage page = new RentPage();
			page.setVisible(true);
        });
	}
	public static void LoadInventory() {
		SwingUtilities.invokeLater(() -> {
			Inventory page = new Inventory();
			page.setVisible(true);
        });
	}
	public static void LoadAdminPage() {
		SwingUtilities.invokeLater(() -> {
			AdminPage page = new AdminPage();
			page.setVisible(true);
        });
	}
	public static void main(String[] args) {
		Controller contr = new Controller();
		SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
    }


}
