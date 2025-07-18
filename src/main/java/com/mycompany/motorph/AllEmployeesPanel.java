/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.motorph;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author angeliquerivera
 */


public class AllEmployeesPanel {
    private final JPanel panel;
    private final EmployeeTable employeeTable;
    private final ButtonPanel buttonPanel;
    private final MainFrame mainFrame;

    public AllEmployeesPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.panel = new JPanel(new BorderLayout());
        this.employeeTable = new EmployeeTable();
        this.buttonPanel = new ButtonPanel(employeeTable, mainFrame);
        
        initialize();
        loadEmployeeData();
    }

    private void initialize() {
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set up selection listener
        employeeTable.addSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                buttonPanel.setViewButtonEnabled(employeeTable.getSelectedEmployee() != null);
            }
        });

        // Configure button actions
        buttonPanel.setRefreshAction(e -> loadEmployeeData());
        buttonPanel.setAddEmployeeAction(e -> showNewEmployeeForm());
        buttonPanel.setBackButtonAction(e -> mainFrame.showHome());

        // Layout setup
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(buttonPanel.getPanel(), BorderLayout.NORTH);
        containerPanel.add(employeeTable.getScrollPane(), BorderLayout.CENTER);
        panel.add(containerPanel, BorderLayout.CENTER);
    }


    private void showNewEmployeeForm() {
    try {
        // Generate the next employee number
        String nextEmpNumber = generateNextEmployeeNumber();
        
        // Create the form with auto-generated number
        NewEmployeeForm form = new NewEmployeeForm(nextEmpNumber);
        
        // Center the form
        Window parentWindow = SwingUtilities.getWindowAncestor(panel);
        if (parentWindow != null) {
            form.setLocationRelativeTo(parentWindow);
        } else {
            form.setLocationRelativeTo(null);
        }
        
        form.setVisible(true);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(panel,
            "Failed to create employee form: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

private String generateNextEmployeeNumber() {
    List<Employee> employees = EmployeeModelFromFile.getEmployeeModelList();
    
    // Find the highest existing employee number
    int maxNumber = employees.stream()
        .mapToInt(e -> {
            try {
                return Integer.parseInt(e.getEmployeeNumber());
            } catch (NumberFormatException ex) {
                return 0;
            }
        })
        .max()
        .orElse(10000); // Default starting number
    
    // Find the first gap in numbering if any
    for (int i = 10001; i <= maxNumber; i++) {
        final int num = i;
        if (employees.stream().noneMatch(e -> e.getEmployeeNumber().equals(String.valueOf(num)))) {
            return String.valueOf(num);
        }
    }
    
    // Otherwise return next sequential number
    return String.valueOf(maxNumber + 1);
}
    private void loadEmployeeData() {
        try {
            boolean loaded = EmployeeModelFromFile.loadEmployeesFromFile("src/main/resources/EmployeeData.csv");

            if (loaded) {
                List<Employee> employees = EmployeeModelFromFile.getEmployeeModelList();
                employeeTable.refresh(employees);
            } else {
                JOptionPane.showMessageDialog(panel,
                    "Failed to load employee data from file",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel,
                "Error loading employee data: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}