package dao;

import dto.EmployeeDTO;
import dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
private Connection connection;
    public UserDAOImpl(){
        connection = DatabaseConnection.getConnection();
    }

    @Override
    public UserDTO addUser(UserDTO user) {
        try {
            // Define the SQL query to insert a new user
            String insertUserQuery = "INSERT INTO Useer (firstName, lastName, birthDate, phone) VALUES (?, ?, ?, ?)";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new java.sql.Date(user.getBirthDate().getTime()));
            preparedStatement.setInt(4, user.getPhone());

            // Execute the insertion query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                // User was successfully added to the database
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    user.setId(generatedId);
                }
                return user;
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
    public UserDTO updateUser(UserDTO user) {
        try {
            // Define the SQL query to update an employee by registerNumber
            String updateEmployeeQuery = "UPDATE useer SET firstname = ?, lastname = ?, birthdate = ?, phone = ? WHERE id = ?";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(updateEmployeeQuery);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new java.sql.Date(user.getBirthDate().getTime()));
            preparedStatement.setInt(4, user.getPhone());
            preparedStatement.setInt(5, user.getId());

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if a row was affected (employee updated)
            if (rowsAffected > 0) {
                return user; // Return the updated employee
            } else {
                return null; // Update failed, return null
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Update failed due to an exception
        }

    }

}
