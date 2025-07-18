package com.mycompany.motorph;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 *
 * @author angeliquerivera
 */
public class Employee {
    private String employeeNumber;
    private String lastName;
    private String firstName;
    private String birthday;
    private String address;
    private String phoneNumber;
    private String sssNumber;
    private String philhealthNumber;
    private String tinNumber;
    private String pagIbigNumber;
    private String status;
    private String position;
    private String immediateSupervisor;
    private String basicSalary;
    private String riceSubsidy;
    private String phoneAllowance;
    private String clothingAllowance;
    private String grossSemiMonthlyRate;
    private double hourlyRate;
    private LocalTime shiftStartTime;
    private boolean nightShift;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private static final LocalTime DEFAULT_SHIFT_START = LocalTime.of(8, 0);

    /**
     * Constructor from CSV data array
     * @param data Array of strings from CSV file
     * @throws IllegalArgumentException if data is invalid
     */
    public Employee(String[] data) {
        if (data == null || data.length < 19) {
            throw new IllegalArgumentException("Insufficient data to create Employee object");
        }

        this.employeeNumber = parseEmployeeNumber(data[0]);
        this.lastName = getValue(data, 1);
        this.firstName = getValue(data, 2);
        this.birthday = getValue(data, 3);
        this.address = getValue(data, 4);
        this.phoneNumber = getValue(data, 5);
        this.sssNumber = getValue(data, 6);
        this.philhealthNumber = getValue(data, 7);
        this.tinNumber = getValue(data, 8);
        this.pagIbigNumber = getValue(data, 9);
        this.status = getValue(data, 10);
        this.position = getValue(data, 11);
        this.immediateSupervisor = getValue(data, 12);
        this.basicSalary = getValue(data, 13);
        this.riceSubsidy = getValue(data, 14);
        this.phoneAllowance = getValue(data, 15);
        this.clothingAllowance = getValue(data, 16);
        this.grossSemiMonthlyRate = getValue(data, 17);
        this.hourlyRate = parseDoubleValue(data[18]);
        this.shiftStartTime = parseShiftStartTime(getValue(data, 19));
        this.nightShift = parseNightShift(getValue(data, 20));
    }

    /**
     * Constructor with individual parameters
     */
    public Employee(String employeeNumber, String lastName, String firstName, 
                   String birthday, String address, String phoneNumber,
                   String sssNumber, String philhealthNumber, String tinNumber,
                   String pagIbigNumber, String status, String position,
                   String immediateSupervisor, String basicSalary,
                   String riceSubsidy, String phoneAllowance,
                   String clothingAllowance, String grossSemiMonthlyRate,
                   double hourlyRate, String shiftStartTime, boolean nightShift) {
        this.employeeNumber = parseEmployeeNumber(employeeNumber);
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sssNumber = sssNumber;
        this.philhealthNumber = philhealthNumber;
        this.tinNumber = tinNumber;
        this.pagIbigNumber = pagIbigNumber;
        this.status = status;
        this.position = position;
        this.immediateSupervisor = immediateSupervisor;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        this.hourlyRate = hourlyRate;
        this.shiftStartTime = parseShiftStartTime(shiftStartTime);
        this.nightShift = nightShift;
    }

    private String parseEmployeeNumber(String empNum) {
        if (empNum == null || empNum.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee number cannot be empty");
        }
        try {
            return String.valueOf((int) Double.parseDouble(empNum.trim()));
        } catch (NumberFormatException e) {
            System.err.println("Invalid employee number format: " + empNum);
            return empNum.trim();
        }
    }

    private String getValue(String[] data, int index) {
        return (index < data.length) ? data[index].trim() : "";
    }

    private double parseDoubleValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + value);
            return 0.0;
        }
    }

    private LocalTime parseShiftStartTime(String timeString) {
        if (timeString == null || timeString.trim().isEmpty()) {
            return DEFAULT_SHIFT_START;
        }
        try {
            return LocalTime.parse(timeString.trim());
        } catch (DateTimeParseException e) {
            System.err.println("Invalid time format: " + timeString);
            return DEFAULT_SHIFT_START;
        }
    }

    private boolean parseNightShift(String value) {
        if (value == null) return false;
        String val = value.trim().toLowerCase();
        return val.equals("true") || val.equals("yes") || val.equals("1");
    }

    // Getters
    public String getEmployeeNumber() { return employeeNumber; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getBirthday() { return birthday; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getSssNumber() { return sssNumber; }
    public String getPhilhealthNumber() { return philhealthNumber; }
    public String getTinNumber() { return tinNumber; }
    public String getPagIbigNumber() { return pagIbigNumber; }
    public String getStatus() { return status; }
    public String getPosition() { return position; }
    public String getImmediateSupervisor() { return immediateSupervisor; }
    public String getBasicSalary() { return basicSalary; }
    public String getRiceSubsidy() { return riceSubsidy; }
    public String getPhoneAllowance() { return phoneAllowance; }
    public String getClothingAllowance() { return clothingAllowance; }
    public String getGrossSemiMonthlyRate() { return grossSemiMonthlyRate; }
    public double getHourlyRate() { return hourlyRate; }
    public LocalTime getShiftStartTime() { return shiftStartTime; }
    public boolean isNightShift() { return nightShift; }

    // Setters
    public void setEmployeeNumber(String employeeNumber) { 
        this.employeeNumber = parseEmployeeNumber(employeeNumber); 
    }
    public void setLastName(String lastName) { 
        this.lastName = lastName != null ? lastName.trim() : ""; 
    }
    public void setFirstName(String firstName) { 
        this.firstName = firstName != null ? firstName.trim() : ""; 
    }
    public void setBirthday(String birthday) { 
        this.birthday = birthday != null ? birthday.trim() : ""; 
    }
    public void setAddress(String address) { 
        this.address = address != null ? address.trim() : ""; 
    }
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : ""; 
    }
    public void setSssNumber(String sssNumber) { 
        this.sssNumber = sssNumber != null ? sssNumber.trim() : ""; 
    }
    public void setPhilhealthNumber(String philhealthNumber) { 
        this.philhealthNumber = philhealthNumber != null ? philhealthNumber.trim() : ""; 
    }
    public void setTinNumber(String tinNumber) { 
        this.tinNumber = tinNumber != null ? tinNumber.trim() : ""; 
    }
    public void setPagIbigNumber(String pagIbigNumber) { 
        this.pagIbigNumber = pagIbigNumber != null ? pagIbigNumber.trim() : ""; 
    }
    public void setStatus(String status) { 
        this.status = status != null ? status.trim() : ""; 
    }
    public void setPosition(String position) { 
        this.position = position != null ? position.trim() : ""; 
    }
    public void setImmediateSupervisor(String immediateSupervisor) { 
        this.immediateSupervisor = immediateSupervisor != null ? immediateSupervisor.trim() : ""; 
    }
    public void setBasicSalary(String basicSalary) { 
        this.basicSalary = basicSalary != null ? basicSalary.trim() : ""; 
    }
    public void setRiceSubsidy(String riceSubsidy) { 
        this.riceSubsidy = riceSubsidy != null ? riceSubsidy.trim() : ""; 
    }
    public void setPhoneAllowance(String phoneAllowance) { 
        this.phoneAllowance = phoneAllowance != null ? phoneAllowance.trim() : ""; 
    }
    public void setClothingAllowance(String clothingAllowance) { 
        this.clothingAllowance = clothingAllowance != null ? clothingAllowance.trim() : ""; 
    }
    public void setGrossSemiMonthlyRate(String grossSemiMonthlyRate) { 
        this.grossSemiMonthlyRate = grossSemiMonthlyRate != null ? grossSemiMonthlyRate.trim() : ""; 
    }
    public void setHourlyRate(double hourlyRate) { 
        this.hourlyRate = hourlyRate; 
    }
    public void setShiftStartTime(String shiftStartTime) { 
        this.shiftStartTime = parseShiftStartTime(shiftStartTime); 
    }
    public void setNightShift(boolean nightShift) { 
        this.nightShift = nightShift; 
    }

    @Override
    public String toString() {
        return toString(true);
    }

    public String toString(boolean detailed) {
        if (detailed) {
            return String.format(
                "Employee ID: %s%nName: %s, %s%nBirthday: %s%nAddress: %s%nPhone: %s%n" +
                "SSS: %s%nPhilHealth: %s%nTIN: %s%nPag-IBIG: %s%nPosition: %s%n" +
                "Status: %s%nSupervisor: %s%nBasic Salary: %s%nHourly Rate: %.2f%n" +
                "Allowances:%n  Rice: %s%n  Phone: %s%n  Clothing: %s%n" +
                "Gross Semi-Monthly: %s%nShift: %s %s",
                employeeNumber,
                lastName, firstName,
                birthday,
                address,
                phoneNumber,
                sssNumber,
                philhealthNumber,
                tinNumber,
                pagIbigNumber,
                position,
                status,
                immediateSupervisor,
                basicSalary,
                hourlyRate,
                riceSubsidy,
                phoneAllowance,
                clothingAllowance,
                grossSemiMonthlyRate,
                shiftStartTime,
                nightShift ? "(Night Shift)" : ""
            );
        }
        return String.format("%s %s (ID: %s)", firstName, lastName, employeeNumber);
    }

    public String toCSV() {
        return String.format(
            "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"," +
            "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%.2f,\"%s\",\"%b\"",
            employeeNumber,
            lastName,
            firstName,
            birthday,
            address,
            phoneNumber,
            sssNumber,
            philhealthNumber,
            tinNumber,
            pagIbigNumber,
            status,
            position,
            immediateSupervisor,
            basicSalary,
            riceSubsidy,
            phoneAllowance,
            clothingAllowance,
            grossSemiMonthlyRate,
            hourlyRate,
            shiftStartTime.toString(),
            nightShift
        );
    }

    public String getSupervisor() {
        return immediateSupervisor;
    }

    public String getBirthDate() {
        return birthday;
    }
}