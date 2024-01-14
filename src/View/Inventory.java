package View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import Controller.Controller;
import Database.tables.EditBicycleTable;
import Database.tables.EditBookingTable;
import Database.tables.EditCarTable;
import Database.tables.EditMotorBikeTable;
import Database.tables.EditOrderTable;
import Database.tables.EditPaymentTable;
import Database.tables.EditRepairTable;
import Database.tables.EditScooterTable;

import model.Bicycle;
import model.Booking;
import model.Car;
import model.Driver;
import model.MotorBike;
import model.Payment;
import model.Repair;
import model.Scooter;
import model.Vehicle;
import model.Order;

public class Inventory extends JFrame{
	private JPanel vehiclesPanel;
	ArrayList<Vehicle> pap = new ArrayList();
	private LocalDateTime currentDateTime;
	public Inventory() {
		  setTitle("Inventory");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new BorderLayout());
	        setPreferredSize(new Dimension(600, 600));
	        // Top panel with checkboxes
	        JPanel topPanel = new JPanel();
	        JLabel text = new JLabel("Inventory");
	        Font labelFont = new Font("Helvetica", Font.BOLD, 26); // Change to your desired font, style, and size
	        text.setFont(labelFont);
	        text.setForeground(Color.BLACK);
	        topPanel.add(text);
	        add(topPanel, BorderLayout.NORTH);
	        vehiclesPanel = new JPanel();
	        vehiclesPanel.setLayout(new BoxLayout(vehiclesPanel, BoxLayout.Y_AXIS));
	        
	        add(new JScrollPane(vehiclesPanel), BorderLayout.CENTER);
	        PrintCars();
	        PrintMbikes();
	        PrintBikes();
	        PrintScooters();
	        pack();
	        setLocationRelativeTo(null);
	        
	}
	
	private void AddVehicles(String vehicleInfo ,String type, int vid) {
	    JPanel row = new JPanel();
	    row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
	    row.setBackground(Color.BLACK);
	    row.setAlignmentX(Component.LEFT_ALIGNMENT);
	    row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Adjust height as needed

	    JPanel innerPanel = new JPanel();
	    innerPanel.setOpaque(false); // So the black background of the outer panel is visible
	    JLabel mycar = new JLabel(vehicleInfo);
	    mycar.setForeground(Color.WHITE);
	    innerPanel.add(mycar);

	    JButton extraRepairButton = new JButton("Report Problem");
	    extraRepairButton.setBackground(Color.white);
	    extraRepairButton.setForeground(Color.black);
	    extraRepairButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {

	        	createReportDialog(type,vid);
	        }
	    });
	    innerPanel.add(extraRepairButton);
	    

	    // Return Vehicle Button
	    JButton returnVehicleButton = new JButton("Return Vehicle");
	    returnVehicleButton.setBackground(Color.white);
	    returnVehicleButton.setForeground(Color.black);
	    returnVehicleButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            //
	        	DateReturn("Date of return");
	        	EditBookingTable loser = new EditBookingTable();
	        	Booking current = loser.getCustomerBookingWithVehicleID(Controller.customer.getCustomerId(), vid);
	        	int mine = loser.getOrderId(current.getBookingId());
	        	EditOrderTable isia = new EditOrderTable();
	        	Order fin;
				try {
					fin = isia.getOrderById(mine);
					int diff = (int) calculateHoursSinceBooking(fin);
					if(currentDateTime.toLocalDate().isAfter(fin.getEndDate()) ) {
		        		createExpenseDialog(diff*10, fin.getOrderId());
		        	}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(type.equals("car")){
						EditCarTable oo = new EditCarTable();
						try {
							oo.updateCarStatus(vid, "Available");
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}else if(type.equals("mbike")){
					EditMotorBikeTable oo = new EditMotorBikeTable();
					try {
						oo.updateMotorbikeStatus(vid, "Available");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(type.equals("bike")){
					EditBicycleTable oo = new EditBicycleTable();
					try {
						oo.updateBicycleStatus(vid, "Available");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					EditScooterTable oo = new EditScooterTable();
					try {
						oo.updateScooterStatus(vid, "Available");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
    			loser.finishBooking(current.getBookingId());
    			 vehiclesPanel.removeAll();
				 PrintCars();
			     PrintMbikes();
			     PrintBikes();
			     PrintScooters();
			     vehiclesPanel.revalidate();
			     vehiclesPanel.repaint();

	        }
	    });
	    innerPanel.add(returnVehicleButton);

	    row.add(Box.createHorizontalGlue()); // Pushes content to center
	    row.add(innerPanel);
	    row.add(Box.createHorizontalGlue()); // Pushes content to center

	    vehiclesPanel.add(row);

	    vehiclesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    vehiclesPanel.repaint();
	}
	private void createReportDialog(String type , int vid) {
	    JDialog expenseDialog = new JDialog();
	    expenseDialog.setTitle("Add Expense");
	    expenseDialog.setSize(400, 400);
	    expenseDialog.setResizable(false);
	    
	    Container contentPane = expenseDialog.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

	    JPanel radioPanel = new JPanel();
	    radioPanel.setLayout(new FlowLayout());
	    JRadioButton crashButton = new JRadioButton("Crash");
	    JRadioButton maintenanceButton = new JRadioButton("Maintenance");
	    ButtonGroup group = new ButtonGroup();
	    group.add(crashButton);
	    group.add(maintenanceButton);
	    radioPanel.add(crashButton);
	    radioPanel.add(maintenanceButton);
	    contentPane.add(radioPanel);
	    JPanel descriptionPanel = new JPanel();
	    descriptionPanel.setLayout(new FlowLayout());
	    descriptionPanel.add(new JLabel("Description:"));	    
	    JTextField descriptionField  = new JTextField(15);
	    descriptionPanel.add(descriptionField);
	    contentPane.add(descriptionPanel);
	    JButton submitButton = new JButton("Submit");
	    submitButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Create an Expense object and fill it with data from the dialog
	            // Close the dialog
	            expenseDialog.dispose();
	            if(maintenanceButton.isSelected()) {
	            	String skase = descriptionField.getText();
	            	EditRepairTable ert = new EditRepairTable();
	            	EditBookingTable ebt = new EditBookingTable();
	            	LocalDate ok = LocalDate.now();
	            	Booking vast = ebt.getCustomerBookingWithVehicleID(Controller.customer.getCustomerId(), vid);
	            	ebt.updateBookingStatus(vast.getBookingId(), "maintance");
	            	ert.insertRepair(vast.getBookingId(),vast.getBookingCost(),"maintenance",ok.getDayOfMonth(),ok.getMonthValue(),ok.getYear(),skase);
	            	if(type.equals("car")) {
	            		EditCarTable ect = new  EditCarTable();
	            		try {
							Car lala = ect.rentFirstAvailableCar();
							Booking bs = new Booking(0,vast.getOrderId(),lala.getVehicleId(),vast.getDriverId(),vast.getBookingCost(),vast.getCoveredInsur(),"Active");
							ebt.insertBooking(bs);
	            		} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	}else if(type.equals("mbike")) {
	            		EditMotorBikeTable ect = new  EditMotorBikeTable();
	            		try {
							MotorBike lala = ect.rentFirstAvailableMotorbike();
							Booking bs = new Booking(0,vast.getOrderId(),lala.getVehicleId(),vast.getDriverId(),vast.getBookingCost(),vast.getCoveredInsur(),"Active");
							ebt.insertBooking(bs);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	}else if(type.equals("bike")) {
	            		EditBicycleTable ect = new  EditBicycleTable();
	            		try {
							Bicycle lala = ect.rentFirstAvailableBicycle();
							Booking bs = new Booking(0,vast.getOrderId(),lala.getVehicleId(),vast.getDriverId(),vast.getBookingCost(),vast.getCoveredInsur(),"Active");
							ebt.insertBooking(bs);						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	}else {
	            		EditScooterTable ect = new  EditScooterTable();
	            		try {
							Scooter lala = ect.rentFirstAvailableScooter();
							Booking bs = new Booking(0,vast.getOrderId(),lala.getVehicleId(),vast.getDriverId(),vast.getBookingCost(),vast.getCoveredInsur(),"Active");
							ebt.insertBooking(bs);	            		} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	}
	            }else if(crashButton.isSelected()){
	            	String skase = descriptionField.getText();
	            	EditRepairTable ert = new EditRepairTable();
	            	EditBookingTable ebt = new EditBookingTable();
	            	LocalDate ok = LocalDate.now();
	            	Booking vast = ebt.getCustomerBookingWithVehicleID(Controller.customer.getCustomerId(), vid);
	            	ebt.updateBookingStatus(vast.getBookingId(), "Crashed");
	            	boolean checkInsur = vast.isCoveredInsur();
	            	ert.insertRepair(vast.getBookingId(),vast.getBookingCost(),"Crashed",ok.getDayOfMonth(),ok.getMonthValue(),ok.getYear(),skase);
	            	if(type.equals("car")) {
	            		EditCarTable ect = new  EditCarTable();
	            		try {
							Car lala = ect.rentFirstAvailableCar();
							Booking bs = new Booking(0,vast.getOrderId(),lala.getVehicleId(),vast.getDriverId(),vast.getBookingCost(),vast.getCoveredInsur(),"Active");
							ebt.insertBooking(bs);
	            		} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	}else if(type.equals("mbike")) {
	            		EditMotorBikeTable ect = new  EditMotorBikeTable();
	            		try {
							MotorBike lala = ect.rentFirstAvailableMotorbike();
							Booking bs = new Booking(0,vast.getOrderId(),lala.getVehicleId(),vast.getDriverId(),vast.getBookingCost(),vast.getCoveredInsur(),"Active");
							ebt.insertBooking(bs);
	            		} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	}else if(type.equals("bike")) {
	            		EditBicycleTable ect = new  EditBicycleTable();
	            		try {
							Bicycle lala = ect.rentFirstAvailableBicycle();
							Booking bs = new Booking(0,vast.getOrderId(),lala.getVehicleId(),vast.getDriverId(),vast.getBookingCost(),vast.getCoveredInsur(),"Active");
							ebt.insertBooking(bs);
	            		} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	}else {
	            		EditScooterTable ect = new  EditScooterTable();
	            		try {
							Scooter lala = ect.rentFirstAvailableScooter();
							Booking bs = new Booking(0,vast.getOrderId(),lala.getVehicleId(),vast.getDriverId(),vast.getBookingCost(),vast.getCoveredInsur(),"Active");
							ebt.insertBooking(bs);	            		} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	}
	        
	            	if(!checkInsur) {
	            		int  extracost = vast.getBookingCost()*3;
	            		createExpenseDialog(extracost, vast.getOrderId());
	            	}
	            }
	            vehiclesPanel.removeAll();
				 PrintCars();
			     PrintMbikes();
			     PrintBikes();
			     PrintScooters();
			     vehiclesPanel.revalidate();
			     vehiclesPanel.repaint();
	        }
	    });
	    contentPane.add(submitButton);

	    // Set dialog location relative to the parent window
	    expenseDialog.setLocationRelativeTo(null);

	    // Make the dialog visible
	    expenseDialog.setVisible(true);
	}
	private  void createExpenseDialog(int number, int vid ) {
		JDialog expenseDialog = new JDialog(this, "Add Expense");
	    expenseDialog.setTitle("Add Expense");
	    expenseDialog.setSize(400, 400);
	    expenseDialog.setResizable(false);
	    Container contentPane = expenseDialog.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	    JPanel descriptionPanel = new JPanel();
	    descriptionPanel.setLayout(new FlowLayout());
	    descriptionPanel.add(new JLabel("Exta Costs :" + String.valueOf(number) +" $"));
	    contentPane.add(descriptionPanel);
        // Submit button
        JPanel submitButtonPanel = new JPanel();
        JButton submitButton = new JButton("Pay extra expenses");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Process payment information here
                // Close the dialog
            	expenseDialog.dispose();
            	Payment realting = new Payment();
                realting.setAmount(number);
            	realting.setDate(LocalDate.now());
            	realting.setOrderId(vid);
            	realting.setType("");
            	realting.setTime(LocalTime.now());
            	EditPaymentTable ept = new EditPaymentTable();
            	ept.insertPayment(realting);
            	
            }
			
        });
        submitButtonPanel.add(submitButton);
        contentPane.add(submitButtonPanel);
        contentPane.setVisible(true);
        expenseDialog.setVisible(true);
	    
	}
	

	private void PrintCars() {
		ArrayList<Car>carlist = new ArrayList();
		EditBookingTable ebt = new EditBookingTable();
		ArrayList<Booking> bookings = new ArrayList();
		bookings = ebt.getCustomerBookings(Controller.customer.getCustomerId());
		EditCarTable ect = new EditCarTable();
		for(Booking current: bookings) {
			if(current.getStatus().equals("Active")) {
				try {
					carlist.add(ect.getCarByVehicleID(current.getVehicleId()));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	for (Car haha : carlist) { 
    		if(haha !=null) {
        	String mama = haha.getBrand() +", " +haha.getModel() +", " +haha.getColor() +", Mileage: " +
        			haha.getMileage() +",RegNum : " +haha.getRegNum() +",Total Passengers" +haha.getNumPassengers() 
        			+",Insurance Cost: " +haha.getInsurPrice() +",Type : " +haha.getType();
        	AddVehicles(mama,"car",haha.getVehicleId());   
    		}
        }
    }
    private void PrintMbikes() {
    	ArrayList<MotorBike>mbikelist = new ArrayList();
		EditBookingTable ebt = new EditBookingTable();
		ArrayList<Booking> bookings = new ArrayList();
		bookings = ebt.getCustomerBookings(Controller.customer.getCustomerId());
		EditMotorBikeTable ect = new EditMotorBikeTable();
		for(Booking current: bookings) {
			if(current.getStatus().equals("Active")) {
				try {
					mbikelist.add(ect.getMotorBikeByVehicleID(current.getVehicleId()));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	for (MotorBike haha : mbikelist) {
    		if(haha != null) {
        	String mama = haha.getBrand() +", " +haha.getModel() +", " +haha.getColor() +", Mileage: " +
        			haha.getMileage() +",RegNum : " +haha.getRegNum() + 
        			",Insurance Cost: " +haha.getInsurPrice() ;
        	AddVehicles(mama,"mbike",haha.getVehicleId());
    		}
        }
    }
    private void  PrintBikes() {
    	ArrayList<Bicycle>bikelist = new ArrayList();
		EditBookingTable ebt = new EditBookingTable();
		ArrayList<Booking> bookings = new ArrayList();
		bookings = ebt.getCustomerBookings(Controller.customer.getCustomerId());
		EditBicycleTable ect = new EditBicycleTable();
		for(Booking current: bookings) {
			if(current.getStatus().equals("Active")) {
				try {
					bikelist.add(ect.getBicycleByVehicleID(current.getVehicleId()));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	for (Bicycle haha : bikelist){
    		if(haha!=null) {
        	String mama = haha.getBrand() +", " +haha.getModel() +", " +haha.getColor() +", Mileage: " +
        			haha.getMileage() + 
        			",Insurance Cost: " +haha.getInsurPrice() ;
        	AddVehicles(mama,"bike",haha.getVehicleId());
    		}
    		}
    }
    
    
    private void  PrintScooters() {
    	ArrayList<Scooter>scooterlist = new ArrayList();
		EditBookingTable ebt = new EditBookingTable();
		ArrayList<Booking> bookings = new ArrayList();
		bookings = ebt.getCustomerBookings(Controller.customer.getCustomerId());
		EditScooterTable ect = new EditScooterTable();
		for(Booking current: bookings) {
			if(current.getStatus().equals("Active")) {
				try {
					scooterlist.add(ect.getScooterByVehicleID(current.getVehicleId()));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	for (Scooter haha : scooterlist) {
    		if(haha!=null) {
        	String mama = haha.getBrand() +", " +haha.getModel() +", " +haha.getColor() +", Mileage: " +
        			haha.getMileage() + 
        			",Insurance Cost: " +haha.getInsurPrice() ;
        			AddVehicles(mama,"scooter",haha.getVehicleId());
    		}
    		}
    }
    public long calculateHoursSinceBooking(Order order) {
        LocalDate endDate = order.getEndDate();
        LocalTime endTime = order.getEndTime();

        // Combine endDate and endTime to get LocalDateTime
        LocalDateTime bookingEndDateTime = LocalDateTime.of(endDate, endTime);

        // Combine currentDate and currentTime to get LocalDateTime

        // Calculate duration
        Duration duration = Duration.between(bookingEndDateTime, currentDateTime);

        // Return the duration in hours
        return duration.toHours();
    }

    public LocalDateTime DateReturn(String type) {
    	 JDialog expenseDialog = new JDialog(this, "Add Date ", true);
	    expenseDialog.setTitle("Add Date ");
	    expenseDialog.setSize(400, 400);
	    expenseDialog.setResizable(false);
	    Container contentPane = expenseDialog.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	    JPanel textPanel = new JPanel();
	    JLabel text=new JLabel("Set date for "+type);
	    textPanel.add(text);
	    contentPane.add(textPanel);
	    JPanel DatePanel = new JPanel();
	    DatePanel.setLayout(new FlowLayout());
	    JComboBox<Integer> Day = new JComboBox<>(getDays());
        JComboBox<Integer> Month = new JComboBox<>(getMonths());
        JComboBox<Integer> Year = new JComboBox<>(getYearRange());
        JComboBox<Integer> hour = new JComboBox<>(getHours());
        DatePanel.add(Day);
        DatePanel.add(Month);
        DatePanel.add(Year);
        DatePanel.add(hour);
	    contentPane.add(DatePanel);
        // Submit button
        JPanel submitButtonPanel = new JPanel();
        JButton submitButton = new JButton("Confrirm Date");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Process payment information here
                // Close the dialog
            	expenseDialog.dispose();
               LocalDate selectedDate =  LocalDate.of((Integer)Year.getSelectedItem(),(Integer)Month.getSelectedItem(),(Integer)Day.getSelectedItem());
               int mamacita  = (Integer) hour.getSelectedItem();
               LocalTime pappi = LocalTime.of(mamacita, 0);
               currentDateTime =LocalDateTime.of(selectedDate, pappi);
            }
			
        });
        submitButtonPanel.add(submitButton);
        contentPane.add(submitButtonPanel);
        contentPane.setVisible(true);
        expenseDialog.setVisible(true);
    	return currentDateTime;
    }
    
    private Integer [] getYearRange() {
    	Integer[] years = new Integer[10];
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < years.length; i++) {
            years[i] = currentYear + i;
        }
        return years;
    }
    
    private Integer [] getHours() {
    	Integer[] days = new Integer[24];
        for (int i = 0; i < days.length; i++) {
            days[i] =  i+1;
        }
        return days;
    }private Integer [] getDays() {
    	Integer[] days = new Integer[30];
        for (int i = 1; i < days.length; i++) {
            days[i-1] =  i;
        }
        return days;
    }
    private Integer [] getMonths() {
    	Integer[] months = new Integer[12];
        for (int i = 1; i < months.length; i++) {
        	months[i-1] =  i;
        }
        return months;
    }

}
