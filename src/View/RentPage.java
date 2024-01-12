package View;

import javax.swing.*;

import Controller.Controller;
import model.Driver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        
        topPanel.add(carsCheckbox);
        topPanel.add(motorbikeCheckbox);
        topPanel.add(motorcycleCheckbox);
        topPanel.add(scootersCheckbox);
        

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
                if (cb.isSelected()) {
                	cars = true;
                	updateText(selectedTextArea);
                	 repaint();
                } else {
                    // check box is unselected, do something else
                	cars = false;
                	updateText(selectedTextArea);
                	vehiclesPanel.removeAll();
                	 repaint();
                }
            }
        });
        motorbikeCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if (cb.isSelected()) {
                	mbikes = true;
                	updateText(selectedTextArea);
                	 repaint();
                } else {
                    // check box is unselected, do something else
                	mbikes = false;
                	updateText(selectedTextArea);
                	 repaint();
                }
            }
        });
        motorcycleCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if (cb.isSelected()) {
                	bikes = true;
                	updateText(selectedTextArea);
                	 repaint();
                } else {
                    // check box is unselected, do something else
                	bikes = false;
                	updateText(selectedTextArea);
                	 repaint();
                }
            }
        });
        scootersCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if (cb.isSelected()) {
                	scooter = true;
                	updateText(selectedTextArea);
                	 repaint();
                } else {
                    // check box is unselected, do something else
                	scooter = false;
                	updateText(selectedTextArea);
                	 repaint();
                }
            }
        });

        pack();
        setLocationRelativeTo(null); // Center the frame
    }
    private void updateText(JTextArea selectedLabel) {
    	vehiclesPanel.removeAll();
   	 	repaint();
    	String ok = "";
        if (cars) {
            ok += "Cars,";
            addVehicleRow("PORCHE F16");
            addVehicleRow("Motorolla");
        }
        if (mbikes) {
            ok += "MotorBikes,";
        }
        if (bikes) {
            ok += "Bicycles,";
        }
        if (scooter) {
            ok += "Scooter,";
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
}
