package service;

import dao.*;

import java.sql.Connection;
import java.util.Scanner;

public class EasybankApp {
    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final Connection connection;

    public EasybankApp() {
        // Initialize the database connection
        connection = DatabaseConnection.getConnection();
        employeeService = new EmployeeService(new EmployeeDAOImpl(), new UserDAOImpl());
        clientService = new ClientService(new ClientDAOImpl(), new UserDAOImpl());
    }

    public void start(){
        showMenu();
    }

    public void showMenu(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Create employee");
            System.out.println("2. Create a client");
            System.out.println("3. Perform a transaction");
            System.out.println("4. View account details");
            System.out.println("5. Add new employee");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> employeeService.addEmployee();
                    case 2 -> clientService.addClient();
                    case 3 -> System.out.println("Perform a transaction feature is not implemented yet.");
                    case 4 -> System.out.println("View account details feature is not implemented yet.");
                    case 5 -> System.out.println("Add new employee feature is not implemented yet.");
                    case 6 -> {
                        System.out.println("Exiting the application.");
                        scanner.close(); // Close the scanner before exiting
                        DatabaseConnection.closeConnection(); // Close the database connection
                        System.exit(0); // Exit the application
                    }
                    default -> System.out.println("Please choose a valid option.");
                }
            } else {
                System.out.println("Please enter a valid option.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    public static void main(String[] args) {
        EasybankApp app = new EasybankApp();
        app.start();
    }
}
