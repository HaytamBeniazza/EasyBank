package dto;

import java.util.Date;

public class SavingAccountDTO extends AccountDTO {
    private double interestRate;

    // Constructors

//    public SavingAccountDTO() {
//        // Default constructor
//    }

    public SavingAccountDTO(
            String accountNumber,
            double balance,
            Date creationDate,
            AccountStatus status,
            double interestRate,
            ClientDTO client,
            String employeeId
    ) {
        super(accountNumber, balance, creationDate, status, client, employeeId);
        this.interestRate = interestRate;
    }

    // Getter and Setter for interestRate

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
