/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.motorph;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angeliquerivera
 */

public class EmployeeDetailsFrame extends JFrame {
    private final Employee employee;
    private static final String CSV_FILE = "src/main/resources/EmployeeData.csv";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public EmployeeDetailsFrame(Employee employee) {
        this.employee = employee;
        setTitle("Employee Details - " + employee.getEmployeeNumber());
        setSize(1400, 800);  // Increased width
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a split pane for left (details) and right (salary computation)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(700);  // Initial divider position
        splitPane.setResizeWeight(0.5);

        // Left panel - Employee details
        JPanel leftPanel = new JPanel(new BorderLayout());
        
        // Employee details panel
        JTextArea detailsArea = new JTextArea(employee.toString());
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> openEditEmployeeFrame());
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteEmployee());
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        leftPanel.add(buttonPanel, BorderLayout.NORTH);
        leftPanel.add(detailsScroll, BorderLayout.CENTER);

        // Right panel - Salary computation
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(createSalaryPanel(), BorderLayout.CENTER);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        mainPanel.add(splitPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void openEditEmployeeFrame() {
        JFrame editFrame = new JFrame("Edit Employee - " + employee.getEmployeeNumber());
        editFrame.setSize(1000, 800);
        editFrame.setLocationRelativeTo(this);

        JPanel editPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Employee Number
        editPanel.add(new JLabel("Employee Number:"));
        JTextField empNumberField = new JTextField(employee.getEmployeeNumber());
        empNumberField.setEditable(false);
        editPanel.add(empNumberField);

        // Last Name
        editPanel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField(employee.getLastName());
        editPanel.add(lastNameField);

        // First Name
        editPanel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField(employee.getFirstName());
        editPanel.add(firstNameField);

        // Birthday
        editPanel.add(new JLabel("Birthday (MM-DD-YYYY):"));
        JTextField birthdayField = new JTextField(employee.getBirthday());
        editPanel.add(birthdayField);

        // Address
        editPanel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField(employee.getAddress());
        editPanel.add(addressField);

        // Phone Number
        editPanel.add(new JLabel("Phone Number:"));
        JTextField phoneField = new JTextField(employee.getPhoneNumber());
        editPanel.add(phoneField);

        // SSS #
        editPanel.add(new JLabel("SSS #:"));
        JTextField sssField = new JTextField(employee.getSssNumber());
        editPanel.add(sssField);

        // PhilHealth #
        editPanel.add(new JLabel("PhilHealth #:"));
        JTextField philhealthField = new JTextField(employee.getPhilhealthNumber());
        editPanel.add(philhealthField);

        // TIN #
        editPanel.add(new JLabel("TIN #:"));
        JTextField tinField = new JTextField(employee.getTinNumber());
        editPanel.add(tinField);

        // Pag-IBIG #
        editPanel.add(new JLabel("Pag-IBIG #:"));
        JTextField pagibigField = new JTextField(employee.getPagIbigNumber());
        editPanel.add(pagibigField);

        // Status
        editPanel.add(new JLabel("Status:"));
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Regular", "Probationary"});
        statusCombo.setSelectedItem(employee.getStatus());
        editPanel.add(statusCombo);

        // Position
        editPanel.add(new JLabel("Position:"));
        JTextField positionField = new JTextField(employee.getPosition());
        editPanel.add(positionField);

        // Immediate Supervisor
        editPanel.add(new JLabel("Immediate Supervisor:"));
        JTextField supervisorField = new JTextField(employee.getImmediateSupervisor());
        editPanel.add(supervisorField);

        // Basic Salary
        editPanel.add(new JLabel("Basic Salary:"));
        JTextField basicSalaryField = new JTextField(employee.getBasicSalary());
        editPanel.add(basicSalaryField);

        // Rice Subsidy
        editPanel.add(new JLabel("Rice Subsidy:"));
        JTextField riceSubsidyField = new JTextField(employee.getRiceSubsidy());
        editPanel.add(riceSubsidyField);

        // Phone Allowance
        editPanel.add(new JLabel("Phone Allowance:"));
        JTextField phoneAllowanceField = new JTextField(employee.getPhoneAllowance());
        editPanel.add(phoneAllowanceField);

        // Clothing Allowance
        editPanel.add(new JLabel("Clothing Allowance:"));
        JTextField clothingAllowanceField = new JTextField(employee.getClothingAllowance());
        editPanel.add(clothingAllowanceField);

        // Gross Semi-monthly Rate
        editPanel.add(new JLabel("Gross Semi-monthly Rate:"));
        JTextField grossRateField = new JTextField(employee.getGrossSemiMonthlyRate());
        editPanel.add(grossRateField);

        // Hourly Rate
        editPanel.add(new JLabel("Hourly Rate:"));
        JTextField hourlyRateField = new JTextField(String.valueOf(employee.getHourlyRate()));
        editPanel.add(hourlyRateField);

        // Shift Start Time
        editPanel.add(new JLabel("Shift Start Time (HH:mm):"));
        JTextField shiftStartField = new JTextField(employee.getShiftStartTime().format(TIME_FORMATTER));
        editPanel.add(shiftStartField);

        // Night Shift
        editPanel.add(new JLabel("Night Shift:"));
        JComboBox<Boolean> nightShiftCombo = new JComboBox<>(new Boolean[]{true, false});
        nightShiftCombo.setSelectedItem(employee.isNightShift());
        editPanel.add(nightShiftCombo);

        // Save button
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            try {
                // Update employee object
                employee.setLastName(lastNameField.getText());
                employee.setFirstName(firstNameField.getText());
                employee.setBirthday(birthdayField.getText());
                employee.setAddress(addressField.getText());
                employee.setPhoneNumber(phoneField.getText());
                employee.setSssNumber(sssField.getText());
                employee.setPhilhealthNumber(philhealthField.getText());
                employee.setTinNumber(tinField.getText());
                employee.setPagIbigNumber(pagibigField.getText());
                employee.setStatus((String) statusCombo.getSelectedItem());
                employee.setPosition(positionField.getText());
                employee.setImmediateSupervisor(supervisorField.getText());
                employee.setBasicSalary(basicSalaryField.getText());
                employee.setRiceSubsidy(riceSubsidyField.getText());
                employee.setPhoneAllowance(phoneAllowanceField.getText());
                employee.setClothingAllowance(clothingAllowanceField.getText());
                employee.setGrossSemiMonthlyRate(grossRateField.getText());
                employee.setHourlyRate(Double.parseDouble(hourlyRateField.getText()));
                employee.setShiftStartTime(shiftStartField.getText());
                employee.setNightShift((Boolean) nightShiftCombo.getSelectedItem());

                // Update CSV file using the model class
                boolean success = EmployeeModelFromFile.updateEmployee(employee);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Employee updated successfully!");
                    editFrame.dispose();
                    refreshDisplay();
                } else {
                    throw new IOException("Failed to update employee in file");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error updating employee: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);

        editFrame.add(new JScrollPane(editPanel), BorderLayout.CENTER);
        editFrame.add(buttonPanel, BorderLayout.SOUTH);
        editFrame.setVisible(true);
    }

    private void deleteEmployee() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete employee #" + employee.getEmployeeNumber() + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = EmployeeModelFromFile.deleteEmployee(employee.getEmployeeNumber());
                if (success) {
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
                    dispose();
                } else {
                    throw new IOException("Failed to delete employee from file");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error deleting employee: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshDisplay() {
        dispose();
        new EmployeeDetailsFrame(employee).setVisible(true);
    }

    private JPanel createSalaryPanel() {
        JPanel container = new JPanel(new BorderLayout(5, 5));
        container.setBorder(BorderFactory.createTitledBorder("Salary Computation"));
        
        // Control panel for year/month selection
        JPanel controlPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        // Year selection
        controlPanel.add(new JLabel("Select Year:"));
        JSpinner yearSpinner = new JSpinner(
            new SpinnerNumberModel(LocalDate.now().getYear(), 2000, LocalDate.now().getYear() + 1, 1));
        
        // Fix number format
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(yearSpinner, "#");
        yearSpinner.setEditor(editor);
        controlPanel.add(yearSpinner);
        
        // Month selection
        controlPanel.add(new JLabel("Select Month:"));
        JComboBox<Month> monthComboBox = new JComboBox<>(Month.values());
        monthComboBox.setSelectedItem(LocalDate.now().getMonth());
        controlPanel.add(monthComboBox);
        
        // Compute button
        JButton computeButton = new JButton("Compute Salary");
        controlPanel.add(new JLabel());
        controlPanel.add(computeButton);
        
        // Results area - make it larger
        JTextArea resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane resultsScroll = new JScrollPane(resultsArea);
        resultsScroll.setPreferredSize(new Dimension(500, 600));
        
        computeButton.addActionListener(e -> {
            int year = (int) yearSpinner.getValue();
            Month month = (Month) monthComboBox.getSelectedItem();
            
            try {
                Grosswage grosswage = MotorPHMain.createGrossWageObject(
                    employee.getEmployeeNumber(), 
                    new MotorPHMain.PayCoverage(year, month)
                );
                double gross = grosswage.calculate();
                
                Netwage netwage = MotorPHMain.createNetWageObject(
                    employee.getEmployeeNumber(), 
                    new MotorPHMain.PayCoverage(year, month), 
                    grosswage
                );
                
                resultsArea.setText(formatSalaryResults(year, month, grosswage, netwage));
            } catch (Exception ex) {
                resultsArea.setText("Error computing salary: " + ex.getMessage());
            }
        });
        
        container.add(controlPanel, BorderLayout.NORTH);
        container.add(resultsScroll, BorderLayout.CENTER);
        return container;
    }
    
    private String formatSalaryResults(int year, Month month, Grosswage grosswage, Netwage netwage) {
        DecimalFormat df = new DecimalFormat("#.##");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== SALARY COMPUTATION FOR %s %d ===\n", month, year));
        sb.append("\n=== WORK HOURS ===\n");
        sb.append(String.format("%-25s: %s hrs\n", "Regular Hours", df.format(grosswage.getRegularHours())));
        sb.append(String.format("%-25s: %s hrs\n", "Overtime Hours", df.format(grosswage.getOvertimeHours())));
        
        sb.append("\n=== EARNINGS ===\n");
        sb.append(String.format("%-25s: PHP %s\n", "Regular Pay", df.format(grosswage.getRegularPay())));
        sb.append(String.format("%-25s: PHP %s\n", "Overtime Pay", df.format(grosswage.getOvertimePay())));
        sb.append(String.format("%-25s: PHP %s\n", "Gross Wage", df.format(grosswage.calculate())));
        
        sb.append("\n=== DEDUCTIONS ===\n");
        sb.append(String.format("%-25s: PHP %s\n", "SSS Contribution", df.format(netwage.getSSSDeduction())));
        sb.append(String.format("%-25s: PHP %s\n", "PhilHealth Contribution", df.format(netwage.getPhilhealthDeduction())));
        sb.append(String.format("%-25s: PHP %s\n", "Withholding Tax", df.format(netwage.getWithholdingTax())));
        
        sb.append("\n=== NET SALARY ===\n");
        sb.append(String.format("%-25s: PHP %s\n", "Take Home Pay", 
            df.format(grosswage.calculate() - netwage.getTotalDeductions() - netwage.getWithholdingTax())));
        
        return sb.toString();
    }
}