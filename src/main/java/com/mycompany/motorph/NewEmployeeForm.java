package com.mycompany.motorph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author angeliquerivera
 */

public class NewEmployeeForm extends JFrame {
    // Form fields
    private final JTextField empNumberField = new JTextField(20);
    private final JTextField lastNameField = new JTextField(20);
    private final JTextField firstNameField = new JTextField(20);
    private final JTextField sssField = new JTextField(20);
    private final JTextField philhealthField = new JTextField(20);
    private final JTextField tinField = new JTextField(20);
    private final JTextField pagibigField = new JTextField(20);

    public NewEmployeeForm(String generatedEmpNumber) {
        setTitle("Add New Employee");
        setSize(550, 400); // Slightly wider to accommodate format hints
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create form panel with all requested fields
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Auto-generated employee number (read-only)
        empNumberField.setText(generatedEmpNumber);
        empNumberField.setEditable(false);
       

        // Add all required fields with format hints
        formPanel.add(createFormattedLabel("Employee Number:"));
        formPanel.add(empNumberField);
        formPanel.add(createFormattedLabel("Last Name*:"));
        formPanel.add(lastNameField);
        formPanel.add(createFormattedLabel("First Name*:"));
        formPanel.add(firstNameField);
        formPanel.add(createFormattedLabel("SSS Number*:", "XX-XXXXXXX-X"));
        formPanel.add(sssField);
        formPanel.add(createFormattedLabel("PhilHealth Number*:", "12 digits"));
        formPanel.add(philhealthField);
        formPanel.add(createFormattedLabel("TIN*:", "XXX-XXX-XXX-XXX"));
        formPanel.add(tinField);
        formPanel.add(createFormattedLabel("Pag-IBIG Number*:", "12 digits"));
        formPanel.add(pagibigField);

        // Create button panel
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::saveEmployee);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        // Add components to frame
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    // Helper method to create labels with format hints
    private JLabel createFormattedLabel(String text) {
        return new JLabel(text);
    }

    private JLabel createFormattedLabel(String text, String format) {
        JLabel label = new JLabel(text);
        label.setToolTipText("Format: " + format);
        label.setText(text + " (" + format + ")");
        return label;
    }
    
    private void saveEmployee(ActionEvent e) {
        try {
            // Validate required fields
            if (lastNameField.getText().trim().isEmpty() ||
                firstNameField.getText().trim().isEmpty() ||
                sssField.getText().trim().isEmpty() ||
                philhealthField.getText().trim().isEmpty() ||
                tinField.getText().trim().isEmpty() ||
                pagibigField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please fill all required fields (*)",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate number formats
            if (!isValidSSS(sssField.getText().trim()) ||
                !isValidPhilhealth(philhealthField.getText().trim()) ||
                !isValidTIN(tinField.getText().trim()) ||
                !isValidPagibig(pagibigField.getText().trim())) {
                return;
            }

            // Create employee data
            String[] employeeData = {
                empNumberField.getText().trim(),  // Employee Number
                lastNameField.getText().trim(),  // Last Name
                firstNameField.getText().trim(),  // First Name
                "", "", "",                       // Birthday, Address, Phone
                sssField.getText().trim(),       // SSS
                philhealthField.getText().trim(),// Philhealth
                tinField.getText().trim(),       // TIN
                pagibigField.getText().trim(),   // Pag-IBIG
                "Regular", "", "",               // Status, Position, Supervisor
                "0", "0", "0", "0", "0", "0",   // Salary fields
                "08:00", "false"                // Shift info
            };

            Employee newEmployee = new Employee(employeeData);

            if (EmployeeModelFromFile.addEmployee(newEmployee)) {
                JOptionPane.showMessageDialog(this,
                    "Employee added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to add employee (possible duplicate ID)",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error saving employee: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Validation methods for government IDs
    private boolean isValidSSS(String sss) {
        if (sss.matches("^\\d{2}-\\d{7}-\\d{1}$")) {
            return true;
        }
        JOptionPane.showMessageDialog(this,
            "Invalid SSS format (should be XX-XXXXXXX-X)",
            "Validation Error",
            JOptionPane.WARNING_MESSAGE);
        return false;
    }

    private boolean isValidPhilhealth(String philhealth) {
        if (philhealth.matches("^\\d{12}$")) {
            return true;
        }
        JOptionPane.showMessageDialog(this,
            "Invalid PhilHealth format (should be 12 digits)",
            "Validation Error",
            JOptionPane.WARNING_MESSAGE);
        return false;
    }

    private boolean isValidTIN(String tin) {
        if (tin.matches("^\\d{3}-\\d{3}-\\d{3}-\\d{3}$")) {
            return true;
        }
        JOptionPane.showMessageDialog(this,
            "Invalid TIN format (should be XXX-XXX-XXX-XXX)",
            "Validation Error",
            JOptionPane.WARNING_MESSAGE);
        return false;
    }

    private boolean isValidPagibig(String pagibig) {
        if (pagibig.matches("^\\d{12}$")) {
            return true;
        }
        JOptionPane.showMessageDialog(this,
            "Invalid Pag-IBIG format (should be 12 digits)",
            "Validation Error",
            JOptionPane.WARNING_MESSAGE);
        return false;
    }
}