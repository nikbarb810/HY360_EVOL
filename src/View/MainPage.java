package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainPage extends JFrame {

    public MainPage() {
        // Set frame to fullscreen and non-resizable
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);

        // Load background image
        BufferedImage bgImage = null;
        try {
            bgImage = ImageIO.read(getClass().getResource("EVOL.png"));
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

        // Top panel with label
        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("evol");
        label.setForeground(Color.BLACK);
        topPanel.add(label);
        topPanel.setBounds(0, 0, layeredPane.getPreferredSize().width, 100);
        topPanel.setOpaque(false);

        // Center panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");

        // Modify buttons
        Color buttonColor = Color.BLACK;
        Color textColor = Color.WHITE;
        Dimension buttonSize = new Dimension(200, 50); // Change dimensions as needed

        button1.setBackground(buttonColor);
        button1.setForeground(textColor);
        button1.setPreferredSize(buttonSize);
        button1.setMaximumSize(buttonSize); // To ensure button size is respected in BoxLayout
        button1.setAlignmentX(Component.CENTER_ALIGNMENT); // To center the button

        button2.setBackground(buttonColor);
        button2.setForeground(textColor);
        button2.setPreferredSize(buttonSize);
        button2.setMaximumSize(buttonSize);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);

        button3.setBackground(buttonColor);
        button3.setForeground(textColor);
        button3.setPreferredSize(buttonSize);
        button3.setMaximumSize(buttonSize);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(button1);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(button2);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(button3);
        buttonPanel.setBounds(layeredPane.getPreferredSize().width / 2 - 100, layeredPane.getPreferredSize().height / 2 - 75, 200, 150);
        buttonPanel.setOpaque(false);

        // Custom Close Button
        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> System.exit(0));
        closeButton.setBounds(layeredPane.getPreferredSize().width - 50, 0, 50, 50);
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setForeground(Color.BLACK);

        // Add panels to layered pane
        layeredPane.add(backgroundPanel, Integer.valueOf(1));
        layeredPane.add(topPanel, Integer.valueOf(2));
        layeredPane.add(buttonPanel, Integer.valueOf(3));
        layeredPane.add(closeButton, Integer.valueOf(4));

        // Add layered pane to frame
        add(layeredPane);

        // Frame configuration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    
}
