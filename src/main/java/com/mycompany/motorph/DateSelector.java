/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.motorph;

import javax.swing.*;
import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author angeliquerivera
 */

public class DateSelector {
    private JSpinner yearSpinner;
    private JComboBox<Month> monthComboBox;
    
    public DateSelector() {
        yearSpinner = new JSpinner(
            new SpinnerNumberModel(LocalDate.now().getYear(), 2000, LocalDate.now().getYear() + 1, 1));
        JSpinner.NumberEditor yearEditor = new JSpinner.NumberEditor(yearSpinner, "#");
        yearSpinner.setEditor(yearEditor);
        
        monthComboBox = new JComboBox<>(Month.values());
        monthComboBox.setSelectedItem(LocalDate.now().getMonth());
    }
    
    public JSpinner getYearSpinner() {
        return yearSpinner;
    }
    
    public JComboBox<Month> getMonthComboBox() {
        return monthComboBox;
    }
    
    public int getSelectedYear() {
        return (int) yearSpinner.getValue();
    }
    
    public Month getSelectedMonth() {
        return (Month) monthComboBox.getSelectedItem();
    }
}