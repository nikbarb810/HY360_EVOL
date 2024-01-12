package View;

import javax.swing.*;

import Controller.Controller;
import Database.tables.EditBicycleTable;
import Database.tables.EditCarTable;
import Database.tables.EditMotorBikeTable;
import Database.tables.EditScooterTable;
import model.Bicycle;
import model.Car;
import model.Driver;
import model.MotorBike;
import model.Scooter;
import model.Vehicle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public  class RentPage extends JFrame {
    private JCheckBox carsCheckbox, scootersCheckbox, motorbikeCheckbox, motorcycleCheckbox;
    private JTextArea selectedTextArea;
    private JPanel vehiclesPanel;
    boolean cars = false;
    boolean mbikes = false;
    boolean bikes = false;
    boolean scooter = false;
    RentPage myself = this;
    boolean pickdate = false;
    ArrayList<Vehicle>rentlist = new ArrayList();
    ArrayList<Car>carlist = new ArrayList();
    ArrayList<MotorBike>mbikelist = new ArrayList();
    ArrayList<Bicycle>bikelist = new ArrayList();
    ArrayList<Scooter>scooterlist = new ArrayList();
    public RentPage() {
        setTitle("Rent Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 600));
        // Top panel with checkboxes
        JPanel topPanel = new JPanel();
        carsCheckbox = new JCheckBox("Cars");
        scootersCheckbox = new JCheckBox("Scooter");
        motorbikeCheckbox = new JCheckBox("Bicycle");
        motorcycleCheckbox = new JCheckBox("Scooter");
        JButton date = new JButton("Dates");
        date.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JDialog dialog = new JDialog();
                dialog.setTitle("Select Dates");
                dialog.setLayout(new FlowLayout());
                dialog.setSize(400, 200);

                // From Date Panel
                JPanel fromDatePanel = new JPanel();
                fromDatePanel.add(new JLabel("From Date:"));
                JComboBox<Integer> fromDay = new JComboBox<>(getDays());
                JComboBox<Integer> fromMonth = new JComboBox<>(getMonths());
                JComboBox<Integer> fromYear = new JComboBox<>(getYearRange());
                JComboBox<Integer> fromHour = new JComboBox<>(getHourRange());
                fromDatePanel.add(fromDay);
                fromDatePanel.add(fromMonth);
                fromDatePanel.add(fromYear);
                fromDatePanel.add(fromHour);

                // To Date Panel
                JPanel toDatePanel = new JPanel();
                toDatePanel.add(new JLabel("To Date:"));
                JComboBox<Integer> toDay = new JComboBox<>(getDays());
                JComboBox<Integer> toMonth = new JComboBox<>(getMonths());
                JComboBox<Integer> toYear = new JComboBox<>(getYearRange());
                JComboBox<Integer> toHour = new JComboBox<>(getHourRange());
                toDatePanel.add(toDay);
                toDatePanel.add(toMonth);
                toDatePanel.add(toYear);
                toDatePanel.add(toHour);
                // Add panels to dialog
                dialog.add(fromDatePanel);
                dialog.add(toDatePanel);
                JPanel submitButtonPanel = new JPanel();
                JButton submitButton = new JButton("Confirm");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Process payment information here
                        // Close the dialog
                        dialog.dispose();
                        if(pickdate ==true) {
                        	try {
								updateText(selectedTextArea);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                        }
                        LocalDate from =  LocalDate.of((Integer)fromYear.getSelectedItem(),(Integer)fromMonth.getSelectedItem(),(Integer)fromDay.getSelectedItem());
                        LocalDate to =  LocalDate.of((Integer)toYear.getSelectedItem(),(Integer)toMonth.getSelectedItem(),(Integer)toDay.getSelectedItem());
                        Controller.cart.setStartDate(from);
                        Controller.cart.setEndDate(to);
                        pickdate = true;
                    }
					
                });
                submitButtonPanel.add(submitButton);
                dialog.add(submitButtonPanel);

                // Display the dialog
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        
        topPanel.add(carsCheckbox);
        topPanel.add(motorbikeCheckbox);
        topPanel.add(motorcycleCheckbox);
        topPanel.add(scootersCheckbox);
        topPanel.add(date);
        

        // Middle panel for vehicle rows
        vehiclesPanel = new JPanel();
        vehiclesPanel.setLayout(new BoxLayout(vehiclesPanel, BoxLayout.Y_AXIS));
        // addVehicleRow("Car Model 1");

        // Bottom panel with selected text area
        JPanel bottomPanel = new JPanel();
        JLabel selectedLabel = new JLabel("Selected: ");
        selectedTextArea = new JTextArea(2, 20);
        selectedTextArea.setEditable(false);

        bottomPanel.add(selectedLabel);
        bottomPanel.add(selectedTextArea);

        // Adding panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(vehiclesPanel), BorderLayout.CENTER);
        JButton sub = new JButton("Submit");
        sub.setBackground(Color.cyan);
        sub.setForeground(Color.black);
        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JDialog dialog = new JDialog();
                dialog.setTitle("Add Payment");
                dialog.setSize(350, 200);
                dialog.setResizable(false);
                dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

                // Add Payment label at the top
                JPanel topPanel = new JPanel();
                topPanel.add(new JLabel("Confirm Payment"));
                dialog.add(topPanel);

                // Submit button
                JPanel submitButtonPanel = new JPanel();
                JButton submitButton = new JButton("Confirm");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Process payment information here
                        // Close the dialog
                        dialog.dispose();
                        myself.dispose();
                    }
					
                });
                submitButtonPanel.add(submitButton);
                dialog.add(submitButtonPanel);

                // Set the dialog location relative to the parent window
                dialog.setLocationRelativeTo(null);

                // Make the dialog visible
                dialog.setVisible(true);
            }
        });
        bottomPanel.add(sub);
        add(bottomPanel, BorderLayout.SOUTH);
        
        carsCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if(pickdate ==true) {
                if (cb.isSelected()) {
                	cars = true;
                	try {
						updateText(selectedTextArea);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	 repaint();
                } else {
                    // check box is unselected, do something else
                	cars = false;
                	try {
						updateText(selectedTextArea);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	vehiclesPanel.removeAll();
                	 repaint();
                }
                }else {
                	cb.setSelected(false);
                }
            }
        });
        motorbikeCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if(pickdate == true) {
                if (cb.isSelected()) {
                	mbikes = true;
                	try {
						updateText(selectedTextArea);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	 repaint();
                } else {
                    // check box is unselected, do something else
                	mbikes = false;
                	try {
						updateText(selectedTextArea);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	 repaint();
                }
                }else {
                	cb.setSelected(false);
                }
            }
        });
        motorcycleCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if(pickdate == true) {
                if (cb.isSelected()) {
                	bikes = true;
                	try {
						updateText(selectedTextArea);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	 repaint();
                } else {
                    // check box is unselected, do something else
                	bikes = false;
                	try {
						updateText(selectedTextArea);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	 repaint();
                }
            }else {
            	cb.setSelected(false);
            }
            }
        });
        scootersCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if(pickdate == true) {
                if (cb.isSelected()) {
                	scooter = true;
                	try {
						updateText(selectedTextArea);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	 repaint();
                } else {
                    // check box is unselected, do something else
                	scooter = false;
                	try {
						updateText(selectedTextArea);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	 repaint();
                }
                }else {
                	cb.setSelected(false);
                }
            }
        });

        pack();
        setLocationRelativeTo(null); // Center the frame
    }
    private void updateText(JTextArea selectedLabel) throws ClassNotFoundException, SQLException {
    	vehiclesPanel.removeAll();
   	 	repaint();
    	String ok = "";
    	carlist.clear();
    	mbikelist.clear();
    	bikelist.clear();
    	scooterlist.clear();
        if (cars) {
            ok += "Cars,";
           EditCarTable ec = new EditCarTable();
           carlist =  ec.getAllAvailableCars(Controller.cart.getStartDate());
           PrintCars();
        }
        if (mbikes) {
            ok += "MotorBikes,";
            EditMotorBikeTable ec = new EditMotorBikeTable();
            mbikelist  = ec.getAllAvailableMotorbikes(Controller.cart.getStartDate());
            PrintMbikes();
        }
        if (bikes) {
            ok += "Bicycles,";
            EditBicycleTable ec = new EditBicycleTable();
            bikelist = ec.getAllAvailableBicycles(Controller.cart.getStartDate());
            PrintBikes();
        }
        if (scooter) {
            ok += "Scooter,";
            EditScooterTable ec = new EditScooterTable();
            scooterlist = ec.getAllAvailableScooters(Controller.cart.getStartDate());
            PrintScooters();
        }

        // Remove the last comma if it exists
        if (!ok.isEmpty() && ok.charAt(ok.length() - 1) == ',') {
            ok = ok.substring(0, ok.length() - 1) + ' ';
        }

        selectedLabel.setText(ok);
    }

    private void addVehicleRow(String vehicleInfo) {
    	JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setBackground(Color.BLACK);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Adjust height as needed

        // Inner panel to hold label and button
        JPanel innerPanel = new JPanel();
        innerPanel.setOpaque(false); // So the black background of the outer panel is visible
        JLabel mycar =new JLabel(vehicleInfo);
        mycar.setForeground(Color.WHITE);
        innerPanel.add(mycar);
        JCheckBox InsurBox = new JCheckBox("Pay insurance");
        innerPanel.add(InsurBox);
        JButton rentButton = new JButton("Rent");
        rentButton.setBackground(Color.white);
        rentButton.setForeground(Color.black);
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the pop-up dialog
                JDialog dialog = new JDialog();
                dialog.setTitle("Select a Driver");
                dialog.setSize(400, 300);
                dialog.setResizable(false);
                Container dialogContentPane = dialog.getContentPane();
                dialogContentPane.setLayout(new BoxLayout(dialogContentPane, BoxLayout.Y_AXIS));

                // Sample list of drivers
                ArrayList<Driver> drivers = getDriversList(); // You need to implement getDriversList

                for (Driver driver : drivers) {
                    JPanel driverRow = new JPanel();
                    driverRow.setLayout(new FlowLayout());

                    JLabel nameLabel = new JLabel("Name: " + driver.FirstName);
                    JLabel surnameLabel = new JLabel("Surname: " + driver.LastName);
                    JLabel licenseLabel = new JLabel("License: " + driver.getLicenseId());

                    JCheckBox selectCheckbox = new JCheckBox();
                    selectCheckbox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.dispose(); // Close the dialog when a checkbox is selected
                        }
                    });

                    driverRow.add(nameLabel);
                    driverRow.add(surnameLabel);
                    driverRow.add(licenseLabel);
                    driverRow.add(selectCheckbox);

                    dialogContentPane.add(driverRow);
                }

                // Set the dialog location relative to the parent window
                dialog.setLocationRelativeTo(RentPage.this);

                // Make the dialog visible
                dialog.setVisible(true);
            }
        });

        innerPanel.add(rentButton);

        // Add inner panel to the center of the row panel
        row.add(Box.createHorizontalGlue()); // Pushes content to center
        row.add(innerPanel);
        row.add(Box.createHorizontalGlue()); // Pushes content to center

        // Add the row to the vehicles panel
        vehiclesPanel.add(row);

        // Add spacing between rows
        vehiclesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    private ArrayList<Driver> getDriversList() {
    	Driver me = new Driver();
    	me.FirstName = "Ilias";
    	me.LastName = "Kapsis";
    	me.LicenseId = 1833;
    	ArrayList<Driver> mama  = new ArrayList();
    	mama.add(me);
		return mama;
    	
    }
    private Integer [] getYearRange() {
    	Integer[] years = new Integer[10];
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < years.length; i++) {
            years[i] = currentYear + i;
        }
        return years;
    }
    private Integer [] getDays() {
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
    private Integer [] getHourRange() {
    	Integer[] months = new Integer[24];
        for (int i = 1; i < months.length; i++) {
        	months[i-1] =  i;
        }
        return months;
    }
    private void PrintCars() {
    	for (Car haha : carlist) {
        	String mama = haha.getBrand() +", " +haha.getModel() +", " +haha.getColor() +", Mileage: " +
        			haha.getMileage() +",RegNum : " +haha.getRegNum() +",Total Passengers" +haha.getNumPassengers() 
        			+",Insurance Cost: " +haha.getInsurPrice() +",Type : " +haha.getType();
        	addVehicleRow(mama);
        }
    }
    private void PrintMbikes() {
    	for (MotorBike haha : mbikelist) {
        	String mama = haha.getBrand() +", " +haha.getModel() +", " +haha.getColor() +", Mileage: " +
        			haha.getMileage() +",RegNum : " +haha.getRegNum() + 
        			",Insurance Cost: " +haha.getInsurPrice() ;
        	addVehicleRow(mama);
        }
    }
    private void  PrintBikes() {
    	for (Bicycle haha : bikelist) {
        	String mama = haha.getBrand() +", " +haha.getModel() +", " +haha.getColor() +", Mileage: " +
        			haha.getMileage() + 
        			",Insurance Cost: " +haha.getInsurPrice() ;
        	addVehicleRow(mama);
        }
    }
    private void  PrintScooters() {
    	for (Scooter haha : scooterlist) {
        	String mama = haha.getBrand() +", " +haha.getModel() +", " +haha.getColor() +", Mileage: " +
        			haha.getMileage() + 
        			",Insurance Cost: " +haha.getInsurPrice() ;
        	addVehicleRow(mama);
        }
    }
}
