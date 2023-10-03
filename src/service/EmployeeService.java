// EmployeeService.java
package service;

import dao.EmployeeDAO;
import dao.UserDAO;
import dto.EmployeeDTO;
import util.Tools;

import java.util.Date;
import java.util.Scanner;

public class EmployeeService {
    private final EmployeeDAO employeeDAO;
    private final UserDAO userDAO;

    public EmployeeService(EmployeeDAO employeeDAO, UserDAO userDAO) {
        this.employeeDAO = employeeDAO;
        this.userDAO = userDAO;
    }

    public void addEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.print("Enter birth date:");
        String birthDate = scanner.nextLine();
        Date birthDate1 = Tools.stringToDate(birthDate);
        System.out.print("Enter phone number:");
        int phone = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the register number:");
        String registerNumber = scanner.nextLine();
        System.out.print("Enter the email:");
        String email = scanner.nextLine();
        System.out.print("Enter recruitment date:");
        String recruitmentDate = scanner.nextLine();

        Date recruitmentDate1 = Tools.stringToDate(recruitmentDate);

        EmployeeDTO newEmployee = new EmployeeDTO(registerNumber, recruitmentDate1, email, firstName, lastName, birthDate1, phone);

        // Implement transaction management here
        try {
            userDAO.addUser(newEmployee);
            employeeDAO.addEmployee(newEmployee);
            System.out.println("Added successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
