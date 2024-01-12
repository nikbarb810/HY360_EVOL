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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import javax.swing.JTextField;

import Controller.Controller;
import model.Customer;
public class SignUp extends JFrame {
    public SignUp() {
        // Set frame to fullscreen and non-resizable
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);

        // Load background image
        BufferedImage bgImage = null;
        try {
            bgImage = ImageIO.read(getClass().getResource("car.png")); // Changed to car.png
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
        JLabel signUpLabel = new JLabel("SIGN UP");
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 30));
        signUpLabel.setForeground(Color.WHITE);
        signUpLabel.setBounds(layeredPane.getPreferredSize().width / 2 - 100, 50, 200, 50);

        // Input fields
        JTextField emailField = new JTextField("Email");
        JTextField usernameField = new JTextField("Username");
        JTextField passwordField = new JTextField("Password");
        JTextField FNameField = new JTextField("First Name");
        JTextField LNameField = new JTextField("LastName");
        
        // Set bounds and styling
        emailField.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 -180, 200, 30);
        usernameField.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 - 140, 200, 30);
        passwordField.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 - 100, 200, 30);
        FNameField.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 - 60, 200, 30);
        LNameField.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 - 20, 200, 30);

        // Add components to layered pane
        layeredPane.add(backgroundPanel, Integer.valueOf(1));
        layeredPane.add(emailField, Integer.valueOf(2));
        layeredPane.add(signUpLabel, Integer.valueOf(3));
        layeredPane.add(usernameField, Integer.valueOf(4));
        layeredPane.add(passwordField, Integer.valueOf(5));
        layeredPane.add(FNameField, Integer.valueOf(6));
        layeredPane.add(LNameField, Integer.valueOf(7));
        JLabel EmailLabel = new JLabel("Email:");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel fNameLabel = new JLabel("First Name:");
        JLabel lNameLabel = new JLabel("Last Name:");
        JLabel DateLabel = new JLabel("Date of Birth :");

        // Set bounds and styling for labels
        int labelWidth = 100;
        int labelHeight = 30;
        int fieldWidth = 150;
        int fieldHeight = 30;
        int xPosition = layeredPane.getPreferredSize().width / 2 - fieldWidth-25;
        int yPositionOffset = 140;
        EmailLabel.setBounds(xPosition+20, layeredPane.getPreferredSize().height / 2 - yPositionOffset-40, labelWidth, labelHeight);
        EmailLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(xPosition, layeredPane.getPreferredSize().height / 2 - yPositionOffset, labelWidth, labelHeight);
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(xPosition, layeredPane.getPreferredSize().height / 2 - (yPositionOffset - 40), labelWidth, labelHeight);
        passwordLabel.setForeground(Color.WHITE);
        fNameLabel.setBounds(xPosition, layeredPane.getPreferredSize().height / 2 - (yPositionOffset - 80), labelWidth, labelHeight);
        fNameLabel.setForeground(Color.WHITE);
        lNameLabel.setBounds(xPosition, layeredPane.getPreferredSize().height / 2 - (yPositionOffset - 120), labelWidth, labelHeight);
        lNameLabel.setForeground(Color.WHITE);
        DateLabel.setBounds(xPosition-20, layeredPane.getPreferredSize().height / 2 - (yPositionOffset - 160), labelWidth-5, labelHeight);
        DateLabel.setForeground(Color.WHITE);
        // Add labels to layered pane
        layeredPane.add(EmailLabel, Integer.valueOf(8));
        layeredPane.add(usernameLabel, Integer.valueOf(9));
        layeredPane.add(passwordLabel, Integer.valueOf(10));
        layeredPane.add(fNameLabel, Integer.valueOf(11));
        layeredPane.add(lNameLabel, Integer.valueOf(12));
        layeredPane.add(DateLabel, Integer.valueOf(13));
        
        JComboBox<Integer> dayComboBox = createDayComboBox();
        JComboBox<Integer> monthComboBox = createMonthComboBox();
        JComboBox<Integer> yearComboBox = createYearComboBox();

        // Set bounds for the date combo boxes
        int dateXPosition = layeredPane.getPreferredSize().width / 2 - 100;
        int dateYPosition = layeredPane.getPreferredSize().height / 2 + 20; // Adjust the Y position as needed
        dayComboBox.setBounds(dateXPosition, dateYPosition, 60, 30);
        monthComboBox.setBounds(dateXPosition + 70, dateYPosition, 100, 30);
        yearComboBox.setBounds(dateXPosition + 180, dateYPosition, 80, 30);

        // Add date combo boxes to layered pane
        layeredPane.add(dayComboBox, Integer.valueOf(14));
        layeredPane.add(monthComboBox, Integer.valueOf(15));
        layeredPane.add(yearComboBox, Integer.valueOf(16));
        
        JTextField LicenseField = new JTextField("License id");
        LicenseField.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 +60, 200, 30);
        layeredPane.add(LicenseField, Integer.valueOf(17));
        JLabel LicenceLabel = new JLabel("LicenceId :");
        LicenceLabel.setBounds(xPosition, layeredPane.getPreferredSize().height / 2 - (yPositionOffset - 200), labelWidth-5, labelHeight);
        LicenceLabel.setForeground(Color.WHITE);
        layeredPane.add(LicenceLabel, Integer.valueOf(18));
        //credit card details 
        JTextField CardNumber = new JTextField("2");
        CardNumber.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 +100, 200, 30);
        layeredPane.add(CardNumber, Integer.valueOf(19));
        JLabel CardNoLabel = new JLabel("Card Number :");
        CardNoLabel.setBounds(xPosition- 20, layeredPane.getPreferredSize().height / 2 - (yPositionOffset - 240), labelWidth-5, labelHeight);
        CardNoLabel.setForeground(Color.WHITE);
        layeredPane.add(CardNoLabel, Integer.valueOf(20));
        
        
        JTextField Bic = new JTextField("000");
        Bic.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 +140, 200, 30);
        layeredPane.add(Bic, Integer.valueOf(21));
        JLabel BicLabel = new JLabel("BIC Number :");
        BicLabel.setBounds(xPosition- 20, layeredPane.getPreferredSize().height / 2 - (yPositionOffset - 280), labelWidth-5, labelHeight);
        BicLabel.setForeground(Color.WHITE);
        layeredPane.add(BicLabel, Integer.valueOf(22));
        JComboBox<Integer> YEAR = createYearComboBox2();
        YEAR.setBounds(dateXPosition, dateYPosition+160, 80, 30);
        layeredPane.add(YEAR, Integer.valueOf(23));
        JLabel YearLabel = new JLabel("Expiry of Card:");
        BicLabel.setBounds(xPosition- 20, layeredPane.getPreferredSize().height / 2 - (yPositionOffset - 320), labelWidth-5, labelHeight);
        BicLabel.setForeground(Color.WHITE);
        layeredPane.add(BicLabel, Integer.valueOf(24));
        JButton SignUpbutton = new JButton("SignUp");
        Color buttonColor = Color.cyan;
        Color textColor = Color.BLACK;
        Dimension buttonSize = new Dimension(400, 150); // Change dimensions as needed
        SignUpbutton.setBackground(buttonColor);
        SignUpbutton.setForeground(textColor);
        SignUpbutton.setPreferredSize(buttonSize);
        SignUpbutton.setMaximumSize(buttonSize); // To ensure button size is respected in BoxLayout
        SignUpbutton.setAlignmentX(Component.CENTER_ALIGNMENT); // To center the button
        int buttonX = 600; // X position
        int buttonY = 600; // Y position
        int buttonWidth = 200; // Width
        int buttonHeight = 50; // Height
        SignUpbutton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        // Add the button to the layered pane
        layeredPane.add(SignUpbutton, Integer.valueOf(25));
        SignUpbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute when Button 1 is pressed
                dispose();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password=passwordField.getText();
                String FirstName= FNameField.getText();
                String LastName=LNameField.getText();
                int day = (Integer) dayComboBox.getSelectedItem();
                int month = (Integer) monthComboBox.getSelectedItem();
                int year = (Integer)yearComboBox.getSelectedItem();
                LocalDate Dob =  LocalDate.of(year, month, day);
                int numbercc = Integer.valueOf(CardNumber.getText());
                int bic =Integer.valueOf(Bic.getText());
                int CardYear=(Integer) YEAR.getSelectedItem();
                String LicenceId= LicenseField.getText();
                Customer ok = new Customer( email, username,  password,  FirstName, LastName,  Dob,  numbercc,  bic, 5,  CardYear,  LicenceId);
                Controller.UploadAccount(ok);
                Controller.LoadMainPage();
                // Add any other actions you want to perform here
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
        setLocationRelativeTo(null); // Center the frame
    }
    
    private JComboBox<Integer> createDayComboBox() {
        Integer[] days = new Integer[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = i;
        }
        return new JComboBox<>(days);
    }

    private JComboBox<Integer> createMonthComboBox() {
        Integer[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
        return new JComboBox<>(months);
    }

    private JComboBox<Integer> createYearComboBox() {
        Integer[] years = new Integer[100];
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < 100; i++) {
            years[i] = currentYear - i;
        }
        return new JComboBox<>(years);
    }
    private JComboBox<Integer> createYearComboBox2() {
        Integer[] years = new Integer[100];
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            years[i] = currentYear + i;
        }
        return new JComboBox<>(years);
    }
}