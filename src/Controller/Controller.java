package Controller;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import View.MainPage;
import model.*;
public class Controller {
	Customer customer;
	Order cart;
	ArrayList<Booking> Bookings;
	
	public Controller() {
		this.customer = null;
		this.cart = null;
		this.Bookings = null;
	}
	
	public void signup(String Username, String password) {
		this.customer.setUsername(Username);
		this.customer.setPassword(password);
	}
	
	public void login(String Username, String password) {
		this.customer.setUsername(Username);
		this.customer.setPassword(password);
	}
	public static void main(String[] args) {
        Controller me = new Controller();
		SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
    }
	
	
}
