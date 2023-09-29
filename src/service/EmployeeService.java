// EmployeeService.java
package service;

import dao.EmployeeDAO;
import dao.UserDAO;
import dto.EmployeeDTO;
import dto.UserDTO;
import util.Tools;

import java.util.Date;
import java.util.List;
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
    public void deleteEmployee(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter employee register number:");
        String registerNumber = scanner.nextLine();

        int rowsAffected = employeeDAO.deleteEmployee(registerNumber);

        if (rowsAffected > 0) {
            System.out.println("Employee with register number " + registerNumber + " deleted successfully.");
        } else {
            System.out.println("Failed to delete employee with register number " + registerNumber);
        }

    }
    public void searchEmployees() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter search query: ");
        String searchQuery = scanner.nextLine();

        // Call the DAO method to search for employees
        List<EmployeeDTO> matchingEmployees = employeeDAO.findEmployeesBySearchQuery(searchQuery);

        if (matchingEmployees.isEmpty()) {
            System.out.println("No employees found matching the search query: " + searchQuery);
        } else {
            System.out.println("Matching Employees:");
            for (EmployeeDTO employee : matchingEmployees) {
                System.out.println(employee);
            }
        }
    }
    public void listAllEmployees() {
        List<EmployeeDTO> employees = employeeDAO.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("List of Employees:");
            for (EmployeeDTO employee : employees) {
                System.out.println("Register Number: " + employee.getRegisterNumber());
                System.out.println("First Name: " + employee.getFirstName());
                System.out.println("Last Name: " + employee.getLastName());
                System.out.println("Birth Date: " + employee.getBirthDate());
                System.out.println("Phone: " + employee.getPhone());
                System.out.println("Recruitment Date: " + employee.getRecruitmentDate());
                System.out.println("Email: " + employee.getEmail());
                System.out.println("-----------------------");
            }
        }
    }
    public void updateEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the register number of the employee to update: ");
        String registerNumber = scanner.nextLine();

        // Check if the employee with the provided register number exists
        EmployeeDTO existingEmployee = employeeDAO.getEmployeeByRegisterNumber(registerNumber);

        if (existingEmployee == null) {
            System.out.println("Employee with register number " + registerNumber + " not found.");
            return; // Exit the method if the employee doesn't exist
        }

        System.out.println("Current Employee Information:");
        System.out.println(existingEmployee);

        // Gather updated information from the user
        System.out.print("Enter updated first name (leave empty to keep current): ");
        String updatedFirstName = scanner.nextLine();
        if (updatedFirstName.isEmpty()) {
            updatedFirstName = existingEmployee.getFirstName();
        }

        System.out.print("Enter updated last name (leave empty to keep current): ");
        String updatedLastName = scanner.nextLine();
        if (updatedLastName.isEmpty()) {
            updatedLastName = existingEmployee.getLastName();
        }

        System.out.print("Enter updated birth date (dd-MM-yyyy) (leave empty to keep current): ");
        String updatedBirthDateStr = scanner.nextLine();
        Date updatedBirthDate = updatedBirthDateStr.isEmpty() ? existingEmployee.getBirthDate() : Tools.stringToDate(updatedBirthDateStr);

        System.out.print("Enter updated phone number (leave empty to keep current): ");
        String updatedPhoneStr = scanner.nextLine();
        int updatedPhone = updatedPhoneStr.isEmpty() ? existingEmployee.getPhone() : Integer.parseInt(updatedPhoneStr);

        System.out.print("Enter updated recruitment date (dd-MM-yyyy) (leave empty to keep current): ");
        String updatedRecruitmentDateStr = scanner.nextLine();
        Date updatedRecruitmentDate = updatedRecruitmentDateStr.isEmpty() ? existingEmployee.getRecruitmentDate() : Tools.stringToDate(updatedRecruitmentDateStr);

        System.out.print("Enter updated email (leave empty to keep current): ");
        String updatedEmail = scanner.nextLine();
        if (updatedEmail.isEmpty()) {
            updatedEmail = existingEmployee.getEmail();
        }

        // Create an updated UserDTO
        UserDTO updatedUser = new UserDTO(updatedFirstName, updatedLastName, updatedBirthDate, updatedPhone);
        // Set the user ID to the existing user ID
        updatedUser.setId(existingEmployee.getId());

        // Update user information in the user table
        try {
            UserDTO updatedUserResult = userDAO.updateUser(updatedUser);

            if (updatedUserResult != null) {
                // User information updated successfully, now update employee information
                existingEmployee.setFirstName(updatedUserResult.getFirstName());
                existingEmployee.setLastName(updatedUserResult.getLastName());
                existingEmployee.setBirthDate(updatedUserResult.getBirthDate());
                existingEmployee.setPhone(updatedUserResult.getPhone());
                existingEmployee.setEmail(updatedEmail);
                existingEmployee.setRecruitmentDate(updatedRecruitmentDate);

                EmployeeDTO updatedEmployeeResult = employeeDAO.updateEmployee(existingEmployee);

                if (updatedEmployeeResult != null) {
                    System.out.println("Employee with register number " + registerNumber + " updated successfully.");
                } else {
                    System.out.println("Failed to update employee with register number " + registerNumber + " in the employee table.");
                }
            } else {
                System.out.println("Failed to update user with register number " + registerNumber + " in the user table.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
