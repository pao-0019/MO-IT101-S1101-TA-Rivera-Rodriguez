package com.mycompany.motorph;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;  // Added this import
import java.time.Month;
import java.util.List;

public class Netwage extends Calculation {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private final Grosswage grosswage;
    private final String employeeID;
    private final String employeeName;
    private final double gross;
    private final double hours;
    private final Month targetMonth;
    private final int targetYear;
    
    // Cached calculations
    private Double sssDeduction;
    private Double philhealthDeduction;
    private Double pagibigDeduction;
    private Double lateDeduction;
    private Double withholdingTax;

    public Netwage(String employeeID, String employeeName, double gross, double hours, 
                  Grosswage grosswage, Month targetMonth, int targetYear) {
        if (employeeID == null || employeeID.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty");
        }
        if (employeeName == null || employeeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        if (gross < 0) {
            throw new IllegalArgumentException("Gross wage cannot be negative");
        }
        if (hours < 0) {
            throw new IllegalArgumentException("Hours cannot be negative");
        }
        if (grosswage == null) {
            throw new IllegalArgumentException("Grosswage cannot be null");
        }
        if (targetMonth == null) {
            throw new IllegalArgumentException("Month cannot be null");
        }
        if (targetYear < 2000 || targetYear > LocalDate.now().getYear() + 1) {
            throw new IllegalArgumentException("Invalid year");
        }

        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.gross = gross;
        this.hours = hours;
        this.grosswage = grosswage;
        this.targetMonth = targetMonth;
        this.targetYear = targetYear;
    }

    @Override
    public double calculate() {
        double totalDeductions = getTotalDeductions();
        double withholdingTax = getWithholdingTax();
        double netWage = gross - totalDeductions - withholdingTax;
        return Double.parseDouble(decimalFormat.format(netWage));
    }

    public double getSSSDeduction() {
        if (sssDeduction == null) {
            Calculation sss = new SSS(grosswage);
            sssDeduction = sss.calculate();
        }
        return sssDeduction;
    }

    public double getPhilhealthDeduction() {
        if (philhealthDeduction == null) {
            Calculation philhealth = new Philhealth(grosswage);
            philhealthDeduction = philhealth.calculate();
        }
        return philhealthDeduction;
    }

    public double getPagIbigDeduction() {
        if (pagibigDeduction == null) {
            Calculation pagibig = new Pagibig(grosswage);
            pagibigDeduction = pagibig.calculate();
        }
        return pagibigDeduction;
    }

    public double getLateDeduction() {
        if (lateDeduction == null) {
            lateDeduction = calculateMonthlyLatePenalty();
        }
        return lateDeduction;
    }

    private double calculateMonthlyLatePenalty() {
        List<AttendanceRecord> records = AttendanceRecord.getAttendanceRecords();
        double totalPenalty = 0.0;
        final double minuteRate = grosswage.getHourlyRate() / 60.0;
        final LocalTime shiftStart = grosswage.getShiftStartTime();
        final LocalTime lateThreshold = shiftStart.plusMinutes(15);

        if (records != null) {
            for (AttendanceRecord record : records) {
                if (record != null && 
                    record.getId() != null && 
                    record.getId().equals(employeeID) && 
                    isDateInTargetMonth(record.getDate())) {
                    LocalTime timeIn = record.getTimeIn();
                    if (timeIn != null && timeIn.isAfter(lateThreshold)) {
                        long minutesLate = java.time.Duration.between(lateThreshold, timeIn).toMinutes();
                        totalPenalty += minuteRate * minutesLate;
                    }
                }
            }
        }
        return totalPenalty;
    }

    private boolean isDateInTargetMonth(LocalDate date) {
        return date != null && 
               date.getYear() == targetYear && 
               date.getMonth() == targetMonth;
    }

    public double getTotalDeductions() {
        return getSSSDeduction() + 
               getPhilhealthDeduction() + 
               getPagIbigDeduction() + 
               getLateDeduction();
    }

    public double getTaxableIncome() {
        return gross - getTotalDeductions();
    }

    public double getWithholdingTax() {
        if (withholdingTax == null) {
            double monthlyTaxableIncome = getTaxableIncome();
            withholdingTax = new WithholdingTax(monthlyTaxableIncome, false).calculate();
        }
        return withholdingTax;
    }

    // Getters
    public Grosswage getGrosswage() { return grosswage; }
    public String getEmployeeID() { return employeeID; }
    public String getEmployeeName() { return employeeName; }
    public double getGross() { return gross; }
    public double getHours() { return hours; }
    public Month getTargetMonth() { return targetMonth; }
    public int getTargetYear() { return targetYear; }

    public String getPayPeriod() {
        return targetMonth.toString() + " " + targetYear;
    }

    public String getCalculationBreakdown() {
        return String.format(
            "Payroll Calculation for %s (%s)%n" +
            "Pay Period: %s%n" +
            "Gross Wage: ₱%,.2f%n" +
            "Total Deductions: ₱%,.2f%n" +
            "  - SSS: ₱%,.2f%n" +
            "  - PhilHealth: ₱%,.2f%n" +
            "  - Pag-IBIG: ₱%,.2f%n" +
            "  - Late Penalty: ₱%,.2f%n" +
            "Taxable Income: ₱%,.2f%n" +
            "Withholding Tax: ₱%,.2f%n" +
            "Net Wage: ₱%,.2f",
            employeeName, employeeID,
            getPayPeriod(),
            gross,
            getTotalDeductions(),
            getSSSDeduction(),
            getPhilhealthDeduction(),
            getPagIbigDeduction(),
            getLateDeduction(),
            getTaxableIncome(),
            getWithholdingTax(),
            calculate()
        );
    }
}