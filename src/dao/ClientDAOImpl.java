package dao;

import dto.ClientDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public int deleteClient(String code) {
        try {
            // Define the SQL query to delete an employee by registerNumber
            String deleteClientQuery = "DELETE FROM client WHERE code = ?";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(deleteClientQuery);
            preparedStatement.setString(1, code);

            // Execute the deletion query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return the number of rows affected
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return a negative value to indicate deletion failure
        }
    }

    @Override
    public List<ClientDTO> getAllClients() {
        List<ClientDTO> clients = new ArrayList<>();

        try {
            // Define the SQL query to retrieve all clients
            String query = "SELECT * FROM client";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and create ClientDTO objects
            while (resultSet.next()) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                int phone = resultSet.getInt("phone");
                Date birthdate = resultSet.getDate("birthdate");
                String code = resultSet.getString("code");
                String address = resultSet.getString("address");
                // Add more fields as needed

                // Create a ClientDTO object and add it to the list
                ClientDTO client = new ClientDTO(code, address, firstName, lastName, birthdate, phone);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public ClientDTO getClientByCode(String code) {
        try {
            String query = "SELECT * FROM client join useer on useer.id = client.userid WHERE code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("userid"); // Assuming this is the reference to the associated user
                // Add other client attributes here...
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String address = resultSet.getString("address");
                Date birthDate = resultSet.getDate("birthdate");
                int phone = resultSet.getInt("phone");

                ClientDTO client = new ClientDTO(code, address, firstName, lastName, birthDate, phone);
                client.setUserId(userId);

                return client;
            } else {
                return null; // Client with the given code not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Query execution failed
        }
    }


}
