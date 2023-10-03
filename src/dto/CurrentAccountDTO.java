package dto;
import dto.AccountDTO;
import dto.AccountStatus;
import dto.ClientDTO;

import java.util.Date;

public class CurrentAccountDTO extends AccountDTO {
    private double discovery;
    private ClientDTO client;

    public CurrentAccountDTO(
            String accountNumber,
            double balance,
            double discovery,
            Date creationDate,
            AccountStatus status,
            String employeeId,
            ClientDTO client // Include the client information
    ) {
        super(accountNumber, balance, creationDate, status, client, employeeId);
        this.discovery = discovery;
        this.client = client;
    }

    public double getDiscovery() {
        return discovery;
    }

    public void setDiscovery(double discovery) {
        this.discovery = discovery;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Current Account:" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", discovery=" + discovery +
                ", creationDate=" + creationDate +
                ", status=" + status +
                ", employeeId='" + employeeId + '\'' +
                ", client=" + client;
    }
}
