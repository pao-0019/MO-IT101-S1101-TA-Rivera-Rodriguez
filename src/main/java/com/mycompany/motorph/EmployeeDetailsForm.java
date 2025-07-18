/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.motorph;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author angeliquerivera
 */

public class EmployeeDetailsForm extends JPanel {
    // Form fields
    private JTextField empNumberField = new JTextField();
    private JTextField lastNameField = new JTextField();
    // Add all other fields...
    
    public EmployeeDetailsForm() {
        setLayout(new GridLayout(0, 2, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Employee Details"));
        
        // Initialize form fields
        add(new JLabel("Employee Number:"));
        add(empNumberField);
        empNumberField.setEditable(false);
        
        add(new JLabel("Last Name:"));
        add(lastNameField);
        
        // Add all other fields...
    }
    
    public void displayEmployee(Employee employee) {
        empNumberField.setText(employee.getEmployeeNumber());
        lastNameField.setText(employee.getLastName());
        // Set all other fields...
    }
    
    public void clearForm() {
        empNumberField.setText("");
        lastNameField.setText("");
        // Clear all other fields...
    }
    
    public JScrollPane getScrollPane() {
        return new JScrollPane(this);
    }
}