package View;

import Database.tables.*;
import model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.*;

public class AdminPage extends JFrame {
    JPanel StatisticsPanel;


    Color titleBackgroundColor = new Color(220, 220, 220);
    Color optionsBackgroundColor = new Color(240, 240, 240);
    Color contentBackgroundColor = new Color(255, 255, 255);


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
        buildUpperRightPanel(upperRightPanel);


        upperRow.add(upperLeftPanel);
        upperRow.add(upperRightPanel);

        // Lower row with 4 columns
        JPanel lowerRow = new JPanel(new GridLayout(1, 4)); // 1 row, 4 columns

        JPanel lowerLeftPanel = new JPanel();
        lowerLeftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buildLowerLeftPanel(lowerLeftPanel);
        
        
        JPanel lowerCenterLeftPanel = new JPanel();
        lowerCenterLeftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buildCenterLeftPanel(lowerCenterLeftPanel);
        
        
        JPanel lowerCenterRightPanel = new JPanel();
        lowerCenterRightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buildCenterRightPanel(lowerCenterRightPanel);
        
        
        JPanel lowerRightPanel = new JPanel();
        lowerRightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buildLowerRightPanel(lowerRightPanel);
        
        
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

    JPanel LowerRightContentPanel; // Keep a reference to the panel

    private void buildLowerRightPanel(JPanel lowerRightPanel) {
        lowerRightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title Panel with label
        JPanel titlePanel = new JPanel(new GridBagLayout()); // Center title in its panel
        JLabel titleLabel = new JLabel("View Most Popular Vehicle");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.anchor = GridBagConstraints.CENTER;
        titlePanel.add(titleLabel, titleGbc); // Add the titleLabel to the titlePanel with constraints

        // Options Panel with date
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        String[] options = {"Select Vehicle", "Car", "MotorBike", "Bicycle", "Scooter"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(0);
        GridBagConstraints optionsGbc = new GridBagConstraints();
        optionsGbc.gridwidth = GridBagConstraints.REMAINDER;
        optionsGbc.anchor = GridBagConstraints.CENTER;
        optionsPanel.add(comboBox, optionsGbc);

        // content
        LowerRightContentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints contentGbc = new GridBagConstraints();
        contentGbc.gridwidth = GridBagConstraints.REMAINDER;
        contentGbc.anchor = GridBagConstraints.CENTER;

        // set common properties for gbc
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title Panel Constraints
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the title panel
        lowerRightPanel.add(titlePanel, gbc);

        // Options Panel Constraints
        gbc.gridy = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the options panel
        lowerRightPanel.add(optionsPanel, gbc);

        // UpperLeftContentPanel Constraints
        gbc.gridy = 2;
        gbc.weighty = 0.8; // Allocate 80% of the space to the content panel
        lowerRightPanel.add(LowerRightContentPanel, gbc);


        // Apply background colors
        titlePanel.setBackground(titleBackgroundColor);
        optionsPanel.setBackground(optionsBackgroundColor);
        LowerRightContentPanel.setBackground(contentBackgroundColor);

        // action listener
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> cb = (JComboBox<String>) event.getSource();
                String selectedOption = (String) cb.getSelectedItem();
                if(!selectedOption.equals("Select Vehicle")) {
                    buildLowerRightContentPanel(selectedOption);
                }
            }
        });

    }

    private void buildLowerRightContentPanel(String selectedOption) {

        LowerRightContentPanel.removeAll();
        LowerRightContentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // set common properties for gbc
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;


        String result = null;
        try {
            if(selectedOption.equals("Car")) {
                EditCarTable ect = new EditCarTable();
                Car car = ect.getMostPopularCar();
                result = "Car:" + car.getVehicleId() + ", " + car.getModel() + " " + car.getBrand() + " " + car.getColor();
            } else if(selectedOption.equals("MotorBike")) {
                EditMotorBikeTable emt = new EditMotorBikeTable();
                MotorBike motorBike = emt.getMostPopularMotorBike();
                result = "Motorbike:" + motorBike.getVehicleId() + ", " + motorBike.getModel() + " " + motorBike.getBrand() + " " + motorBike.getColor();
            } else if(selectedOption.equals("Bicycle")) {
                EditBicycleTable ebt = new EditBicycleTable();
                Bicycle bicycle = ebt.getMostPopularBicycle();
                result = "Bicycle:" + bicycle.getVehicleId() + ", " + bicycle.getModel() + " " + bicycle.getBrand() + " " + bicycle.getColor();
            } else if(selectedOption.equals("Scooter")) {
                EditScooterTable est = new EditScooterTable();
                Scooter scooter = est.getMostPopularScooter();
                result = "Scooter:" + scooter.getVehicleId() + ", " + scooter.getModel() + " " + scooter.getBrand() + " " + scooter.getColor();
            }

            JLabel resultLabel = new JLabel(result);
            resultLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
            LowerRightContentPanel.add(resultLabel, gbc);

            LowerRightContentPanel.revalidate();
            LowerRightContentPanel.repaint();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    JPanel CenterRightContentPanel; // Keep a reference to the content panel

    private void buildCenterRightPanel(JPanel lowerCenterRightPanel) {
        lowerCenterRightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title Panel with label
        JPanel titlePanel = new JPanel(new GridBagLayout()); // Center title in its panel
        JLabel titleLabel = new JLabel("View Repairs Cost");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.anchor = GridBagConstraints.CENTER;
        titlePanel.add(titleLabel, titleGbc); // Add the titleLabel to the titlePanel with constraints

        // Options Panel with date
        JPanel optionsPanel = new JPanel(new GridBagLayout()); // Use FlowLayout for side-by-side components

        // Date
        JButton dateButton = new JButton("Date");

        // Add the components to the optionsPanel
        optionsPanel.add(dateButton);

        // Content Panel
        CenterRightContentPanel = new JPanel(new GridBagLayout());
        CenterLeftContentPanel.setLayout(new BoxLayout(CenterLeftContentPanel, BoxLayout.Y_AXIS));

        // Set common properties for gbc
        // Set common properties for gbc
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title Panel Constraints
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the title panel
        lowerCenterRightPanel.add(titlePanel, gbc);

        // Options Panel Constraints
        gbc.gridy = 1;
        gbc.weighty = 0.1; // Allocate 5% of the space to the options panel
        lowerCenterRightPanel.add(optionsPanel, gbc);

        // UpperLeftContentPanel Constraints
        gbc.gridy = 2;
        gbc.weighty = 0.8; // Allocate 80% of the space to the content panel
        lowerCenterRightPanel.add(CenterRightContentPanel, gbc);

        // Apply background colors
        titlePanel.setBackground(titleBackgroundColor);
        optionsPanel.setBackground(optionsBackgroundColor);
        CenterRightContentPanel.setBackground(contentBackgroundColor);

        // Add action listener
        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createViewRepairsCostDialog();
            }
        });
    }

    private void createViewRepairsCostDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("View Repairs Cost");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());

        // Create combo boxes for year and month selection
        JComboBox<Integer> yearComboBox = new JComboBox<>(getYearOptions());
        JComboBox<Month> monthComboBox = new JComboBox<>(Month.values());

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedYear = (int) yearComboBox.getSelectedItem();
                Month selectedMonth = (Month) monthComboBox.getSelectedItem();
                LocalDate startDate = LocalDate.of(selectedYear, selectedMonth, 1);

                System.out.println("startDate: " + startDate);

                // Clear the content panel
                CenterRightContentPanel.removeAll();

                // Call your database query function with the selected type and date
                EditRepairTable ert = new EditRepairTable();
                ArrayList<Map<String,Integer>> results = null;
                try {
                    results = ert.calculateMonthlyCostsByTypeFromStartDate(startDate);


                    // get "Maintenance" and "Crash" costs
                    int maintenanceCost = results.get(0).get("Maintenance");
                    int crashCost = results.get(0).get("Crash");

                    // Create a new panel for the results
                    JPanel resultsPanel = new JPanel(new GridBagLayout());
                    resultsPanel.setBackground(contentBackgroundColor);

                    // Create a label for the maintenance cost
                    JLabel maintenanceLabel = new JLabel("Maintenance Cost: " + maintenanceCost);
                    maintenanceLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
                    GridBagConstraints maintenanceLabelGbc = new GridBagConstraints();
                    maintenanceLabelGbc.gridwidth = GridBagConstraints.REMAINDER;
                    maintenanceLabelGbc.anchor = GridBagConstraints.CENTER;
                    resultsPanel.add(maintenanceLabel, maintenanceLabelGbc);

                    // Create a label for the crash cost
                    JLabel crashLabel = new JLabel("Crash Cost: " + crashCost);
                    crashLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
                    GridBagConstraints crashLabelGbc = new GridBagConstraints();
                    crashLabelGbc.gridwidth = GridBagConstraints.REMAINDER;
                    crashLabelGbc.anchor = GridBagConstraints.CENTER;
                    resultsPanel.add(crashLabel, crashLabelGbc);

                    // Add the results panel to the content panel
                    CenterRightContentPanel.add(resultsPanel);

                    // Refresh the content panel
                    CenterRightContentPanel.revalidate();
                    CenterRightContentPanel.repaint();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

                dialog.dispose();
            }
        });

        // Add components to the dialog
        dialog.add(new JLabel("Year:"));
        dialog.add(yearComboBox);
        dialog.add(new JLabel("Month:"));
        dialog.add(monthComboBox);
        dialog.add(submitButton);

        // Set dialog size and make it visible
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

    JPanel CenterLeftContentPanel; // Keep a reference to the content panel
    String CenterLeftType;


    private void buildCenterLeftPanel(JPanel lowerCenterLeftPanel) {
        lowerCenterLeftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title Panel with label
        JPanel titlePanel = new JPanel(new GridBagLayout()); // Center title in its panel
        JLabel titleLabel = new JLabel("View Bookings Revenue");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.anchor = GridBagConstraints.CENTER;
        titlePanel.add(titleLabel, titleGbc); // Add the titleLabel to the titlePanel with constraints

        // Options Panel with dropdown AND date
        JPanel optionsPanel = new JPanel(new GridBagLayout()); // Use FlowLayout for side-by-side components
        String[] bookingOptions = {"Select Booking", "Car", "Motorbike", "Bicycle", "Scooter"};
        GridBagConstraints optionsGbc = new GridBagConstraints();
        optionsGbc.gridwidth = GridBagConstraints.REMAINDER;
        optionsGbc.anchor = GridBagConstraints.CENTER;
        JComboBox<String> bookingComboBox = new JComboBox<>(bookingOptions);

        bookingComboBox.setSelectedIndex(0);

        // Date picker
        JButton datePickerButton = new JButton("Date");

        // Add components to the optionsPanel
        optionsPanel.add(bookingComboBox);
        optionsPanel.add(datePickerButton);

        // Content Panel
        CenterLeftContentPanel = new JPanel();
        CenterLeftContentPanel.setLayout(new BoxLayout(CenterLeftContentPanel, BoxLayout.Y_AXIS));

        // Set common properties for gbc
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title Panel Constraints
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the title panel
        lowerCenterLeftPanel.add(titlePanel, gbc);

        // Options Panel Constraints
        gbc.gridy = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the options panel
        lowerCenterLeftPanel.add(optionsPanel, gbc);

        // UpperLeftContentPanel Constraints
        gbc.gridy = 2;
        gbc.weighty = 0.8; // Allocate 80% of the space to the content panel
        lowerCenterLeftPanel.add(CenterLeftContentPanel, gbc);

        // Apply background colors
        titlePanel.setBackground(titleBackgroundColor);
        optionsPanel.setBackground(optionsBackgroundColor);
        CenterLeftContentPanel.setBackground(contentBackgroundColor);

        // Add action listener to bookingComboBox
        bookingComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> bookingComboBox = (JComboBox<String>) event.getSource();
                CenterLeftType = (String) bookingComboBox.getSelectedItem();
            }
        });

        // Add action listener to datePickerButton
        datePickerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                showRevenueDateDialog();
            }
        });

    }

    // function creates a dialog for entering a single date
    // fetches said date alongside the CenterLeftType
    // Calls the database query function "calculateRentalIncomeByCategoryAndTimePeriod"
    private void showRevenueDateDialog() {
        JDialog dateDialog = new JDialog();
        dateDialog.setTitle("Select Month and Year");
        dateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dateDialog.setLayout(new FlowLayout());

        // Create combo boxes for year and month selection
        JComboBox<Integer> yearComboBox = new JComboBox<>(getYearOptions());
        JComboBox<Month> monthComboBox = new JComboBox<>(Month.values());

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedYear = (int) yearComboBox.getSelectedItem();
                Month selectedMonth = (Month) monthComboBox.getSelectedItem();
                LocalDate startDate = LocalDate.of(selectedYear, selectedMonth, 1);

                System.out.println("startDate: " + startDate);
                System.out.println("type: " + CenterLeftType);

                // Call your database query function with the selected type and date
                EditBookingTable ebt = new EditBookingTable();
                ArrayList<Map<String,String>> results = ebt.calculateRentalIncomeByCategoryAndTimePeriod(CenterLeftType, startDate);

                // Clear the content panel
                CenterLeftContentPanel.removeAll();


                // Add the results to the content panel
                for (Map<String,String> result : results) {
                    JPanel resultPanel = new JPanel(new GridBagLayout());
                    resultPanel.setBackground(contentBackgroundColor);
                    resultPanel.setFont(new Font("Helvetica", Font.PLAIN, 16));
                    GridBagConstraints resultGbc = new GridBagConstraints();
                    resultGbc.gridwidth = GridBagConstraints.REMAINDER;
                    resultGbc.anchor = GridBagConstraints.CENTER;
                    // Create the string to be displayed
                    String resultString = "Total Revenue for " + CenterLeftType + "s: " + result.get("totalIncome");
                    System.out.println(resultString);
                    resultPanel.add(new JLabel(resultString), resultGbc);

                    CenterLeftContentPanel.add(resultPanel);
                }

                // Refresh the content panel
                CenterLeftContentPanel.revalidate();
                CenterLeftContentPanel.repaint();

                dateDialog.dispose();
            }
        });

        // Add components to the dialog
        dateDialog.add(new JLabel("Year:"));
        dateDialog.add(yearComboBox);
        dateDialog.add(new JLabel("Month:"));
        dateDialog.add(monthComboBox);
        dateDialog.add(submitButton);

        // Set dialog size and make it visible
        dateDialog.pack();
        dateDialog.setLocationRelativeTo(null);
        dateDialog.setVisible(true);
    }




    private JPanel LowerLeftContentPanel; // Keep a reference to the content panel

    private void buildLowerLeftPanel(JPanel lowerLeftPanel) {

        lowerLeftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title Panel with label
        JPanel titlePanel = new JPanel(new GridBagLayout()); // Center title in its panel
        JLabel titleLabel = new JLabel("View Booking Duration");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.anchor = GridBagConstraints.CENTER;
        titlePanel.add(titleLabel, titleGbc); // Add the titleLabel to the titlePanel with constraints

        // Options Panel with dropdown
        JPanel optionsPanel = new JPanel(new GridBagLayout()); // Center combobox in its panel
        String[] vehicleOptions = {"Select Vehicle", "Car", "Motorbike", "Bicycle", "Scooter"};
        JComboBox<String> vehicleDropDown = new JComboBox<>(vehicleOptions);
        GridBagConstraints optionsGbc = new GridBagConstraints();
        optionsGbc.gridwidth = GridBagConstraints.REMAINDER;
        optionsGbc.anchor = GridBagConstraints.CENTER;
        optionsPanel.add(vehicleDropDown, optionsGbc); // Add the vehicleDropDown to the optionsPanel with constraints

        // UpperLeftContentPanel for content
        LowerLeftContentPanel = new JPanel();
        LowerLeftContentPanel.setLayout(new BoxLayout(LowerLeftContentPanel, BoxLayout.Y_AXIS));




        // Set common properties for gbc
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title Panel Constraints
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the title panel
        lowerLeftPanel.add(titlePanel, gbc);

        // Options Panel Constraints
        gbc.gridy = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the options panel
        lowerLeftPanel.add(optionsPanel, gbc);

        // UpperLeftContentPanel Constraints
        gbc.gridy = 2;
        gbc.weighty = 0.8; // Allocate 80% of the space to the content panel
        lowerLeftPanel.add(LowerLeftContentPanel, gbc);


        // Apply background colors
        titlePanel.setBackground(titleBackgroundColor);
        optionsPanel.setBackground(optionsBackgroundColor);
        LowerLeftContentPanel.setBackground(contentBackgroundColor);


        // Add ActionListener to the JComboBox
        vehicleDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedVehicle = (String) vehicleDropDown.getSelectedItem();
                // Make sure "Select Vehicle" is not processed
                if (!"Select Vehicle".equals(selectedVehicle)) {
                    // Call a function to handle this selection (to be implemented)
                    handleVehicleSelection(selectedVehicle);
                }
            }
        });

    }


    private void handleVehicleSelection(String selectedVehicle) {
        EditOrderTable eot = new EditOrderTable();

        // Calculate and fetch the statistics based on the selected vehicle type
        Map<String, String> vehicleStats;
        if ("Car".equals(selectedVehicle)) {
            vehicleStats = eot.calculateCarStatistics();
        } else if ("Motorbike".equals(selectedVehicle)) {
            vehicleStats = eot.calculateMotorBikeStatistics();
        } else if ("Bicycle".equals(selectedVehicle)) {
            vehicleStats = eot.calculateBicycleStatistics();
        } else if ("Scooter".equals(selectedVehicle)) {
            vehicleStats = eot.calculateScooterStatistics();
        } else {
            // Invalid selection or "Select Vehicle," do nothing
            return;
        }

        // Clear the existing content panel
        LowerLeftContentPanel.removeAll();
        LowerLeftContentPanel.setLayout(new GridBagLayout());
        // Set common properties for gbc
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Create panels for each statistic and add them to the content panel
        for (Map.Entry<String, String> entry : vehicleStats.entrySet()) {
            JPanel statisticPanel = new JPanel(new GridBagLayout());
            statisticPanel.setBackground(contentBackgroundColor);
            statisticPanel.setFont(new Font("Helvetica", Font.PLAIN, 16));
            GridBagConstraints statisticGbc = new GridBagConstraints();
            statisticGbc.gridwidth = GridBagConstraints.REMAINDER;
            statisticGbc.anchor = GridBagConstraints.CENTER;
            JLabel keyLabel = new JLabel(entry.getKey() + ":");
            JLabel valueLabel = new JLabel(entry.getValue());
            statisticPanel.add(keyLabel);
            statisticPanel.add(valueLabel);
            LowerLeftContentPanel.add(statisticPanel, gbc);
        }

        // Repaint the content panel to reflect changes
        LowerLeftContentPanel.revalidate();
        LowerLeftContentPanel.repaint();
    }


    private JPanel UpperRightContentPanel; // Keep a reference to the content panel
    private LocalDate fromDate; // Local variable to store the selected "From Date"
    private LocalDate toDate;   // Local variable to store the selected "To Date"



    private void buildUpperRightPanel(JPanel upperRightPanel) {
        upperRightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title Panel with label
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel titleLabel = new JLabel("Booking Statistics");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.anchor = GridBagConstraints.CENTER;
        titlePanel.add(titleLabel, titleGbc);

        // Options Panel with "Date" button
        JPanel optionsPanel = new JPanel(new GridBagLayout());

        JButton dateButton = new JButton("Date");
        GridBagConstraints optionsGbc = new GridBagConstraints();
        optionsGbc.gridwidth = GridBagConstraints.REMAINDER;
        optionsGbc.anchor = GridBagConstraints.CENTER;
        optionsPanel.add(dateButton, optionsGbc);

        // Content Panel
        UpperRightContentPanel = new JPanel();
        UpperRightContentPanel.setLayout(new BoxLayout(UpperRightContentPanel, BoxLayout.Y_AXIS));

        titlePanel.setBackground(titleBackgroundColor);
        optionsPanel.setBackground(optionsBackgroundColor);
        UpperRightContentPanel.setBackground(contentBackgroundColor);

        // Common GridBagConstraints settings
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title Panel Constraints
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the title panel
        upperRightPanel.add(titlePanel, gbc);

        // Options Panel Constraints
        gbc.gridy = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the options panel
        upperRightPanel.add(optionsPanel, gbc);

        // Content Panel Constraints
        gbc.gridy = 2;
        gbc.weighty = 0.8; // Allocate 80% of the space to the content panel
        upperRightPanel.add(UpperRightContentPanel, gbc);

        dateButton.addActionListener(e -> createDatePickerDialog(this::fetchAndDisplayBookings));
    }

    @FunctionalInterface
    interface DateQueryFunction {
        void query(LocalDate fromDate, LocalDate toDate);
    }

    private void createDatePickerDialog(DateQueryFunction dateQuery) {
        // Create a dialog
        JDialog dateDialog = new JDialog();
        dateDialog.setTitle("Date Selection");
        dateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create a panel for date inputs
        JPanel datePanel = new JPanel(new GridLayout(2, 3)); // 2 rows, 3 columns

        // Add JComboBoxes for "From Date" and "To Date"
        JComboBox<Integer> fromDayComboBox = new JComboBox<>(getDayOptions());
        JComboBox<Month> fromMonthComboBox = new JComboBox<>(Month.values());
        JComboBox<Integer> fromYearComboBox = new JComboBox<>(getYearOptions());
        JComboBox<Integer> toDayComboBox = new JComboBox<>(getDayOptions());
        JComboBox<Month> toMonthComboBox = new JComboBox<>(Month.values());
        JComboBox<Integer> toYearComboBox = new JComboBox<>(getYearOptions());

        datePanel.add(new JLabel("From Date:"));
        datePanel.add(fromDayComboBox);
        datePanel.add(fromMonthComboBox);
        datePanel.add(fromYearComboBox);
        datePanel.add(new JLabel("To Date:"));
        datePanel.add(toDayComboBox);
        datePanel.add(toMonthComboBox);
        datePanel.add(toYearComboBox);

        // Create a "Submit" button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            int fromDay = (int) fromDayComboBox.getSelectedItem();
            Month fromMonth = (Month) fromMonthComboBox.getSelectedItem();
            int fromYear = (int) fromYearComboBox.getSelectedItem();
            int toDay = (int) toDayComboBox.getSelectedItem();
            Month toMonth = (Month) toMonthComboBox.getSelectedItem();
            int toYear = (int) toYearComboBox.getSelectedItem();

            LocalDate fromDate = LocalDate.of(fromYear, fromMonth, fromDay);
            LocalDate toDate = LocalDate.of(toYear, toMonth, toDay);

            dateQuery.query(fromDate, toDate);

            dateDialog.dispose();
        });

        // Add components to the dialog
        dateDialog.add(datePanel, BorderLayout.CENTER);
        dateDialog.add(submitButton, BorderLayout.SOUTH);

        // Set dialog size and make it visible
        dateDialog.pack();
        dateDialog.setLocationRelativeTo(null);
        dateDialog.setVisible(true);
    }

    public void fetchAndDisplayBookings(LocalDate fromDate, LocalDate toDate) {
        try {
            EditOrderTable eot = new EditOrderTable();
            List<Booking> bookings = eot.getBookingsBetweenDates(fromDate, toDate);
            // Assume there's a method that takes a list of bookings and displays them
            displayBookings(bookings);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Handle exceptions properly
        }
    }


    // Helper methods to get day and year options
    private Integer[] getDayOptions() {
        // Return an array of integers from 1 to 31
        return IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new);
    }

    private Integer[] getYearOptions() {
        return IntStream.rangeClosed(2023, 2025).boxed().toArray(Integer[]::new);
    }

    private void displayBookings(List<Booking> bookings) {
        // Clear the previous content
        UpperRightContentPanel.removeAll();

        if (bookings != null) {
            for (Booking booking : bookings) {
                JPanel bookingPanel = new JPanel();
                bookingPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                bookingPanel.setBackground(contentBackgroundColor);
                bookingPanel.add(new JLabel(booking.toString())); // Customize this to display booking details
                UpperRightContentPanel.add(bookingPanel);
            }
        }

        // Revalidate and repaint to update the UI
        UpperRightContentPanel.revalidate();
        UpperRightContentPanel.repaint();
    }




    JPanel UpperLeftContentPanel; // Declare the empty panel as a class variable

    private void buildUpperLeftPanel(JPanel upperLeftPanel) {
        upperLeftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title Panel with label
        JPanel titlePanel = new JPanel(new GridBagLayout()); // Center title in its panel
        JLabel titleLabel = new JLabel("View Vehicles");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.anchor = GridBagConstraints.CENTER;
        titlePanel.add(titleLabel, titleGbc); // Add the titleLabel to the titlePanel with constraints

        // Options Panel with dropdown
        JPanel optionsPanel = new JPanel(new GridBagLayout()); // Center combobox in its panel
        String[] vehicleOptions = {"Select Vehicle", "Car", "Motorbike", "Bicycle", "Scooter"};
        JComboBox<String> vehicleDropDown = new JComboBox<>(vehicleOptions);
        GridBagConstraints optionsGbc = new GridBagConstraints();
        optionsGbc.gridwidth = GridBagConstraints.REMAINDER;
        optionsGbc.anchor = GridBagConstraints.CENTER;
        optionsPanel.add(vehicleDropDown, optionsGbc); // Add the vehicleDropDown to the optionsPanel with constraints

        // UpperLeftContentPanel for content
        UpperLeftContentPanel = new JPanel();
        UpperLeftContentPanel.setLayout(new BoxLayout(UpperLeftContentPanel, BoxLayout.Y_AXIS));

        // Set common properties for gbc
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title Panel Constraints
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the title panel
        upperLeftPanel.add(titlePanel, gbc);

        // Options Panel Constraints
        gbc.gridy = 1;
        gbc.weighty = 0.1; // Allocate 10% of the space to the options panel
        upperLeftPanel.add(optionsPanel, gbc);

        // UpperLeftContentPanel Constraints
        gbc.gridy = 2;
        gbc.weighty = 0.8; // Allocate 80% of the space to the content panel
        upperLeftPanel.add(UpperLeftContentPanel, gbc);


        // Apply background colors
        titlePanel.setBackground(titleBackgroundColor);
        optionsPanel.setBackground(optionsBackgroundColor);
        UpperLeftContentPanel.setBackground(contentBackgroundColor);

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


    // Now let's define the populateVehiclePanel method
    private void populateVehiclePanel(String vehicleType) {
        // Clear the previous content
        UpperLeftContentPanel.removeAll();

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
                    vehiclePanel.setBackground(contentBackgroundColor);
                    vehiclePanel.add(new JLabel(vehicle.toString())); // Customize this to display vehicle details
                    UpperLeftContentPanel.add(vehiclePanel);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Handle exceptions properly
        }

        // Revalidate and repaint to update the UI
        UpperLeftContentPanel.revalidate();
        UpperLeftContentPanel.repaint();
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
