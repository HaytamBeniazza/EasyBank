package service;

import dao.ClientDAO;
import dao.ClientDAOImpl;
import dao.CurrentAccountDAO;
import dao.SavingAccountDAO;
import dto.AccountStatus;
import dto.ClientDTO;
import dto.CurrentAccountDTO;
import util.Tools;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CurrentAccountService {
    private final CurrentAccountDAO currentAccountDAO;
    private ClientDAO clientDAO;

    public CurrentAccountService(CurrentAccountDAO currentAccountDAO, ClientDAO clientDAO) {
        this.currentAccountDAO = currentAccountDAO;
        this.clientDAO = clientDAO;
    }
    public void addAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter balance: ");
        double balance = scanner.nextDouble();
        System.out.print("Enter discovery: ");
        double discovery = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter creation date (dd-MM-yyyy): ");
        String creationDateStr = scanner.nextLine();
        Date creationDate = Tools.stringToDate(creationDateStr);
        System.out.print("Enter status (active or inactive): ");
        String statusStr = scanner.nextLine();
        AccountStatus status = AccountStatus.valueOf(statusStr.toUpperCase());
        System.out.print("Enter client code: ");
        String clientCode = scanner.nextLine();
        System.out.print("Enter employee register number: ");
        String employeeRegisterNumber = scanner.nextLine();

        // Retrieve client information
        ClientDTO client = clientDAO.getClientByCode(clientCode);

        if (client == null) {
            System.out.println("Client with code " + clientCode + " not found.");
            return;
        }

        CurrentAccountDTO currentAccount = new CurrentAccountDTO(accountNumber, balance, discovery, creationDate, status, employeeRegisterNumber, client);

        try {
            currentAccountDAO.createAccount(currentAccount);
            System.out.println("Current account created successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void deleteAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the account number to delete: ");
        String accountNumber = scanner.nextLine();

        int rowsDeleted = currentAccountDAO.deleteAccount(accountNumber);

        if (rowsDeleted == 1) {
            System.out.println("Account with account number " + accountNumber + " deleted successfully.");
        } else if (rowsDeleted == 0) {
            System.out.println("No account found with account number " + accountNumber + ". Deletion failed.");
        } else {
            System.out.println("Error occurred while deleting the account.");
        }
    }

    public void getAccountsByClient(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the client code: ");
        String clientCode = scanner.nextLine();

        try {
            List<CurrentAccountDTO> currentAccounts =  currentAccountDAO.getAccountsByClient(clientCode);

            for(CurrentAccountDTO currentAccountDTO : currentAccounts){
                System.out.println(currentAccountDTO.toString());

            }

        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
