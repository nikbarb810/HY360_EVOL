package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminPage extends JFrame {
    JPanel StatisticsPanel;

    public AdminPage() {
        setTitle("Admin Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 600));

        // Top panel with label
        JPanel topPanel = new JPanel();
        JLabel text = new JLabel("Statistics");
        text.setFont(new Font("Helvetica", Font.BOLD, 26));
        text.setForeground(Color.BLACK);
        topPanel.add(text);

        // Middle panel for vehicle rows
        StatisticsPanel = new JPanel();
        StatisticsPanel.setLayout(new BoxLayout(StatisticsPanel, BoxLayout.Y_AXIS));

        // Bottom panel with button
        JPanel bottomPanel = new JPanel();
        JButton sub = new JButton("Add A Car");
        sub.setBackground(Color.cyan);
        sub.setForeground(Color.black);
        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createAddCarDialog();
            }
        });
        bottomPanel.add(sub);
        JButton sub2 = new JButton("Add A MotorBike");
        sub2.setBackground(Color.cyan);
        sub2.setForeground(Color.black);
        sub2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createAddMotorBikeDialog();
            }
        });
        bottomPanel.add(sub2);
        JButton sub3 = new JButton("Add A Bicycle");
        sub3.setBackground(Color.cyan);
        sub3.setForeground(Color.black);
        sub3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createAddBicycleDialog();
            }
        });
        bottomPanel.add(sub3);
        JButton sub4 = new JButton("Add A Scooter");
        sub4.setBackground(Color.cyan);
        sub4.setForeground(Color.black);
        sub4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createAddScooterDialog();
            }
        });
        bottomPanel.add(sub4);

        // Adding panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(StatisticsPanel), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    protected void createAddScooterDialog() {
		// TODO Auto-generated method stub
    	JDialog addVehicleDialog = new JDialog(this, "Add Scooter", true);
	    addVehicleDialog.setSize(400, 300); // Adjusted for additional space
	    addVehicleDialog.setResizable(true);

	    // Use BoxLayout for vertical stacking
	    Container contentPane = addVehicleDialog.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

	    // Add fields and labels to the dialog
	    contentPane.add(createLabelFieldPair("Vehicle Brand:", 15));
	    contentPane.add(createLabelFieldPair("Vehicle Model:", 15));
	    contentPane.add(createLabelFieldPair("Color:", 15));
	    contentPane.add(createLabelFieldPair("Rental Price:", 15));
	    contentPane.add(createLabelFieldPair("Insur Price:", 15));
	    contentPane.add(createLabelFieldPair("Mileage:", 15));

	    // Add submit button
	    JButton submitButton = new JButton("Submit");
	    submitButton.addActionListener(e -> {
	        // Process the vehicle data here
	        // ...
	        addVehicleDialog.dispose(); // Close the dialog
	    });
	    contentPane.add(submitButton);

	    // Set dialog location relative to the parent window
	    addVehicleDialog.setLocationRelativeTo(this);

	    // Make the dialog visible
	    addVehicleDialog.setVisible(true);
		
	}

	protected void createAddBicycleDialog() {
		// TODO Auto-generated method stub
		JDialog addVehicleDialog = new JDialog(this, "Add Bicycle", true);
	    addVehicleDialog.setSize(400, 300); // Adjusted for additional space
	    addVehicleDialog.setResizable(true);

	    // Use BoxLayout for vertical stacking
	    Container contentPane = addVehicleDialog.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

	    // Add fields and labels to the dialog
	    contentPane.add(createLabelFieldPair("Vehicle Brand:", 15));
	    contentPane.add(createLabelFieldPair("Vehicle Model:", 15));
	    contentPane.add(createLabelFieldPair("Color:", 15));
	    contentPane.add(createLabelFieldPair("Rental Price:", 15));
	    contentPane.add(createLabelFieldPair("Insur Price:", 15));
	    contentPane.add(createLabelFieldPair("Mileage:", 15));

	    // Add submit button
	    JButton submitButton = new JButton("Submit");
	    submitButton.addActionListener(e -> {
	        // Process the vehicle data here
	        // ...
	        addVehicleDialog.dispose(); // Close the dialog
	    });
	    contentPane.add(submitButton);

	    // Set dialog location relative to the parent window
	    addVehicleDialog.setLocationRelativeTo(this);

	    // Make the dialog visible
	    addVehicleDialog.setVisible(true);
	}

	protected void createAddMotorBikeDialog() {
		// TODO Auto-generated method stub
		JDialog addVehicleDialog = new JDialog(this, "Add Motorbike", true);
	    addVehicleDialog.setSize(400, 300); // Adjusted for additional space
	    addVehicleDialog.setResizable(true);

	    // Use BoxLayout for vertical stacking
	    Container contentPane = addVehicleDialog.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

	    // Add fields and labels to the dialog
	    contentPane.add(createLabelFieldPair("Vehicle Brand:", 15));
	    contentPane.add(createLabelFieldPair("Vehicle Model:", 15));
	    contentPane.add(createLabelFieldPair("Color:", 15));
	    contentPane.add(createLabelFieldPair("Rental Price:", 15));
	    contentPane.add(createLabelFieldPair("Insur Price:", 15));
	    contentPane.add(createLabelFieldPair("Registration Number:", 15));
	    contentPane.add(createLabelFieldPair("Mileage:", 15));

	    // Add submit button
	    JButton submitButton = new JButton("Submit");
	    submitButton.addActionListener(e -> {
	        // Process the vehicle data here
	        // ...
	        addVehicleDialog.dispose(); // Close the dialog
	    });
	    contentPane.add(submitButton);

	    // Set dialog location relative to the parent window
	    addVehicleDialog.setLocationRelativeTo(this);

	    // Make the dialog visible
	    addVehicleDialog.setVisible(true);
		
	}

	private void createAddCarDialog() {
	    // Create and display the pop-up dialog for adding a vehicle
	    JDialog addVehicleDialog = new JDialog(this, "Add Car", true);
	    addVehicleDialog.setSize(400, 300); // Adjusted for additional space
	    addVehicleDialog.setResizable(true);

	    // Use BoxLayout for vertical stacking
	    Container contentPane = addVehicleDialog.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

	    // Add fields and labels to the dialog
	    contentPane.add(createLabelFieldPair("Vehicle Brand:", 15));
	    contentPane.add(createLabelFieldPair("Vehicle Model:", 15));
	    contentPane.add(createLabelFieldPair("Color:", 15));
	    contentPane.add(createLabelFieldPair("Rental Price:", 15));
	    contentPane.add(createLabelFieldPair("Insur Price:", 15));
	    contentPane.add(createLabelFieldPair("Registration Number:", 15));
	    contentPane.add(createLabelFieldPair("Type of car:", 15));
	    contentPane.add(createLabelFieldPair("Num of Passengers:", 15));
	    contentPane.add(createLabelFieldPair("Mileage:", 15));

	    // Add submit button
	    JButton submitButton = new JButton("Submit");
	    submitButton.addActionListener(e -> {
	        // Process the vehicle data here
	        // ...
	        addVehicleDialog.dispose(); // Close the dialog
	    });
	    contentPane.add(submitButton);

	    // Set dialog location relative to the parent window
	    addVehicleDialog.setLocationRelativeTo(this);

	    // Make the dialog visible
	    addVehicleDialog.setVisible(true);
	}

	private JPanel createLabelFieldPair(String labelText, int textFieldSize) {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
	    panel.add(new JLabel(labelText));
	    panel.add(Box.createRigidArea(new Dimension(5, 0))); // Space between label and text field
	    panel.add(new JTextField(textFieldSize));
	    return panel;
	}

}
