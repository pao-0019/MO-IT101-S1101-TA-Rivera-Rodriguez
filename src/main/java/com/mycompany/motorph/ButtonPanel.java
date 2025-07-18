/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.motorph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author angeliquerivera
 */

public class ButtonPanel {
    private final JPanel panel;
    private final JButton backButton;
    private final JButton refreshButton;
    private final JButton addEmployeeButton;
    private final JButton viewButton;
    private final JButton logoutButton; 
    private final MainFrame mainFrame;

    public ButtonPanel(EmployeeTable employeeTable, MainFrame mainFrame) {
          this.mainFrame = mainFrame; 
          
        // Initialize panel
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Create buttons with consistent styling
        backButton = createStyledButton("Back", new Color(200, 200, 200));
        refreshButton = createStyledButton("Refresh", new Color(173, 216, 230));
        addEmployeeButton = createStyledButton("Add Employee", new Color(144, 238, 144));
        viewButton = createStyledButton("View Employee", new Color(255, 218, 185));
        logoutButton = createStyledButton("Log Out", new Color(255, 99, 71));
        // Initial state
        setViewButtonEnabled(false);

        // Add buttons to panel
        panel.add(backButton);
        panel.add(refreshButton);
        panel.add(addEmployeeButton);
        panel.add(viewButton);
        panel.add(logoutButton);
        
         // Logout button action listener
    logoutButton.addActionListener(e -> mainFrame.showLogin()); 

        // Configure view button action
        viewButton.addActionListener(e -> {
            Employee selected = employeeTable.getSelectedEmployee();
            if (selected != null) {
                System.out.println("[DEBUG] Viewing employee: " + selected.getEmployeeNumber());
                new EmployeeDetailsFrame(selected).setVisible(true);
            }
        });
        
     
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 30));
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        return button;
    }

    public void setBackButtonAction(ActionListener action) {
        clearButtonActions(backButton);
        backButton.addActionListener(action);
    }

    public void setRefreshAction(ActionListener action) {
        clearButtonActions(refreshButton);
        refreshButton.addActionListener(action);
    }

    public void setAddEmployeeAction(ActionListener action) {
    // Clear any existing listeners
    for (ActionListener al : addEmployeeButton.getActionListeners()) {
        addEmployeeButton.removeActionListener(al);
    }
    
    // Add new listener
    addEmployeeButton.addActionListener(e -> {
        System.out.println("[DEBUG] Add Employee button pressed");
        if (SwingUtilities.isEventDispatchThread()) {
            action.actionPerformed(e);
        } else {
            SwingUtilities.invokeLater(() -> action.actionPerformed(e));
        }
    });
}
    public void setViewButtonEnabled(boolean enabled) {
        viewButton.setEnabled(enabled);
        viewButton.setBackground(enabled ? new Color(255, 218, 185) : Color.LIGHT_GRAY);
    }

    private void clearButtonActions(JButton button) {
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}