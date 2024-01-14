package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.Controller;
import Database.tables.EditCustomerTable;
import model.Customer;

public class LogInPage extends JFrame{
	private static final long serialVersionUID = 1L;

	public LogInPage() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);

        // Load background image
        BufferedImage bgImage = null;
        try {
            bgImage = ImageIO.read(getClass().getResource("mypic.jpg")); // Changed to car.png
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage finalBgImage = bgImage;

        // Create layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        // Panel for background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(finalBgImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 0, layeredPane.getPreferredSize().width, layeredPane.getPreferredSize().height);
        backgroundPanel.setOpaque(false);

        // Top label with "SIGN UP"
        JLabel LogInLabel = new JLabel("Log In");
        LogInLabel.setFont(new Font("Arial", Font.BOLD, 30));
        LogInLabel.setForeground(Color.WHITE);
        LogInLabel.setBounds(layeredPane.getPreferredSize().width / 2 - 50, 50, 200, 50);

        // Input fields
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();

        // Set bounds and styling
        usernameField.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 - 140, 200, 30);
        passwordField.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 - 100, 200, 30);


        // Add components to layered pane
        layeredPane.add(backgroundPanel, Integer.valueOf(1));
        layeredPane.add(usernameField, Integer.valueOf(2));
        layeredPane.add(LogInLabel, Integer.valueOf(3));
        layeredPane.add(passwordField, Integer.valueOf(4));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        int labelWidth = 100;
        int labelHeight = 30;
        int fieldWidth = 150;
        int fieldHeight = 30;
        int xPosition = layeredPane.getPreferredSize().width / 2 - fieldWidth-25;
        int yPositionOffset = 140;
        usernameLabel.setBounds(xPosition, layeredPane.getPreferredSize().height / 2 - yPositionOffset, labelWidth, labelHeight);
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(xPosition, layeredPane.getPreferredSize().height / 2 - (yPositionOffset - 40), labelWidth, labelHeight);
        passwordLabel.setForeground(Color.WHITE);
        layeredPane.add(usernameLabel, Integer.valueOf(5));
        layeredPane.add(passwordLabel, Integer.valueOf(6));

        //log in button
        JButton LogInbutton = new JButton("Log In");
        Color buttonColor = Color.cyan;
        Color textColor = Color.BLACK;
        Dimension buttonSize = new Dimension(400, 150); // Change dimensions as needed
        LogInbutton.setBackground(buttonColor);
        LogInbutton.setForeground(textColor);
        LogInbutton.setPreferredSize(buttonSize);
        LogInbutton.setMaximumSize(buttonSize); // To ensure button size is respected in BoxLayout
        LogInbutton.setAlignmentX(Component.CENTER_ALIGNMENT); // To center the button
        int buttonX = 580; // X position
        int buttonY = 360; // Y position
        int buttonWidth = 200; // Width
        int buttonHeight = 50; // Height
        LogInbutton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        // Add the button to the layered pane
        layeredPane.add(LogInbutton, Integer.valueOf(7));
        LogInbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute when Button 1 is pressed
                String username = usernameField.getText();
                //username = String.valueOf("CSD4652");
                String password=passwordField.getText();
                if(username.equals("admin") && password.equals("admin")) {
                	dispose();
                	Controller.LoadAdminPage();
                	
                }else {
                	 EditCustomerTable editCustomerTable = new EditCustomerTable();
                     Customer papa = editCustomerTable.authenticateUser(username, password);
                     if(papa == null) {
                    	 
                     }else{
                    	 dispose();
                    	 Controller.SetCustomer(papa); 	
                    	 Controller.cart.setCustomerId(papa.getCustomerId());
                     	 Controller.LoadCustomerPage();	 
                     }
                }
 
            }
        });
     // Custom Close Button
        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> System.exit(0));
        closeButton.setBounds(layeredPane.getPreferredSize().width - 50, 0, 50, 50);
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setForeground(Color.WHITE);
        layeredPane.add(closeButton, Integer.valueOf(26));
        // Add layered pane to frame
        add(layeredPane);

        // Frame configuration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

	}
}
