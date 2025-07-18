package com.mycompany.motorph;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author angeliquerivera
 */
public class HomePanel {
    private JPanel panel;
    private MainFrame mainFrame;

    public HomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initialize();
    }

    private void initialize() {
        panel = new JPanel(new BorderLayout());
        
        // Left panel (blue background with welcome message)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(0x004C99));  // Blue color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        
        JLabel welcomeLabel = new JLabel("Welcome to MotorPH Employee Management", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        
         // Motorcycle logo 
        ImageIcon motorcycleIcon = new ImageIcon(new ImageIcon("src/main/resources/MotorPHLogo.png")
            .getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH));  // Optional scaling
        JLabel logoLabel = new JLabel(motorcycleIcon);
        
        // Add the welcome label to the left panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(welcomeLabel, gbc);
        
          gbc.gridy = 1;
        leftPanel.add(logoLabel, gbc);
        
        // Right panel (buttons for navigation)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);  // White background for the right panel

        // Create buttons with the desired color and size
        JButton searchButton = createStyledButton("Individual Employee ", new Color(0x0066CC));
        JButton allEmployeesButton = createStyledButton("All Employee List", new Color(0x0066CC));
        JButton logoutButton = createStyledButton("Log Out", new Color(0x0066CC));
        JButton exitButton = createStyledButton("Exit", new Color(0x0066CC));

        // Add buttons to right panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(searchButton, gbc);

        gbc.gridy = 1;
        rightPanel.add(allEmployeesButton, gbc);

        gbc.gridy = 2;
        rightPanel.add(logoutButton, gbc);

        gbc.gridy = 3;
        rightPanel.add(exitButton, gbc);

        // Add the panels to the main panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(550);  // Set the divider location to 450
        splitPane.setDividerSize(5);  // Optionally, you can set the divider size

        panel.add(splitPane, BorderLayout.CENTER);

        // Action listeners
        searchButton.addActionListener(e -> mainFrame.showIndividualSearch());
        allEmployeesButton.addActionListener(e -> mainFrame.showAllEmployees());
        logoutButton.addActionListener(e -> mainFrame.showLogin());
        exitButton.addActionListener(e -> System.exit(0));
    }

    // Helper method to create buttons with a unified size and custom color
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 40));  // Unified size for buttons
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    public JPanel getPanel() {
        return panel;
    }
}
