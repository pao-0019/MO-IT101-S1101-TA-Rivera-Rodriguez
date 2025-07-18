/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.motorph;

import com.mycompany.motorph.Employee;
import com.mycompany.motorph.MotorPHMain;
import com.mycompany.motorph.Grosswage;
import com.mycompany.motorph.Netwage;
import com.mycompany.motorph.DateSelector;
import com.mycompany.motorph.SalaryFormatter;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author angeliquerivera
 */

public class SalaryComputationForm {
    private final JFrame frame;
    private final Employee employee;
    private final DateSelector dateSelector;
    private final JTextArea resultsArea;

    public SalaryComputationForm(Employee employee) {
        this.employee = employee;
        this.dateSelector = new DateSelector();
        this.frame = new JFrame("Salary Computation - " + employee.getEmployeeNumber());
        this.resultsArea = new JTextArea();
        
        initialize();
    }

    private void initialize() {
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header with employee info
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Date selection panel
        JPanel datePanel = createDateSelectionPanel();
        mainPanel.add(datePanel, BorderLayout.CENTER);

        // Results panel
        JPanel resultsPanel = createResultsPanel();
        mainPanel.add(resultsPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Employee Information"));
        
        JTextArea employeeInfo = new JTextArea(employee.toString(false));
        employeeInfo.setEditable(false);
        employeeInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        employeeInfo.setBackground(panel.getBackground());
        
        panel.add(new JScrollPane(employeeInfo), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createDateSelectionPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Select Pay Period"));

        // Year selection
        panel.add(new JLabel("Year:"));
        panel.add(dateSelector.getYearSpinner());

        // Month selection
        panel.add(new JLabel("Month:"));
        panel.add(dateSelector.getMonthComboBox());

        // Compute button
        JButton computeButton = new JButton("Compute Salary");
        computeButton.addActionListener(e -> computeSalary());
        
        panel.add(new JLabel()); // Spacer
        panel.add(computeButton);

        return panel;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Salary Computation Results"));

        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setPreferredSize(new Dimension(700, 250));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void computeSalary() {
        try {
            int year = dateSelector.getSelectedYear();
            Month month = dateSelector.getSelectedMonth();

            // Validate date isn't in the future
            LocalDate selectedDate = LocalDate.of(year, month, 1);
            if (selectedDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Selected period cannot be in the future");
            }

            // Compute gross wage
            Grosswage grosswage = MotorPHMain.createGrossWageObject(
                employee.getEmployeeNumber(),
                new MotorPHMain.PayCoverage(year, month)
            );
            double gross = grosswage.calculate();

            // Compute net wage
            Netwage netwage = MotorPHMain.createNetWageObject(
                employee.getEmployeeNumber(),
                new MotorPHMain.PayCoverage(year, month),
                grosswage
            );

            // Display results
            resultsArea.setText(SalaryFormatter.formatSalaryResults(year, month, grosswage, netwage));
            
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, 
                ex.getMessage(), 
                "Invalid Date", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, 
                "Error computing salary: " + ex.getMessage(), 
                "Calculation Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void show() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
