/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.motorph;

import com.mycompany.motorph.Grosswage;
import com.mycompany.motorph.Netwage;
import java.text.DecimalFormat;
import java.time.Month;

/**
 *
 * @author angeliquerivera
 */

public class SalaryFormatter {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    
    public static String format(double value) {
        return decimalFormat.format(value);
    }
    
    public static String formatSalaryResults(int year, Month month, Grosswage grosswage, Netwage netwage) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("=== SALARY COMPUTATION FOR %s %d ===\n", month, year));
        sb.append("\n=== WORK HOURS ===\n");
        sb.append(String.format("%-25s: %s hrs\n", "Regular Hours", format(grosswage.getRegularHours())));
        sb.append(String.format("%-25s: %s hrs\n", "Overtime Hours", format(grosswage.getOvertimeHours())));
        sb.append(String.format("%-25s: %s hrs\n", "Total Hours Worked", format(grosswage.getHoursWorked())));
        
        sb.append("\n=== EARNINGS ===\n");
        sb.append(String.format("%-25s: PHP %s\n", "Regular Pay", format(grosswage.getRegularPay())));
        sb.append(String.format("%-25s: PHP %s\n", "Overtime Pay", format(grosswage.getOvertimePay())));
        sb.append(String.format("%-25s: PHP %s\n", "Gross Wage", format(grosswage.calculate())));
        
        sb.append("\n=== DEDUCTIONS ===\n");
        sb.append(String.format("%-25s: PHP %s\n", "SSS Contribution", format(netwage.getSSSDeduction())));
        sb.append(String.format("%-25s: PHP %s\n", "PhilHealth Contribution", format(netwage.getPhilhealthDeduction())));
        sb.append(String.format("%-25s: PHP %s\n", "Pag-IBIG Contribution", format(netwage.getPagIbigDeduction())));
        sb.append(String.format("%-25s: PHP %s\n", "Late/Tardy Deductions", format(netwage.getLateDeduction())));
        sb.append(String.format("%-25s: PHP %s\n", "Withholding Tax", format(netwage.getWithholdingTax())));
        sb.append(String.format("%-25s: PHP %s\n", "Total Deductions", format(
            netwage.getTotalDeductions() + netwage.getWithholdingTax())));
        
        sb.append("\n=== NET SALARY ===\n");
        sb.append(String.format("%-25s: PHP %s\n", "Take Home Pay", 
            format(grosswage.calculate() - netwage.getTotalDeductions() - netwage.getWithholdingTax())));
        
        return sb.toString();
    }
}