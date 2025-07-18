package com.mycompany.motorph;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import javax.swing.SwingUtilities;

public class MotorPHMain {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private static final String EMPLOYEE_DATA_PATH = "src/main/resources/EmployeeData.csv";
    private static final String ATTENDANCE_DATA_PATH = "src/main/resources/AttendanceRecord.csv";

    public static void main(String[] args) {
        // Initialize and show the application using the new MainFrame
        SwingUtilities.invokeLater(() -> new MainFrame().show());
    }

    // ================== UTILITY METHODS ================== //

    public static Employee findEmployeeById(String empId) {
        List<Employee> employees = EmployeeModelFromFile.getEmployeeModelList();
        for (Employee employee : employees) {
            if (employee.getEmployeeNumber().equals(empId)) {
                return employee;
            }
        }
        return null;
    }

    public static PayCoverage getValidPayCoverage(Scanner scanner) {
        System.out.println("\n=== PAY PERIOD DETAILS ===");
        int year = getValidYear(scanner);
        Month month = getValidMonth(scanner);
        
        LocalDate currentDate = LocalDate.now();
        if (year > currentDate.getYear() + 1) {
            throw new IllegalArgumentException("Year cannot be more than 1 year in the future");
        }
        
        return new PayCoverage(year, month);
    }

    public static int getValidYear(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter Year (YYYY): ");
                int year = scanner.nextInt();
                scanner.nextLine();
                
                if (year < 2000 || year > LocalDate.now().getYear() + 1) {
                    throw new IllegalArgumentException("Year must be between 2000 and " + (LocalDate.now().getYear() + 1));
                }
                return year;
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a 4-digit year.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static Month getValidMonth(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Available Months:");
                for (Month m : Month.values()) {
                    System.out.println((m.ordinal() + 1) + ". " + m);
                }
                System.out.print("Enter Month (1-12): ");
                int monthNum = scanner.nextInt();
                scanner.nextLine();
                
                if (monthNum < 1 || monthNum > 12) {
                    throw new IllegalArgumentException("Month must be between 1-12");
                }
                return Month.of(monthNum);
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a number between 1-12.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static String getValidEmployeeNumber(Scanner scanner) {
        while (true) {
            try {
                System.out.print("\nEnter Employee ID: ");
                String empId = scanner.next().trim();
                
                if (!empId.matches("\\d+")) {
                    throw new IllegalArgumentException("Employee ID must contain only numbers");
                }
                
                if (findEmployeeById(empId) == null) {
                    throw new NoSuchElementException("Employee with ID " + empId + " not found");
                }
                
                return empId;
            } catch (IllegalArgumentException | NoSuchElementException e) {
                System.err.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
                scanner.nextLine();
            }
        }
    }

    // ================== PAYROLL CALCULATION METHODS ================== //

    public static Grosswage createGrossWageObject(String empId, PayCoverage coverage) {
        Employee employee = findEmployeeById(empId);
        return new Grosswage(
            empId, 
            employee.getFirstName(), 
            employee.getLastName(), 
            coverage.year(), 
            coverage.month(),
            employee.getShiftStartTime(), 
            employee.isNightShift()
        );
    }

    public static Netwage createNetWageObject(String empId, PayCoverage coverage, Grosswage grosswage) {
        Employee employee = findEmployeeById(empId);
        String employeeName = employee.getLastName() + ", " + employee.getFirstName();
        return new Netwage(
            empId, 
            employeeName, 
            grosswage.calculate(), 
            grosswage.getHoursWorked(),
            grosswage, 
            coverage.month(), 
            coverage.year()
        );
    }

    // ================== HELPER RECORDS ================== //

    public record PayCoverage(int year, Month month) {}
}