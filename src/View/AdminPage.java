package View;

import Database.tables.*;
import model.Bicycle;
import model.Car;
import model.MotorBike;
import model.Scooter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

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

        // Configure the StatisticsPanel to use BorderLayout
        StatisticsPanel = new JPanel();
        StatisticsPanel.setLayout(new GridLayout(2, 1)); // 2 rows, 1 column

        // Upper row with 2 columns
        JPanel upperRow = new JPanel(new GridLayout(1, 2)); // 1 row, 2 columns
        JPanel upperLeftPanel = new JPanel();
        buildUpperLeftPanel(upperLeftPanel);
        upperLeftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel upperRightPanel = new JPanel();
        upperRightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        upperRow.add(upperLeftPanel);
        upperRow.add(upperRightPanel);

        // Lower row with 4 columns
        JPanel lowerRow = new JPanel(new GridLayout(1, 4)); // 1 row, 4 columns
        JPanel lowerLeftPanel = new JPanel();
        lowerLeftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel lowerCenterLeftPanel = new JPanel();
        lowerCenterLeftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel lowerCenterRightPanel = new JPanel();
        lowerCenterRightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel lowerRightPanel = new JPanel();
        lowerRightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lowerRow.add(lowerLeftPanel);
        lowerRow.add(lowerCenterLeftPanel);
        lowerRow.add(lowerCenterRightPanel);
        lowerRow.add(lowerRightPanel);

        // Add the upper and lower rows to the StatisticsPanel
        StatisticsPanel.add(upperRow);
        StatisticsPanel.add(lowerRow);

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

        // New button for querying the database
        JButton queryButton = new JButton("Query");
        queryButton.setBackground(Color.green);
        queryButton.setForeground(Color.black);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createQueryDatabaseDialog();
            }
        });
        bottomPanel.add(queryButton);



        // Adding panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(StatisticsPanel), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    private void buildUpperLeftPanel(JPanel upperLeftPanel) {
        // Set the layout manager for upperLeftPanel
        upperLeftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        Color titleBackgroundColor = new Color(220, 220, 220);
        Color optionsBackgroundColor = new Color(240, 240, 240);
        Color contentBackgroundColor = new Color(255, 255, 255);

        // Title Panel with label
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.PAGE_AXIS));
        titlePanel.setBackground(titleBackgroundColor);
        JLabel titleLabel = new JLabel("View Vehicles");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);

        // Options Panel with dropdown
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center the panel on the x-axis
        optionsPanel.setBackground(optionsBackgroundColor);
        String[] vehicleOptions = {"Select Vehicle", "Car", "Motorbike", "Bicycle", "Scooter"};
        JComboBox<String> vehicleDropDown = new JComboBox<>(vehicleOptions);
        optionsPanel.add(vehicleDropDown);

        // Third Panel - Empty for now
        emptyPanel = new JPanel();
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setBackground(contentBackgroundColor);

        // Title Panel Constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the title panel
        gbc.fill = GridBagConstraints.BOTH;
        upperLeftPanel.add(titlePanel, gbc);

        // Options Panel Constraints
        gbc.gridy = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the options panel
        upperLeftPanel.add(optionsPanel, gbc);

        // Empty Panel Constraints
        gbc.gridy = 2;
        gbc.weighty = 0.8; // Allocate 80% of the space to the empty panel
        upperLeftPanel.add(emptyPanel, gbc);

        // Add ActionListener to the JComboBox
        vehicleDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedVehicle = (String) vehicleDropDown.getSelectedItem();
                // Make sure "Select Vehicle" is not processed
                if (!"Select Vehicle".equals(selectedVehicle)) {
                    populateVehiclePanel(selectedVehicle);
                }
            }
        });
    }


    JPanel emptyPanel; // Declare the empty panel as a class variable

    // Now let's define the populateVehiclePanel method
    private void populateVehiclePanel(String vehicleType) {
        // Clear the previous content
        emptyPanel.removeAll();

        try {
            ArrayList<?> vehicles = null;
            switch (vehicleType) {
                case "Car":
                    EditCarTable ect = new EditCarTable();
                    vehicles = ect.getAllCars();
                    break;
                case "Motorbike":
                    EditMotorBikeTable emt = new EditMotorBikeTable();
                    vehicles = emt.getAllMotorBikes();
                    break;
                case "Bicycle":
                    EditBicycleTable ebt = new EditBicycleTable();
                    vehicles = ebt.getAllBicycles();
                    break;
                case "Scooter":
                    EditScooterTable est = new EditScooterTable();
                    vehicles = est.getAllScooters();
                    break;
            }

            if (vehicles != null) {
                for (Object vehicle : vehicles) {
                    JPanel vehiclePanel = new JPanel();
                    vehiclePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    vehiclePanel.add(new JLabel(vehicle.toString())); // Customize this to display vehicle details
                    emptyPanel.add(vehiclePanel);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Handle exceptions properly
        }

        // Revalidate and repaint to update the UI
        emptyPanel.revalidate();
        emptyPanel.repaint();
    }

    private void createQueryDatabaseDialog() {
        // Create the dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Query Database");
        dialog.setLayout(new BorderLayout());
        dialog.setPreferredSize(new Dimension(400, 100));

        // Create input field
        JTextField inputField = new JTextField(30);

        // Create a panel for the input field and add it to the dialog
        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        dialog.add(inputPanel, BorderLayout.CENTER);

        // Create a submit button
        JButton submitQuery = new JButton("Submit Query");
        submitQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = inputField.getText();
                // Execute the query and get the results
                EditVehicleSequenceTable evt = new EditVehicleSequenceTable();
                try {
                    List<Map<String, Object>> results = evt.executeSelectQuery(query);
                    // Show the results in a new dialog
                    showResultsInDialog(results);
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    // Show error message
                    JOptionPane.showMessageDialog(dialog, "Error executing query: " + ex.getMessage(), "Query Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the submit button to the dialog
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitQuery);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Finalize and display the dialog
        dialog.pack();
        dialog.setLocationRelativeTo(null); // Center the dialog
        dialog.setVisible(true);
    }

    private void showResultsInDialog(List<Map<String, Object>> results) {
        // Create a new dialog to show the results
        JDialog resultsDialog = new JDialog();
        resultsDialog.setTitle("Query Results");
        resultsDialog.setLayout(new BorderLayout());

        // Set the dialog resizable and maximizable
        resultsDialog.setResizable(true);
        resultsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create the main panel with BoxLayout for vertical stacking
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // ScrollPane for mainPanel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        resultsDialog.add(scrollPane, BorderLayout.CENTER);

        // Format and append the results to the main panel
        for (Map<String, Object> row : results) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            rowPanel.setBorder(BorderFactory.createEtchedBorder());

            for (Map.Entry<String, Object> entry : row.entrySet()) {
                JTextArea textArea = new JTextArea(entry.getKey() + ": " + entry.getValue());
                textArea.setEditable(false);
                rowPanel.add(textArea);
            }
            mainPanel.add(rowPanel);
        }


        // Finalize and display the results dialog
        resultsDialog.pack();
        resultsDialog.setLocationRelativeTo(null); // Center the results dialog
        resultsDialog.setVisible(true);
    }

    private void toggleFullScreen(JDialog dialog) {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (dialog.isUndecorated()) {
            dialog.dispose();
            dialog.setUndecorated(false);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } else {
            dialog.dispose();
            dialog.setUndecorated(true);
            device.setFullScreenWindow(dialog);
            dialog.setVisible(true);
        }
    }


    protected void createAddScooterDialog() {
        JDialog addVehicleDialog = new JDialog(this, "Add Scooter", true);
        addVehicleDialog.setResizable(true);

        Container contentPane = addVehicleDialog.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JTextField brandField = createLabelFieldPair("Vehicle Brand:", 15, contentPane);
        JTextField modelField = createLabelFieldPair("Vehicle Model:", 15, contentPane);
        JTextField colorField = createLabelFieldPair("Color:", 15, contentPane);
        JTextField rentalPriceField = createLabelFieldPair("Rental Price:", 15, contentPane);
        JTextField insurPriceField = createLabelFieldPair("Insur Price:", 15, contentPane);
        JTextField mileageField = createLabelFieldPair("Mileage:", 15, contentPane);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            try {
                String brand = brandField.getText();
                String model = modelField.getText();
                String color = colorField.getText();
                int rentalPrice = Integer.parseInt(rentalPriceField.getText());
                int insurPrice = Integer.parseInt(insurPriceField.getText());
                int mileage = Integer.parseInt(mileageField.getText());

                Scooter newScooter = new Scooter(0, brand, model, color, rentalPrice,"Available", insurPrice, mileage);

                EditScooterTable est = new EditScooterTable();
                est.insertScooter(newScooter);

                addVehicleDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addVehicleDialog, "Please enter valid numbers.");
            }
        });
        contentPane.add(submitButton);

        addVehicleDialog.pack();
        addVehicleDialog.setLocationRelativeTo(this);
        addVehicleDialog.setVisible(true);
    }

    protected void createAddBicycleDialog() {
        JDialog addVehicleDialog = new JDialog(this, "Add Bicycle", true);
        addVehicleDialog.setResizable(true);

        Container contentPane = addVehicleDialog.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JTextField brandField = createLabelFieldPair("Vehicle Brand:", 15, contentPane);
        JTextField modelField = createLabelFieldPair("Vehicle Model:", 15, contentPane);
        JTextField colorField = createLabelFieldPair("Color:", 15, contentPane);
        JTextField rentalPriceField = createLabelFieldPair("Rental Price:", 15, contentPane);
        JTextField insurPriceField = createLabelFieldPair("Insur Price:", 15, contentPane);
        JTextField mileageField = createLabelFieldPair("Mileage:", 15, contentPane);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            try {
                String brand = brandField.getText();
                String model = modelField.getText();
                String color = colorField.getText();
                int rentalPrice = Integer.parseInt(rentalPriceField.getText());
                int insurPrice = Integer.parseInt(insurPriceField.getText());
                int mileage = Integer.parseInt(mileageField.getText());

                Bicycle newBicycle = new Bicycle(0, brand, model, color, rentalPrice, "Available", insurPrice, mileage);

                EditBicycleTable ebt = new EditBicycleTable();
                ebt.insertBicycle(newBicycle);

                addVehicleDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addVehicleDialog, "Please enter valid numbers.");
            }
        });
        contentPane.add(submitButton);

        addVehicleDialog.pack();
        addVehicleDialog.setLocationRelativeTo(this);
        addVehicleDialog.setVisible(true);
    }
    protected void createAddMotorBikeDialog() {
        JDialog addVehicleDialog = new JDialog(this, "Add Motorbike", true);
        addVehicleDialog.setResizable(true);

        Container contentPane = addVehicleDialog.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JTextField brandField = createLabelFieldPair("Vehicle Brand:", 15, contentPane);
        JTextField modelField = createLabelFieldPair("Vehicle Model:", 15, contentPane);
        JTextField colorField = createLabelFieldPair("Color:", 15, contentPane);
        JTextField rentalPriceField = createLabelFieldPair("Rental Price:", 15, contentPane);
        JTextField insurPriceField = createLabelFieldPair("Insur Price:", 15, contentPane);
        JTextField regNumberField = createLabelFieldPair("Registration Number:", 15, contentPane);
        JTextField mileageField = createLabelFieldPair("Mileage:", 15, contentPane);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            try {
                String brand = brandField.getText();
                String model = modelField.getText();
                String color = colorField.getText();
                int rentalPrice = Integer.parseInt(rentalPriceField.getText());
                int insurPrice = Integer.parseInt(insurPriceField.getText());
                int regNumber = Integer.parseInt(regNumberField.getText());
                int mileage = Integer.parseInt(mileageField.getText());

                MotorBike newMotorBike = new MotorBike(0, brand, model, color, rentalPrice, "Available", insurPrice, regNumber, mileage);

                EditMotorBikeTable emt = new EditMotorBikeTable();
                emt.insertMotorBikeTable(newMotorBike);

                addVehicleDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addVehicleDialog, "Please enter valid numbers.");
            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(addVehicleDialog, "Error adding motorbike to database.");
            }
        });
        contentPane.add(submitButton);

        addVehicleDialog.pack();
        addVehicleDialog.setLocationRelativeTo(this);
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

		// Fields declarations
		JTextField brandField = createLabelFieldPair("Vehicle Brand:", 15, contentPane);
		JTextField modelField = createLabelFieldPair("Vehicle Model:", 15, contentPane);
		JTextField colorField = createLabelFieldPair("Color:", 15, contentPane);
		JTextField rentalPriceField = createLabelFieldPair("Rental Price:", 15, contentPane);
		JTextField insurPriceField = createLabelFieldPair("Insur Price:", 15, contentPane);
		JTextField regNumberField = createLabelFieldPair("Registration Number:", 15, contentPane);
		JTextField typeField = createLabelFieldPair("Type of car:", 15, contentPane);
		JTextField numPassengersField = createLabelFieldPair("Num of Passengers:", 15, contentPane);
		JTextField mileageField = createLabelFieldPair("Mileage:", 15, contentPane);

		// Add submit button
		JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		submitButton.addActionListener(e -> {
			try {
				// Retrieve values from fields and parse integer values
				String brand = brandField.getText();
				String model = modelField.getText();
				String color = colorField.getText();
				int rentalPrice = Integer.parseInt(rentalPriceField.getText());
				int insurPrice = Integer.parseInt(insurPriceField.getText());
				int regNumber = Integer.parseInt(regNumberField.getText());
				String type = typeField.getText();
				int numPassengers = Integer.parseInt(numPassengersField.getText());
				int mileage = Integer.parseInt(mileageField.getText());

				// Assuming vehicleID is auto-generated or not needed for this operation
				Car newCar = new Car(0, brand, model, color, rentalPrice, "Available", insurPrice, regNumber,type, numPassengers, mileage);

				// Insert the new car into the database
				EditCarTable ect = new EditCarTable();
				ect.insertCarTable(newCar);

				// Dispose the dialog
				addVehicleDialog.dispose();
			} catch (NumberFormatException ex) {
				// Handle invalid number format
				JOptionPane.showMessageDialog(addVehicleDialog, "Please enter valid numbers.");
			} catch (SQLException | ClassNotFoundException ex) {
				// Handle SQL and ClassNotFound exceptions
				JOptionPane.showMessageDialog(addVehicleDialog, "Error adding car to database.");
			}
		});
		contentPane.add(submitButton);

        // Pack the components within the dialog
        addVehicleDialog.pack();

		// Set dialog location relative to the parent window
		addVehicleDialog.setLocationRelativeTo(this);

		// Make the dialog visible
		addVehicleDialog.setVisible(true);
	}

	private JTextField createLabelFieldPair(String labelText, int fieldSize, Container contentPane) {
		JLabel label = new JLabel(labelText);
		JTextField textField = new JTextField(fieldSize);
		JPanel panel = new JPanel();
		panel.add(label);
		panel.add(textField);
		contentPane.add(panel);
		return textField;
	}

}
