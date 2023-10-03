package service;
import dao.ClientDAO;
import dao.ClientDAOImpl;
import dao.SavingAccountDAO;
import dto.ClientDTO;
import dto.SavingAccountDTO;
import dto.AccountStatus; // Import AccountStatus from the dto package
import util.Tools;

import java.util.Date;
import java.util.Scanner;

public class SavingAccountService {
    private final SavingAccountDAO savingAccountDAO;
    private ClientDAO clientDAO;

    public SavingAccountService(SavingAccountDAO savingAccountDAO, ClientDAO clientDAO) {
        this.savingAccountDAO = savingAccountDAO;
        this.clientDAO = clientDAO;
    }

    public void addAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter balance: ");
        double balance = scanner.nextDouble();

        System.out.print("Enter creation date (dd-MM-yyyy): ");
        scanner.nextLine(); // Consume the newline character
        String creationDateStr = scanner.nextLine();
        Date creationDate = Tools.stringToDate(creationDateStr);

        System.out.print("Enter status ('active' or 'inactive'): ");
        String statusStr = scanner.nextLine();
        AccountStatus status = AccountStatus.valueOf(statusStr.toUpperCase());

        System.out.print("Enter interest rate (e.g., 0.025 for 2.5%): ");
        double interestRate = scanner.nextDouble();

        System.out.print("Enter client ID: ");
        String clientId = scanner.next();

        System.out.print("Enter employee ID: ");
        String employeeId = scanner.next();

        ClientDTO client = clientDAO.getClientByCode(clientId);


        SavingAccountDTO savingAccount = new SavingAccountDTO(accountNumber, balance, creationDate, status, interestRate, client, employeeId);

        try {
            savingAccountDAO.createAccount(savingAccount);
            System.out.println("Saving account created successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the account number to delete: ");
        String accountNumber = scanner.nextLine();

        int rowsDeleted = savingAccountDAO.deleteAccount(accountNumber);

        if (rowsDeleted >= 1) {
            System.out.println("Account with account number " + accountNumber + " deleted successfully.");
        } else if (rowsDeleted == 0) {
            System.out.println("No account found with account number " + accountNumber + ". Deletion failed.");
        } else {
            System.out.println("Error occurred while deleting the account.");
        }
    }
}
