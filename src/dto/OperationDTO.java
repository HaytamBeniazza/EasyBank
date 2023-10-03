package dto;
import dao.CurrentAccountDAO;
import dao.SavingAccountDAO;
import dto.AccountDTO;
import dto.EmployeeDTO;
import dto.OperationType;

import java.util.Date;

public class OperationDTO {
    private int id;
    private double amount;
    private OperationType type;
    private Date date;
    private SavingAccountDTO savingAccountDTO;
    private CurrentAccountDTO currentAccountDTO;
    private EmployeeDTO employeeDTO;

//    public OperationDTO() {
//        // Default constructor
//    }

    public OperationDTO(double amount, OperationType type, Date date, CurrentAccountDTO currentAccountDTO, SavingAccountDTO savingAccountDTO, EmployeeDTO employeeDTO) {
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.savingAccountDTO = savingAccountDTO;
        this.currentAccountDTO = currentAccountDTO;
        this.employeeDTO = employeeDTO;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CurrentAccountDTO getCurrentAccount() {
        return currentAccountDTO;
    }

    public void setCurrentAccount(CurrentAccountDTO currentAccountDTO) {
        this.currentAccountDTO = currentAccountDTO;
    }
    public SavingAccountDTO getSavingAccount() {
        return savingAccountDTO;
    }

    public void setSavingAccount(SavingAccountDTO savingAccountDTO) {
        this.savingAccountDTO = savingAccountDTO;
    }

    public EmployeeDTO getEmployee() {
        return employeeDTO;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employeeDTO = employeeDTO;
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", type=" + type +
                ", date=" + date +
                ", current account=" + currentAccountDTO +
                ", saving account=" + savingAccountDTO +
                ", employee=" + employeeDTO +
                '}';
    }
}
