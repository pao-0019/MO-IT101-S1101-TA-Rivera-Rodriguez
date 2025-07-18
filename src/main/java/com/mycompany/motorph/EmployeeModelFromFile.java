package com.mycompany.motorph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author angeliquerivera
 */

public class EmployeeModelFromFile {
    private static String filePath = "src/main/resources/EmployeeData.csv";
    private static List<Employee> employees = new ArrayList<>();

    /**
     * Loads employee data from the CSV file
     * @param filePath Path to the employee data file
     * @return true if loading succeeded, false otherwise
     */
    public static boolean loadEmployeesFromFile(String filePath) {
        List<Employee> loadedEmployees = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip header row
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] rowData = parseCSVLine(line);
                    
                    if (rowData.length >= 19) { // Ensure we have all required fields
                        loadedEmployees.add(new Employee(rowData));
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing employee data: " + e.getMessage());
                    continue; // Skip malformed lines but continue loading others
                }
            }
            
            // Sort employees by ID after loading
            loadedEmployees.sort(Comparator.comparing(Employee::getEmployeeNumber));
            
            // Only update if loading succeeded
            employees = loadedEmployees;
            EmployeeModelFromFile.filePath = filePath;
            return true;
        } catch (IOException e) {
            System.err.println("Error loading employee data: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds a new employee to the system and saves to file
     * @param employee The employee to add
     * @return true if successful, false otherwise
     */
    public static boolean addEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        
        // Check if employee already exists
        if (getEmployeeById(employee.getEmployeeNumber()) != null) {
            System.err.println("Employee with ID " + employee.getEmployeeNumber() + " already exists");
            return false;
        }
        
        employees.add(employee);
        // Maintain sorted order
        employees.sort(Comparator.comparing(Employee::getEmployeeNumber));
        return saveEmployeesToFile();
    }

    /**
     * Updates an existing employee in the system and saves to file
     * @param employee The updated employee data
     * @return true if successful, false otherwise
     */
    public static boolean updateEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        
        Employee existing = getEmployeeById(employee.getEmployeeNumber());
        if (existing == null) {
            System.err.println("Employee with ID " + employee.getEmployeeNumber() + " not found");
            return false;
        }
        
        // Update all fields
        existing.setLastName(employee.getLastName());
        existing.setFirstName(employee.getFirstName());
        existing.setBirthday(employee.getBirthday());
        existing.setAddress(employee.getAddress());
        existing.setPhoneNumber(employee.getPhoneNumber());
        existing.setSssNumber(employee.getSssNumber());
        existing.setPhilhealthNumber(employee.getPhilhealthNumber());
        existing.setTinNumber(employee.getTinNumber());
        existing.setPagIbigNumber(employee.getPagIbigNumber());
        existing.setStatus(employee.getStatus());
        existing.setPosition(employee.getPosition());
        existing.setImmediateSupervisor(employee.getImmediateSupervisor());
        existing.setBasicSalary(employee.getBasicSalary());
        existing.setRiceSubsidy(employee.getRiceSubsidy());
        existing.setPhoneAllowance(employee.getPhoneAllowance());
        existing.setClothingAllowance(employee.getClothingAllowance());
        existing.setGrossSemiMonthlyRate(employee.getGrossSemiMonthlyRate());
        existing.setHourlyRate(employee.getHourlyRate());
        existing.setShiftStartTime(employee.getShiftStartTime().toString());
        existing.setNightShift(employee.isNightShift());
        
        return saveEmployeesToFile();
    }

    /**
     * Deletes an employee from the system and saves to file
     * @param employeeId The ID of the employee to delete
     * @return true if successful, false otherwise
     */
    public static boolean deleteEmployee(String employeeId) {
        Employee employee = getEmployeeById(employeeId);
        if (employee == null) {
            System.err.println("Employee with ID " + employeeId + " not found");
            return false;
        }
        
        boolean removed = employees.remove(employee);
        if (removed) {
            return saveEmployeesToFile();
        }
        return false;
    }

    /**
     * Generates the next available employee number
     * @return String representing the next available employee number
     */
    public static String generateNextEmployeeNumber() {
        if (employees.isEmpty()) {
            return "10001"; // Starting number
        }
        
        // Find the first gap in numbering
        for (int i = 0; i < employees.size() - 1; i++) {
            int current = Integer.parseInt(employees.get(i).getEmployeeNumber());
            int next = Integer.parseInt(employees.get(i + 1).getEmployeeNumber());
            if (next > current + 1) {
                return String.valueOf(current + 1);
            }
        }
        
        // If no gaps, return next sequential number
        int lastNumber = Integer.parseInt(employees.get(employees.size() - 1).getEmployeeNumber());
        return String.valueOf(lastNumber + 1);
    }

    /**
     * Saves all employees to the CSV file
     * @return true if successful, false otherwise
     */
    private static boolean saveEmployeesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            bw.write("\"Employee #\",\"Last Name\",\"First Name\",\"Birthday\",\"Address\"," +
                    "\"Phone Number\",\"SSS #\",\"PhilHealth #\",\"TIN #\",\"Pag-IBIG #\"," +
                    "\"Status\",\"Position\",\"Immediate Supervisor\",\"Basic Salary\"," +
                    "\"Rice Subsidy\",\"Phone Allowance\",\"Clothing Allowance\"," +
                    "\"Gross Semi-monthly Rate\",\"Hourly Rate\",\"Shift Start Time\",\"Night Shift\"");
            bw.newLine();
            
            // Write employee data in sorted order
            for (Employee emp : employees) {
                bw.write(emp.toCSV());
                bw.newLine();
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error saving employee data: " + e.getMessage());
            return false;
        }
    }

    /**
     * Initializes employee data by loading from default file path
     */
    public static void initialize() {
        loadEmployeesFromFile(filePath);
    }

    /**
     * Parses a CSV line, handling quoted values and commas within quotes
     * @param line The CSV line to parse
     * @return Array of string values
     */
    private static String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder value = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(value.toString().trim());
                value = new StringBuilder();
            } else {
                value.append(c);
            }
        }
        // Add the last value
        values.add(value.toString().trim());

        return values.toArray(new String[0]);
    }

    /**
     * Gets an unmodifiable list of all employees
     * @return List of Employee objects
     */
    public static List<Employee> getEmployeeModelList() {
        return Collections.unmodifiableList(employees);
    }

    /**
     * Finds an employee by ID
     * @param employeeId The employee ID to search for
     * @return Employee object if found, null otherwise
     */
    public static Employee getEmployeeById(String employeeId) {
        return employees.stream()
                .filter(e -> e.getEmployeeNumber().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates the file path for the employee data
     * @param newFilePath New path to the employee data file
     */
    public static void setFilePath(String newFilePath) {
        filePath = newFilePath;
    }
}