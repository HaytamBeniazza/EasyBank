package service;

import dao.*;

import java.sql.Connection;
import java.util.Scanner;

public class EasybankApp {
    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final SavingAccountService savingAccountService;
    private final CurrentAccountService currentAccountService;
    private final OperationService operationService;
    private final Connection connection;

    public EasybankApp() {
        // Initialize the database connection
        connection = DatabaseConnection.getConnection();
        operationService = new OperationService(new OperationDAOImpl(), new CurrentAccountDAOImpl(), new SavingAccountDAOImpl(), new EmployeeDAOImpl());
        employeeService = new EmployeeService(new EmployeeDAOImpl(), new UserDAOImpl());
        clientService = new ClientService(new ClientDAOImpl(), new UserDAOImpl());
        savingAccountService = new SavingAccountService(new SavingAccountDAOImpl(), new ClientDAOImpl());
        currentAccountService = new CurrentAccountService(new CurrentAccountDAOImpl(), new ClientDAOImpl());
    }

    public void start(){
        showMenu();
    }

    public void showMenu(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Add employee");
            System.out.println("2. Delete employee");
            System.out.println("3. Get all employees");
            System.out.println("4. Search by attribute");
            System.out.println("5. Update employee");
            System.out.println("6. Add a client");
            System.out.println("7. Delete client");
            System.out.println("8. Get all clients");
            System.out.println("9. Add Saving Account");
            System.out.println("10. Add Current Account");
            System.out.println("11. Delete Current Account");
            System.out.println("12. Delete Saving Account");
            System.out.println("13. Find Current Account by client");
            System.out.println("14. Add an operation");

            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> employeeService.addEmployee();
                    case 2 -> employeeService.deleteEmployee();
                    case 3 -> employeeService.listAllEmployees();
                    case 4 -> employeeService.searchEmployees();
                    case 5 -> employeeService.updateEmployee();
                    case 6 -> clientService.addClient();
                    case 7 -> clientService.deleteClient();
                    case 8 -> clientService.getAllClients();
                    case 9 -> savingAccountService.addAccount();
                    case 10 -> currentAccountService.addAccount();
                    case 11 -> currentAccountService.deleteAccount();
                    case 12 -> savingAccountService.deleteAccount();
                    case 13 -> currentAccountService.getAccountsByClient();
                    case 14 -> operationService.addOperation();
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
