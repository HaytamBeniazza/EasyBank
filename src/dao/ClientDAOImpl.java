package dao;

import dto.ClientDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAOImpl implements ClientDAO{
    private Connection connection;

    public ClientDAOImpl(){
        connection = DatabaseConnection.getConnection();
    }

    @Override
    public ClientDTO addClient(ClientDTO client) {
        try {
            // Define the SQL query to insert a new employee
            String insertClientQuery = "INSERT INTO client (code, address, userId) VALUES (?, ?, ?)";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(insertClientQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getCode());
            preparedStatement.setString(2, client.getAddress());
            preparedStatement.setInt(3, client.getId());

            // Execute the insertion query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                // Employee was successfully added to the database
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    String generatedCode = generatedKeys.getString(1);
                    client.setCode(generatedCode);
                }
                return client;
            } else {
                // Insertion failed
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
