package com.mycompany.motorph;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author angeliquerivera
 */
public class EmployeeTable {
    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane scrollPane;
    
    public EmployeeTable() {
        String[] columns = {"Employee #", "Last Name", "First Name", "SSS #", "PhilHealth #", "TIN #", "Pag-IBIG #"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(table);
    }
    
    public void refresh(List<Employee> employees) {
        model.setRowCount(0);
        for (Employee emp : employees) {
            model.addRow(new Object[]{
                emp.getEmployeeNumber(),
                emp.getLastName(),
                emp.getFirstName(),
                emp.getSssNumber(),
                emp.getPhilhealthNumber(),
                emp.getTinNumber(),
                emp.getPagIbigNumber()
            });
        }
    }
    
    public Employee getSelectedEmployee() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String empId = (String) model.getValueAt(row, 0);
            return EmployeeModelFromFile.getEmployeeById(empId);
        }
        return null;
    }
    
    public void addSelectionListener(ListSelectionListener listener) {
        table.getSelectionModel().addListSelectionListener(listener);
    }
    
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    DefaultTableModel getModel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}