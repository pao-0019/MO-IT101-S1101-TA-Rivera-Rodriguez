package com.mycompany.motorph;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author angeliquerivera
 */

public class IndividualSearchPanel {
    private JPanel panel;
    private MainFrame mainFrame;
    private JTextField searchField;
    private JButton logoutButton;
    
    public IndividualSearchPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initialize();
    }
    
    private void initialize() {
        panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Load employee data first
        if (!EmployeeModelFromFile.loadEmployeesFromFile("src/main/resources/EmployeeData.csv")) {
            JOptionPane.showMessageDialog(panel, 
                "Failed to load employee data", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        // Search components
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        
        // logout button
        logoutButton = new JButton("Log Out");
        logoutButton.setPreferredSize(new Dimension(120, 30));
        logoutButton.setBackground(searchButton.getBackground());  // Set background color same as search button
        logoutButton.setForeground(searchButton.getForeground());  // Set foreground (text color) same as search button
        logoutButton.setFocusPainted(false);
        
        searchPanel.add(new JLabel("Employee ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(logoutButton);
        
        // Back button
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> mainFrame.showHome());
        
        // Top panel layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(backButton, BorderLayout.EAST);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        searchButton.addActionListener(e -> handleSearch());
        
        // Action listener for logout
        logoutButton.addActionListener(e -> mainFrame.showLogin()); 
    }
    
    private void handleSearch() {
        try {
            String empId = searchField.getText().trim();
            if (empId.isEmpty()) {
                JOptionPane.showMessageDialog(panel, 
                    "Please enter an Employee ID", 
                    "Input Required", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Ensure data is loaded
            if (EmployeeModelFromFile.getEmployeeModelList().isEmpty()) {
                if (!EmployeeModelFromFile.loadEmployeesFromFile("src/main/resources/EmployeeData.csv")) {
                    throw new Exception("Employee data not loaded");
                }
            }
            
            Employee employee = EmployeeModelFromFile.getEmployeeById(empId);
            if (employee != null) {
                // Use EmployeeDetailsFrame instead of creating a custom dialog
                SwingUtilities.invokeLater(() -> {
                    EmployeeDetailsFrame detailsFrame = new EmployeeDetailsFrame(employee);
                    detailsFrame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(panel, 
                    "Employee not found", 
                    "Search Result", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, 
                "Error searching employee: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public JPanel getPanel() {
        return panel;
    }
}