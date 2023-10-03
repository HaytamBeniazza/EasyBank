package dao;

import dto.AccountStatus;
import dto.ClientDTO;
import dto.CurrentAccountDTO;
import dto.SavingAccountDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SavingAccountDAOImpl implements SavingAccountDAO {
    private final Connection connection;

    public SavingAccountDAOImpl() {
        // Initialize your database connection here
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public SavingAccountDTO createAccount(SavingAccountDTO account) {
        try {
            // Define the SQL query to insert a new saving account
            String insertQuery = "INSERT INTO SavingAccount (account_number, balance, creation_date, status, interest_rate, client_id, employee_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setDate(3, new java.sql.Date(account.getCreationDate().getTime()));
            preparedStatement.setObject(4, account.getStatus().name());
            preparedStatement.setDouble(5, account.getInterestRate());
            preparedStatement.setObject(6, account.getClient().getCode());
            preparedStatement.setString(7, account.getEmployeeId());

            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if a row was affected (account created)
            if (rowsAffected > 0) {
                return account; // Return the created account
            } else {
                return null; // Insert failed, return null
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Insert failed due to an exception
        }
    }

    @Override
    public int deleteAccount(String accountNumber) {
        try {
            // Define the SQL query to delete a saving account by account number
            String deleteQuery = "DELETE FROM SavingAccount WHERE account_number = ?";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, accountNumber);

            // Execute the delete query and return the number of rows deleted
            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate an error
        }
    }

    @Override
    public List<SavingAccountDTO> getAccountsByClient(String clientCode) {
        try {
            String query = "SELECT * FROM client JOIN useer ON client.userid = useer.id JOIN savingAccount ON client.code = savingAccount.client_id WHERE client.code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clientCode);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<SavingAccountDTO> savingAccounts = new ArrayList<>();

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
                double interestRate = resultSet.getDouble("interest_rate");
                Date creationDate = resultSet.getDate("creation_date");
                AccountStatus status = AccountStatus.valueOf(resultSet.getString("status"));
                String employeeRegisterNumber = resultSet.getString("employee_id");

                ClientDTO client = new ClientDTO(clientCode, clientAddress, firstName, lastName, birthDate, phone);
                client.setUserId(userId);

                SavingAccountDTO savingAccount = new SavingAccountDTO(accountNumber, balance, creationDate, status, interestRate, client, employeeRegisterNumber);
                savingAccounts.add(savingAccount);
            }

            return savingAccounts;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Query execution failed
        }
    }

    @Override
    public SavingAccountDTO getAccountByNumber(String accountNumber) {
        try {
            String query = "SELECT * FROM client JOIN useer ON client.userid = useer.id JOIN savingAccount ON client.code = savingAccount.client_id WHERE savingaccount.account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();


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
                double interestRate = resultSet.getDouble("interest_rate");
                Date creationDate = resultSet.getDate("creation_date");
                AccountStatus status = AccountStatus.valueOf(resultSet.getString("status"));
                String employeeRegisterNumber = resultSet.getString("employee_id");

                ClientDTO client = new ClientDTO(clientCode, clientAddress, firstName, lastName, birthDate, phone);
                client.setUserId(userId);

                SavingAccountDTO savingAccount = new SavingAccountDTO(accountNumber, balance, creationDate, status, interestRate, client, employeeRegisterNumber);
                savingAccount.setAccountNumber(accountNumber);
                return savingAccount;
            }else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

