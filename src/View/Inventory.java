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

import model.Driver;
import model.Repair;
import model.Vehicle;

public class Inventory extends JFrame{
	private JPanel vehiclesPanel;
	ArrayList<Vehicle> pap = new ArrayList();
	
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
	        AddVehicles("Porche f16");
	        AddVehicles("Toyota starlet");
	        
	        add(new JScrollPane(vehiclesPanel), BorderLayout.CENTER);
	        pack();
	        setLocationRelativeTo(null);
	        
	}
	
	private void AddVehicles(String vehicleInfo) {
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

	        	createReportDialog();
	        }
	    });
	    innerPanel.add(extraRepairButton);
	    
	    
	    // Extra Expenses Button
	    JButton extraExpensesButton = new JButton("Extra Expenses");
	    extraExpensesButton.setBackground(Color.white);
	    extraExpensesButton.setForeground(Color.black);
	    extraExpensesButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Handle extra expenses action
	            // You can implement what happens when the button is clicked
	        	createExpenseDialog();
	        }
	    });
	    innerPanel.add(extraExpensesButton);

	    // Return Vehicle Button
	    JButton returnVehicleButton = new JButton("Return Vehicle");
	    returnVehicleButton.setBackground(Color.white);
	    returnVehicleButton.setForeground(Color.black);
	    returnVehicleButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            //
	        }
	    });
	    innerPanel.add(returnVehicleButton);

	    row.add(Box.createHorizontalGlue()); // Pushes content to center
	    row.add(innerPanel);
	    row.add(Box.createHorizontalGlue()); // Pushes content to center

	    vehiclesPanel.add(row);

	    vehiclesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}
	private void createReportDialog() {
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
	        }
	    });
	    contentPane.add(submitButton);

	    // Set dialog location relative to the parent window
	    expenseDialog.setLocationRelativeTo(null);

	    // Make the dialog visible
	    expenseDialog.setVisible(true);
	}
	private  void createExpenseDialog() {
		JDialog expenseDialog = new JDialog(this, "Add Expense");
	    expenseDialog.setTitle("Add Expense");
	    expenseDialog.setSize(400, 400);
	    expenseDialog.setResizable(false);
	    Container contentPane = expenseDialog.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	    JPanel descriptionPanel = new JPanel();
	    descriptionPanel.setLayout(new FlowLayout());
	    descriptionPanel.add(new JLabel("Exta Costs : 100 $:"));
	    contentPane.add(descriptionPanel);
	    JPanel cardNumberPanel = new JPanel();
        cardNumberPanel.add(new JLabel("Card Number:"));
        JTextField cardNumberField = new JTextField(10);
        cardNumberPanel.add(cardNumberField);
        contentPane.add(cardNumberPanel);

        // BIC number
        JPanel bicNumberPanel = new JPanel();
        bicNumberPanel.add(new JLabel("BIC Number:"));
        JTextField bicNumberField = new JTextField(10);
        bicNumberPanel.add(bicNumberField);
        contentPane.add(bicNumberPanel);

        // Expiration date with combo boxes next to each other
        JPanel expirationDatePanel = new JPanel();
        expirationDatePanel.add(new JLabel("Expiration Date:"));
        JComboBox<Integer> monthComboBox = new JComboBox<>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        JComboBox<Integer> yearComboBox = new JComboBox<>(getYearRange());
        expirationDatePanel.add(monthComboBox);
        expirationDatePanel.add(yearComboBox);
        contentPane.add(expirationDatePanel);

        // Submit button
        JPanel submitButtonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Process payment information here
                // Close the dialog
            	expenseDialog.dispose();
            }
			
        });
        submitButtonPanel.add(submitButton);
        contentPane.add(submitButtonPanel);
        contentPane.setVisible(true);
        expenseDialog.setVisible(true);
	    
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
