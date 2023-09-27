// EmployeeService.java
package service;

import dao.ClientDAO;
import dao.EmployeeDAO;
import dao.UserDAO;
import dto.ClientDTO;
import dto.EmployeeDTO;
import util.Tools;

import java.util.Date;
import java.util.Scanner;

public class ClientService {
    private final ClientDAO clientDAO;
    private final UserDAO userDAO;

    public ClientService(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
    }

    public void addClient() {
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
        System.out.print("Enter the code:");
        String code = scanner.nextLine();
        System.out.print("Enter the adress:");
        String address = scanner.nextLine();


        ClientDTO newClient = new ClientDTO(code, address, firstName, lastName, birthDate1, phone);

        // Implement transaction management here
        try {
            userDAO.addUser(newClient);
            clientDAO.addClient(newClient);
            System.out.println("Added successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
