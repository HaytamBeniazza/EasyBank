package dto;

import java.util.Date;

public class AccountDTO {
    protected String accountNumber;
    protected double balance;
    protected Date creationDate;
    protected AccountStatus status;
    protected ClientDTO client;
    protected String employeeId;

    public AccountDTO(String accountNumber, double balance, Date creationDate, AccountStatus status, ClientDTO client, String employeeId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.creationDate = creationDate;
        this.status = status;
        this.client = client;
        this.employeeId = employeeId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}

