package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RentPage extends JFrame {
    private JCheckBox carsCheckbox, scootersCheckbox, motorbikeCheckbox, motorcycleCheckbox;
    private JTextArea selectedTextArea;
    private JPanel vehiclesPanel;

    public RentPage() {
        setTitle("Rent Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel with checkboxes
        JPanel topPanel = new JPanel();
        carsCheckbox = new JCheckBox("Cars");
        scootersCheckbox = new JCheckBox("Scooters");
        motorbikeCheckbox = new JCheckBox("Motorbike");
        motorcycleCheckbox = new JCheckBox("Motorcycle");

        topPanel.add(carsCheckbox);
        topPanel.add(scootersCheckbox);
        topPanel.add(motorbikeCheckbox);
        topPanel.add(motorcycleCheckbox);

        // Middle panel for vehicle rows
        vehiclesPanel = new JPanel();
        vehiclesPanel.setLayout(new BoxLayout(vehiclesPanel, BoxLayout.Y_AXIS));
        // Example: Add vehicles here
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
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    private void addVehicleRow(String vehicleInfo) {
        JPanel row = new JPanel();
        row.add(new JLabel(vehicleInfo));
        JButton rentButton = new JButton("Rent");
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle rent action
                selectedTextArea.append(vehicleInfo + "\n");
            }
        });
        row.add(rentButton);
        vehiclesPanel.add(row);
    }
}
