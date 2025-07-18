package com.mycompany.motorph;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author angeliquerivera
 */

public class MainFrame {
    private static final String EMPLOYEE_DATA_PATH = "src/main/resources/EmployeeData.csv";
    private static final String ATTENDANCE_DATA_PATH = "src/main/resources/AttendanceRecord.csv";
    
    private final JFrame frame;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    
    public MainFrame() {
        this.frame = new JFrame("MotorPH Employee System");
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
        
        initialize();
        loadData();
        createUI();
    }
    
    private void initialize() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
    }
    
    private void loadData() {
        try {
            EmployeeModelFromFile.loadEmployeesFromFile(EMPLOYEE_DATA_PATH);
            AttendanceRecord.loadAttendanceFromCSV(ATTENDANCE_DATA_PATH);
        } catch (Exception e) {
            ErrorDialog.show("Application failed to initialize: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private void createUI() {
        // Create panels with reference to this MainFrame
        HomePanel homePanel = new HomePanel(this);
        IndividualSearchPanel searchPanel = new IndividualSearchPanel(this);  // Pass MainFrame reference
        AllEmployeesPanel employeesPanel = new AllEmployeesPanel(this);
         LoginPanel loginPanel = new LoginPanel(this);  // Login Panel
        // Add cards to the panel
        
        cardPanel.add(loginPanel.getPanel(), "Login");
        cardPanel.add(homePanel.getPanel(), "Home");
        cardPanel.add(searchPanel.getPanel(), "Individual Search");
        cardPanel.add(employeesPanel.getPanel(), "All Employees");
        
        frame.add(cardPanel);
        
        // Show the home panel by default
        //showHome();
        // Initially show the Login screen
        showLogin();
    }
    // Show the Login panel first
    public void showLogin() {
        cardLayout.show(cardPanel, "Login");
    }
    public void showHome() {
        cardLayout.show(cardPanel, "Home");
    }
    
    public void showIndividualSearch() {
        cardLayout.show(cardPanel, "Individual Search");
    }
    
    public void showAllEmployees() {
        cardLayout.show(cardPanel, "All Employees");
    }
    
    public void show() {
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame().show();
            } catch (Exception e) {
                ErrorDialog.show("Failed to start application: " + e.getMessage());
            }
        });
    }
}