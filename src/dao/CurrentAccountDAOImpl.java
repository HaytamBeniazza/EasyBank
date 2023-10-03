package dao;


import dto.AccountStatus;
import dto.ClientDTO;
import dto.CurrentAccountDTO;
import dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CurrentAccountDAOImpl implements CurrentAccountDAO{
    private final Connection connection;

    public CurrentAccountDAOImpl() {
        // Initialize your database connection here
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public CurrentAccountDTO createAccount(CurrentAccountDTO account) {
        try {
            String query = "INSERT INTO CurrentAccount (account_number, balance, discovery, creation_date, status, client_id, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setDouble(3, account.getDiscovery());
            preparedStatement.setDate(4, new java.sql.Date(account.getCreationDate().getTime()));
            preparedStatement.setString(5, account.getStatus().name());
            preparedStatement.setObject(6, account.getClient().getCode());
            preparedStatement.setString(7, account.getEmployeeId());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                return account;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteAccount(String accountNumber) {
        try {
            String query = "DELETE FROM CurrentAccount WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Return 0 to indicate deletion failure
        }
    }

//    @Override
//    public List<CurrentAccountDTO> getAccountsByClient(String clientCode) {
//        return null;
//    }

    @Override
    public List<CurrentAccountDTO> getAccountsByClient(String clientCode) {
        try {
            String query = "SELECT * FROM client JOIN useer ON client.userid = useer.id JOIN CurrentAccount ON client.code = CurrentAccount.client_id WHERE client.code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clientCode);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<CurrentAccountDTO> currentAccounts = new ArrayList<>();

            while (resultSet.next()) {
                String clientAddress = resultSet.getString("address");
                int userId = resultSet.getInt("userid"); // Assuming this is the reference to the associated user
                // Add other client attributes here...
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                Date birthDate = resultSet.getDate("birthdate");
                int phone = resultSet.getInt("phone");

                String accountNumber = resultSet.getString("account_number");
                double balance = resultSet.getDouble("balance");
                double discovery = resultSet.getDouble("discovery");
                Date creationDate = resultSet.getDate("creation_date");
                AccountStatus status = AccountStatus.valueOf(resultSet.getString("status"));
                String employeeRegisterNumber = resultSet.getString("employee_id");

                ClientDTO client = new ClientDTO(clientCode, clientAddress, firstName, lastName, birthDate, phone);
                client.setUserId(userId);

                CurrentAccountDTO currentAccount = new CurrentAccountDTO(accountNumber, balance, discovery, creationDate, status, clientCode, client);
                currentAccounts.add(currentAccount);
            }

            return currentAccounts;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Query execution failed
        }
    }

    @Override
    public CurrentAccountDTO getAccountByNumber(String accountNumber) {
        try {
            String query = "SELECT * FROM client JOIN useer ON client.userid = useer.id JOIN CurrentAccount ON client.code = CurrentAccount.client_id WHERE currentAccount.account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

//            List<CurrentAccountDTO> currentAccounts = new ArrayList<>();

            if (resultSet.next()) {
                String clientAddress = resultSet.getString("address");
                int userId = resultSet.getInt("userid"); // Assuming this is the reference to the associated user
                // Add other client attributes here...
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                Date birthDate = resultSet.getDate("birthdate");
                int phone = resultSet.getInt("phone");

                String clientCode = resultSet.getString("code");
                double balance = resultSet.getDouble("balance");
                double discovery = resultSet.getDouble("discovery");
                Date creationDate = resultSet.getDate("creation_date");
                AccountStatus status = AccountStatus.valueOf(resultSet.getString("status"));
                String employeeRegisterNumber = resultSet.getString("employee_id");

                ClientDTO client = new ClientDTO(clientCode, clientAddress, firstName, lastName, birthDate, phone);
                client.setUserId(userId);

                CurrentAccountDTO currentAccount = new CurrentAccountDTO(accountNumber, balance, discovery, creationDate, status, clientCode, client);
                currentAccount.setAccountNumber(accountNumber);
                return currentAccount;
            }else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
