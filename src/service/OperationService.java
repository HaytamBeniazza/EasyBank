package service;

import dao.CurrentAccountDAO;
import dao.EmployeeDAO;
import dao.OperationDAO;
import dao.SavingAccountDAO;
import dto.*;
import util.Tools;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OperationService {
    private OperationDAO operationDAO;
    private CurrentAccountDAO currentAccountDAO;
    private SavingAccountDAO savingAccountDAO;
    private EmployeeDAO employeeDAO;
//    private AccountDTO accountDTO;

    public OperationService(
//            AccountDTO accountDTO,
            OperationDAO operationDAO,
            CurrentAccountDAO currentAccountDAO,
            SavingAccountDAO savingAccountDAO,
            EmployeeDAO employeeDAO
    ) {
        this.operationDAO = operationDAO;
        this.currentAccountDAO = currentAccountDAO;
        this.savingAccountDAO = savingAccountDAO;
        this.employeeDAO = employeeDAO;
//        this.accountDTO = accountDTO;
    }

    public void addOperation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter operation type (WITHDRAWAL or TRANSACTION): ");
        String typeStr = scanner.nextLine();
        OperationType type = OperationType.valueOf(typeStr.toUpperCase()); // Assuming OperationType is an enum
        System.out.print("Enter operation date (dd-MM-yyyy): ");
        String dateStr = scanner.nextLine();
        Date date = Tools.stringToDate(dateStr); // Assuming you have a Tools class for date conversion
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter employee register number: ");
        String employeeRegisterNumber = scanner.nextLine();

        EmployeeDTO employee = employeeDAO.getEmployeeByRegisterNumber(employeeRegisterNumber);

        if (accountNumber.startsWith("1")) {
            // This is a saving account
            SavingAccountDTO savingAccount = savingAccountDAO.getAccountByNumber(accountNumber);

            // Create an OperationDTO object with the retrieved account and employee information
            OperationDTO newOperation = new OperationDTO(amount, type, date, null, savingAccount, employee);

            // Now set the savingAccountDTO property
            newOperation.setSavingAccount(savingAccount);

            try {
                operationDAO.createOperationSaving(newOperation);
                System.out.println("Added successfully");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else if (accountNumber.startsWith("2")) {
            // This is a current account
            CurrentAccountDTO currentAccount = currentAccountDAO.getAccountByNumber(accountNumber);

            // Create an OperationDTO object with the retrieved account and employee information
            OperationDTO newOperation = new OperationDTO(amount, type, date, currentAccount, null, employee);

            // Now set the currentAccountDTO property
            newOperation.setCurrentAccount(currentAccount);

            try {
                operationDAO.createOperationCurrent(newOperation);
                System.out.println("Added successfully");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }

}
