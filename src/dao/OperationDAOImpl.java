package dao;

import dto.OperationDTO;

import java.sql.*;

public class OperationDAOImpl implements OperationDAO{
    private Connection connection;
    public OperationDAOImpl(){
        connection = DatabaseConnection.getConnection();
    }
    @Override
    public OperationDTO createOperationSaving(OperationDTO operation) {
        try {
            String query = "INSERT INTO operation (amount, type, date, saving_account_number, current_account_number, employee_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, operation.getAmount());
            preparedStatement.setString(2, operation.getType().name());
            preparedStatement.setDate(3, new java.sql.Date(operation.getDate().getTime()));
            preparedStatement.setString(4, null);
            preparedStatement.setString(5, operation.getCurrentAccount().getAccountNumber());
            preparedStatement.setString(6, operation.getEmployee().getRegisterNumber());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int operationId = generatedKeys.getInt(1);
                    operation.setId(operationId);
                    return operation;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public OperationDTO createOperationCurrent(OperationDTO operation) {
        try {
            String query = "INSERT INTO operation (amount, type, date, saving_account_number, current_account_number, employee_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, operation.getAmount());
            preparedStatement.setString(2, operation.getType().name());
            preparedStatement.setDate(3, new java.sql.Date(operation.getDate().getTime()));
            preparedStatement.setString(4, operation.getSavingAccount().getAccountNumber());
            preparedStatement.setString(5, null);
            preparedStatement.setString(6, operation.getEmployee().getRegisterNumber());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int operationId = generatedKeys.getInt(1);
                    operation.setId(operationId);
                    return operation;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
